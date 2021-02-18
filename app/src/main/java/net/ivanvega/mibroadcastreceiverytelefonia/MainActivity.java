package net.ivanvega.mibroadcastreceiverytelefonia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.ivanvega.mibroadcastreceiverytelefonia.receivers.MyBroadcastReceiver;

public class MainActivity extends AppCompatActivity {
    MyBroadcastReceiver myBroadcastReceiver=
            new MyBroadcastReceiver();

    Button btnS;
    TextView lbl;
    EditText txtTel, txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnS = findViewById(R.id.btnSend);
        lbl = findViewById(R.id.lbl);
        txtTel = findViewById(R.id.txtPhone);
        txtMessage = findViewById(R.id.txtTexto);

        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent broadcast = new Intent();
                //net.ivanvega.mibroadcastreceiverytelefonia.MIBROACAST

                broadcast.setAction(getString(R.string.action_broadcast));
                broadcast.putExtra("key1", "parametro de la difusion");
                sendBroadcast(broadcast);
            }
        });

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        filter.addAction(getString(R.string.action_broadcast));
        this.registerReceiver(myBroadcastReceiver, filter);


    }

    private void enviarSMS(String tel, String msj) {
         SmsManager smsManager =  SmsManager.getDefault();

         smsManager.sendTextMessage(tel,null, msj,
         null, null);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(myBroadcastReceiver);
    }

    public void btnSMS_onclick(View v){
        enviarSMS(txtTel.getText().toString(), txtMessage.getText().toString());
    }

}