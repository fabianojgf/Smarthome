package org.smart.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import org.smart.R;
import org.smart.services.MessageDeliveryOptions;

public class ConfigActivity extends AppCompatActivity {
    EditText editTextBrokerHost, editTextBrokerPort,
            editTextClientId, editTextTopicDomain,
            editTextTopicTargetId;
    RadioGroup radioGroup;
    Button buttonOk, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        editTextBrokerHost = (EditText) findViewById(R.id.editTextBrokerHost);
        editTextBrokerPort = (EditText) findViewById(R.id.editTextBrokerPort);
        editTextClientId = (EditText) findViewById(R.id.editTextClientId);
        editTextTopicDomain = (EditText) findViewById(R.id.editTextTopicDomain);
        editTextTopicTargetId = (EditText) findViewById(R.id.editTextTopicTargetId);

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

    public MessageDeliveryOptions makeConnectInfo() {
        MessageDeliveryOptions options = new MessageDeliveryOptions();
        options.setBrokerUri("tcp://" + editTextBrokerHost.getText().toString() + ":" + editTextBrokerPort.getText());
        options.setClientId(editTextClientId.getText().toString());
        options.setTopicDomain(editTextTopicDomain.getText().toString());
        options.setTopicTargetId(editTextTopicTargetId.getText().toString());

        return options;
    }
}
