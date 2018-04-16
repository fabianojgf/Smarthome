package org.smart.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.smart.R;
import org.smart.mqtt.equipament.model.Equipament;
import org.smart.mqtt.equipament.model.Lamp;
import org.smart.services.MessageDeliveryService;
import org.smart.services.MessageDeliveryServiceDelegate;
import org.smart.services.MessageDeliveryOptions;

import java.io.IOException;

public class LEDActivity extends AppCompatActivity implements ServiceConnection, MessageDeliveryServiceDelegate {
    private MessageDeliveryService service;
    private Lamp equipament;
    private CameraManager mCameraManager;
    private String mCameraId;
    private Boolean lightOn;

    TextView textViewLabelAddress, textViewAddress;
    Button buttonOnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led);

        textViewLabelAddress = (TextView) findViewById(R.id.textViewLabelAddress);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        buttonOnOff = (Button) findViewById(R.id.buttonOnOff);
        lightOn = false;

        equipament = new Lamp();
        equipament.setOn(lightOn);

        Boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable) {
            AlertDialog alert = new AlertDialog.Builder(LEDActivity.this)
                    .create();
            alert.setTitle("Error !!");
            alert.setMessage("Your device doesn't support flash light!");
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application
                    finish();
                    System.exit(0);
                }
            });
            alert.show();
            return;
        }
        else {
            mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            try {
                mCameraId = mCameraManager.getCameraIdList()[1];
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

            buttonOnOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (lightOn) {
                            turnOffLight();
                            lightOn = false;
                        } else {
                            turnOnLight();
                            lightOn = true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(equipament == null)
                        equipament = new Lamp();

                    equipament.setOn(lightOn);
                    sendInformation();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        MessageDeliveryOptions options =
                (MessageDeliveryOptions) getIntent().getSerializableExtra("CONNECTION");
        Intent intent = new Intent(this, MessageDeliveryService.class);
        intent.putExtra("CONNECTION", options);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    public void turnOnLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void turnOffLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postReceivedMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        Lamp object;
        try {
            object = mapper.readValue(message, Lamp.class);
            setEquipament(object);
            updateOutput(getEquipament());
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void sendInformation() {
        textViewAddress.setText(equipament.getUri());

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "---";
        try {
            jsonInString = mapper.writeValueAsString(equipament);
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }

        service.publish(service.getOptions().getTopicFullName(), jsonInString);
    }

    public void updateOutput(Equipament equipament) {
        Lamp object = (Lamp) equipament;

        textViewAddress.setText(object.getUri());
        lightOn = object.isOn();

        if(lightOn) {
            turnOnLight();
        }
        else {
            turnOffLight();
        }
    }

    public Lamp getEquipament() {
        return equipament;
    }

    public void setEquipament(Lamp equipament) {
        this.equipament = equipament;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        MessageDeliveryService.MessageBinder messageBinder = (MessageDeliveryService.MessageBinder) iBinder;
        service = messageBinder.getService();
        service.setDelegate(LEDActivity.this);
        Toast.makeText(LEDActivity.this, "Connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        service = null;
        Toast.makeText(LEDActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
    }
}
