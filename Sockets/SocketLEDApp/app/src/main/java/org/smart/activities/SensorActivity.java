package org.smart.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.smart.R;
import org.smart.gateway.service.options.SocketClientOptions;
import org.smart.mqtt.equipament.model.LightSensor;
import org.smart.services.MessageDeliveryService;
import org.smart.services.MessageDeliveryServiceDelegate;

public class SensorActivity extends AppCompatActivity implements SensorEventListener, ServiceConnection, MessageDeliveryServiceDelegate {
    private SensorManager mSensorManager;
    private Sensor mPressure;

    private TextView textView;

    private MessageDeliveryService service;
    private LightSensor equipament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        textView = (TextView) findViewById(R.id.textViewSensorValue);

        equipament = new LightSensor();

        // Get an instance of the sensor service, and use that to get an instance of
        // a particular sensor.
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
        textView.setText("onSensonAccuracyChangedorChanged: " + accuracy);
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        float millibars_of_pressure = event.values[0];

        // Do something with this sensor data.
        textView.setText("onSensorChanged: " + millibars_of_pressure);

        getEquipament().setValue(millibars_of_pressure);
        sendInformation();
    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();

        mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);

        SocketClientOptions options =
                (SocketClientOptions) getIntent().getSerializableExtra("CONNECTION");
        Intent intent = new Intent(this, MessageDeliveryService.class);
        intent.putExtra("CONNECTION", options);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public LightSensor getEquipament() {
        return equipament;
    }

    public void setEquipament(LightSensor equipament) {
        this.equipament = equipament;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        MessageDeliveryService.MessageBinder messageBinder = (MessageDeliveryService.MessageBinder) iBinder;
        service = messageBinder.getService();
        service.setMessageDeliveryServiceDelegate(SensorActivity.this);
        Toast.makeText(SensorActivity.this, "Connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        service = null;
        Toast.makeText(SensorActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postReceivedMessage(String message) {
        //Do nothing
    }

    public void sendInformation() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "---";
        try {
            jsonInString = mapper.writeValueAsString(equipament);
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }

        if(service != null) {
            service.publish(jsonInString);
        }
    }
}
