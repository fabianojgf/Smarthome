package org.smart.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.security.PublicKey;
import java.util.ArrayList;

public class MessageDeliveryService extends Service  implements MqttCallback, IMqttActionListener {
    private static final MemoryPersistence persistence = new MemoryPersistence();

    private MqttAndroidClient mqttClient;
    private MqttConnectOptions connectionOptions;

    private MessageBinder binder = new MessageBinder();
    private ArrayList<MqttAndroidClient> lostConnectionClients;

    private MessageDeliveryServiceDelegate delegate;
    private MessageDeliveryOptions options;

    private boolean isReady = false;
    private boolean doConnectTask = true;
    private boolean isConnectInvoked = false;

    private Handler handler = new Handler();
    private final int RECONNECT_INTERVAL = 10000; // 10 seconds
    private final int DISCONNECT_INTERVAL = 20000; // 20 seconds
    private final int CONNECTION_TIMEOUT = 60;
    private final int KEEP_ALIVE_INTERVAL = 200;

    public MessageDeliveryService() {

    }

    public class MessageBinder extends Binder {
        public MessageDeliveryService getService() {
            return MessageDeliveryService.this;
        }
    }

    public void initNewClient() {
        if(mqttClient != null)
            removeOldClient();
        mqttClient = new MqttAndroidClient(MessageDeliveryService.this, options.getBrokerUri(), options.getClientId());
        mqttClient.setCallback(this);
    }

    public void removeOldClient() {
        try {
            mqttClient.setCallback(null);
            mqttClient.disconnect();
            mqttClient = null;
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void initConnectionService(MessageDeliveryOptions options) {
        if(mqttClient != null) {
            removeOldClient();
            mqttClient = null;
        }

        lostConnectionClients = new ArrayList<>();

        connectionOptions = new MqttConnectOptions();
        connectionOptions.setCleanSession(true);
        connectionOptions.setConnectionTimeout(CONNECTION_TIMEOUT);
        connectionOptions.setKeepAliveInterval(KEEP_ALIVE_INTERVAL);

        initNewClient();

        handler.post(connect);
        handler.postDelayed(disconnect, DISCONNECT_INTERVAL);
    }

    public void subscribe(String topic) {
        try {
            mqttClient.subscribe(topic, 0);
            isReady = true;
        } catch (MqttSecurityException mqttSexEx) {
            isReady = false;
        } catch (MqttException mqttEx) {
            isReady = false;
        }
    }

    public void unsubscribe(String topic) {
        try {
            mqttClient.unsubscribe(topic);
        } catch (MqttSecurityException mqttSecEx) {
            Log.e("TAG", mqttSecEx.getMessage());
        } catch (MqttException mqttEx) {
            Log.e("TAG", mqttEx.getMessage());
        }
    }

    public void publish(String topic, String jsonPayload) {
//        if(!isReady) {
//            return;
//        }

        try {
            MqttMessage msg = new MqttMessage();
            msg.setQos(0);
            msg.setPayload(jsonPayload.getBytes("UTF-8"));
            mqttClient.publish(topic, msg);
        } catch (Exception ex) {
            Log.e("TAG", ex.toString());
        }
    }

    public Runnable connect = new Runnable() {
        public void run() {
            connectClient();
            handler.postDelayed(connect, RECONNECT_INTERVAL);
        }
    };

    public Runnable disconnect = new Runnable() {
        public void run() {
            disconnectClients();
            handler.postDelayed(disconnect, DISCONNECT_INTERVAL);
        }
    };

    private void connectClient() {
        if(doConnectTask) {
            doConnectTask = false;

            try {
                isConnectInvoked = true;
                mqttClient.connect(connectionOptions, null, this);
            } catch (MqttException ex) {
                doConnectTask = true;
                Log.e("TAG", ex.toString());
            }
        }
    }

    private void disconnectClients() {
        if (lostConnectionClients.size() > 0) {
            // Disconnect lost connection clients
            for (MqttAndroidClient client : lostConnectionClients) {
                if (client.isConnected()) {
                    try {
                        client.disconnect();
                    } catch (MqttException e) {
                        Log.e("TAG", e.toString());
                    }
                }
            }

            // Close already disconnected clients
            for (int i = lostConnectionClients.size() - 1; i >= 0; i--) {
                try {
                    if (!lostConnectionClients.get(i).isConnected()) {
                        MqttAndroidClient client = lostConnectionClients.get(i);
                        client.close();
                        lostConnectionClients.remove(i);
                    }
                } catch (IndexOutOfBoundsException e) {
                    Log.e("TAG", e.toString());
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "CREATE SERVICE", Toast.LENGTH_SHORT).show();

//        options = new MessageDeliveryOptions();
//        options.setBrokerUri("tcp://192.168.15.6:1883");
//        options.setTopicDomain("smarthome");
//        options.setTopicTargetId("lamp1");
//        options.setClientId("lamp1_1");

//        initConnectionService(options);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        disconnectClients();
        if (isConnectInvoked && mqttClient != null && mqttClient.isConnected()) {
            try {
                // unsubscribe here
                unsubscribe(options.getTopicFullName());
                mqttClient.disconnect();
            } catch (MqttException e) {
                Log.e("TAG", e.toString());
            }
        }

        handler.removeCallbacks(connect);
        handler.removeCallbacks(disconnect);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Toast.makeText(this, "STARTING SERVICE", Toast.LENGTH_SHORT).show();

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Toast.makeText(this, "BIDING SERVICE", Toast.LENGTH_SHORT).show();

        options = (MessageDeliveryOptions) intent.getSerializableExtra("CONNECTION");
        initConnectionService(options);

        return binder;
    }

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        isReady = true;
        subscribe(options.getTopicFullName());

        Toast.makeText(this, "SUCESS SERVICE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        initNewClient();

        isReady = false;
        doConnectTask = true;
        isConnectInvoked = false;
        Toast.makeText(this, "FAILURE SERVICE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void connectionLost(Throwable cause) {
        Log.e("TAG", cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        //Toast.makeText(this, "MESSAGE RECEIVED: " + new String(message.getPayload()), Toast.LENGTH_SHORT).show();
        delegate.postReceivedMessage(new String(message.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.e("TAG", "deliveryComplete()");
    }

    public MessageDeliveryServiceDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(MessageDeliveryServiceDelegate delegate) {
        this.delegate = delegate;
    }

    public MessageDeliveryOptions getOptions() {
        return options;
    }

    public void setOptions(MessageDeliveryOptions options) {
        this.options = options;
    }
}
