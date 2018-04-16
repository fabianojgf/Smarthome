package org.smart.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import org.smart.R;
import org.smart.gateway.service.options.SocketClientOptions;

public class ConfigActivity extends AppCompatActivity {
    EditText editTextHost, editTextHostPort, editTextLocalPort,
            editTextClientName, editTextTargetName;
    RadioGroup radioGroup;
    Button buttonOk, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        editTextHost = (EditText) findViewById(R.id.editTextHost);
        editTextHostPort = (EditText) findViewById(R.id.editTextHostPort);
        editTextLocalPort = (EditText) findViewById(R.id.editTextLocalPort);
        editTextClientName = (EditText) findViewById(R.id.editTextClientName);
        editTextTargetName = (EditText) findViewById(R.id.editTextTargetName);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        buttonOk = (Button) findViewById(R.id.buttonOk);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<?> c = (radioGroup.getCheckedRadioButtonId() == R.id.radioLightSensor) ? SensorActivity.class : LEDActivity.class;
                Intent intent = new Intent(ConfigActivity.this, c);
                intent.putExtra("CONNECTION", makeConnectInfo());
                startActivity(intent);
            }
        });
    }

    public SocketClientOptions makeConnectInfo() {
        SocketClientOptions options = new SocketClientOptions();
        options.setHost(editTextHost.getText().toString());
        options.setHostPort(Integer.valueOf(editTextHostPort.getText().toString()));
        options.setLocalPort(Integer.valueOf(editTextLocalPort.getText().toString()));
        options.setClientName(editTextClientName.getText().toString());
        options.setTargetName(editTextTargetName.getText().toString());

        return options;
    }
}
