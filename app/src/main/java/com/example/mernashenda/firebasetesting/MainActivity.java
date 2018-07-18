package com.example.mernashenda.firebasetesting;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
//import com.google.firebase.messaging;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private static final String PREF_SWITCH_NEWS = "switch_news";
    private static final String PREF_SWITCH_PROMO = "switch_promo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch switchNews = (Switch) findViewById(R.id.switchNews);
        Switch switchPromo = (Switch) findViewById(R.id.switchPromo);
        Button btnToken = (Button) findViewById(R.id.btn_token);
        Button btnKirim = (Button) findViewById(R.id.btn_kirim);

        btnToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = FirebaseInstanceId.getInstance().getToken();
                EditText tvToken = (EditText) findViewById(R.id.tv_token);
                tvToken.setText(getResources().getString(R.string.token, token));
                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                Log.i("TOKEN", token);
            }
        });

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This registration token comes from the client FCM SDKs.
                String registrationToken = "YOUR_REGISTRATION_TOKEN";

// See documentation on defining a message payload.
                //Message message = Message.builder()
                 //       .putData("score", "850")
                 //       .putData("time", "2:45")
                 //       .setToken(registrationToken)
                  //      .build();

// Send a message to the device corresponding to the provided
// registration token.
                //String response = FirebaseMessaging.getInstance().send(message);
// Response is a message ID string.
                //System.out.println("Successfully sent message: " + response);
            }
        });

        switchNews.setChecked(isSwitchChecked(PREF_SWITCH_NEWS));
        switchPromo.setChecked(isSwitchChecked(PREF_SWITCH_PROMO));

        switchNews.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                setSwitchChecked(PREF_SWITCH_NEWS, checked);

                if(checked) {
                    FirebaseMessaging.getInstance().subscribeToTopic("news");
                    Toast.makeText(getApplicationContext(), "Subscribe to News Topic" , Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("news");
                    Toast.makeText(getApplicationContext(), "Unsubscribe from News Topic" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        switchPromo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                setSwitchChecked(PREF_SWITCH_PROMO, checked);

                if(checked) {
                    FirebaseMessaging.getInstance().subscribeToTopic("promo");
                    Toast.makeText(getApplicationContext(), "Subscribe to Promo Topic", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("promo");
                    Toast.makeText(getApplicationContext(), "Unsubscribe from Promo Topic", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    private void setSwitchChecked(String permission, boolean isChecked) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(permission, isChecked);
        editor.apply();
    }

    private boolean isSwitchChecked(String permission) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getBoolean(permission, false);
    }

}
