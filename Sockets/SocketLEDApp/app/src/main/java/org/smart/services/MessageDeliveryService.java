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
import org.smart.gateway.model.GatewayClientInfo;
import org.smart.gateway.service.SocketService;
import org.smart.gateway.service.SocketServiceDelegate;
import org.smart.gateway.service.options.SocketClientOptions;
import org.smart.gateway.service.thread.SocketClientThread;
import org.smart.gateway.service.thread.SocketClientThreadDelegate;
import org.smart.gateway.service.thread.SocketListeningThread;
import org.smart.gateway.service.thread.SocketListeningThreadDelegate;
import org.smart.gateway.util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.PublicKey;
import java.util.ArrayList;

public class MessageDeliveryService extends Service implements SocketListeningThreadDelegate, SocketClientThreadDelegate {
    ServerSocket server;
    Thread serverThread;
    Thread cThread;
    Thread wThread;

    SocketListeningThread listeningThread;
    SocketClientThread clientThread;

    SocketServiceDelegate socketServiceDelegate;
    SocketClientOptions options;
    GatewayClientInfo clientInfo;

    private MessageBinder binder = new MessageBinder();

    private MessageDeliveryServiceDelegate messageDeliveryServiceDelegate;

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

    public void initConnectionService(SocketClientOptions options) {
        this.options = options;

        clientInfo = new GatewayClientInfo();
        clientInfo.setHost(NetworkUtil.getHostAddress());
        clientInfo.setPort(options.getLocalPort());
        clientInfo.setKey(options.getClientName());

        listeningClients();
        connectClient();
        //handler.postDelayed(disconnect, DISCONNECT_INTERVAL);
    }

    public void publish(final String message) {
        wThread = new Thread() {
            @Override
            public void run() {
                if(clientThread != null) {
                    clientThread.sendMessage(options.getTargetName(), message);
                    interrupt();
                }
            }
        };
        wThread.start();
    }

    public Runnable listening = new Runnable() {
        public void run() {
            listeningClients();
            //handler.postDelayed(listening, RECONNECT_INTERVAL);
        }
    };

    public Runnable connect = new Runnable() {
        public void run() {
            connectClient();
            //handler.postDelayed(connect, RECONNECT_INTERVAL);
        }
    };

    public Runnable disconnect = new Runnable() {
        public void run() {
            disconnectClients();
            //handler.postDelayed(disconnect, DISCONNECT_INTERVAL);
        }
    };

    private void listeningClients() {
        serverThread = new Thread() {
            @Override
            public void run() {
                try {
                    server = new ServerSocket(options.getLocalPort());

                    while(true) {
                        //Waiting for connection
                        Socket clientSocket = server.accept();

                        //Connection stablished
                        listeningThread = new SocketListeningThread(clientSocket);
                        listeningThread.setDelegate(MessageDeliveryService.this);
                        listeningThread.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        serverThread.start();
    }

    private void connectClient() {
        cThread = new Thread() {
            @Override
            public void run() {
                if(clientThread == null) {
                    Socket client;
                    try {
                        client = new Socket(options.getHost(), options.getHostPort());

                        clientThread = new SocketClientThread(client);
                        clientThread.setDelegate(MessageDeliveryService.this);
                        clientThread.setClientInfo(clientInfo);
                        clientThread.start();

                        clientThread.connect();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        cThread.start();

    }

    private void disconnectClients() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "CREATE SERVICE", Toast.LENGTH_SHORT).show();

//        options = new SocketClientOptions();
//        options.setHost("192.168.15.6");
//        options.setHostPort(8000);
//        options.setLocalPort(8001);
//        options.setClientName("lamp1");
//        options.setTargetName("control");
//
//        initConnectionService(options);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        disconnectClients();

        handler.removeCallbacks(connect);
        handler.removeCallbacks(disconnect);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "STARTING SERVICE", Toast.LENGTH_SHORT).show();

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Toast.makeText(this, "BIDING SERVICE", Toast.LENGTH_SHORT).show();

        SocketClientOptions options =
                (SocketClientOptions) intent.getSerializableExtra("CONNECTION");
        initConnectionService(options);

        return binder;
    }

    public SocketServiceDelegate getSocketServiceDelegate() {
        return socketServiceDelegate;
    }

    public void setSocketServiceDelegate(SocketServiceDelegate socketServiceDelegate) {
        this.socketServiceDelegate = socketServiceDelegate;
    }

    public MessageDeliveryServiceDelegate getMessageDeliveryServiceDelegate() {
        return messageDeliveryServiceDelegate;
    }

    public void setMessageDeliveryServiceDelegate(MessageDeliveryServiceDelegate messageDeliveryServiceDelegate) {
        this.messageDeliveryServiceDelegate = messageDeliveryServiceDelegate;
    }

    public void setOptions(SocketClientOptions options) {
        this.options = options;
    }

    @Override
    public SocketClientOptions getOptions() {
        return null;
    }

    @Override
    public void postInformation(String message) {
        messageDeliveryServiceDelegate.postReceivedMessage(message);
    }
}
