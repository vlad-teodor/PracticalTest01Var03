package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ro.pub.cs.systems.eim.PracticalTest01Var03Service;

public class PracticalTest01Var03MainActivity extends AppCompatActivity {
    Button displayButton = null;
    EditText text1 = null, text2 = null;
    CheckBox checkBox1 = null, checkBox2 = null;
    TextView textView = null;
    Button navigate = null;

    boolean service_started = false;
    private final static int SECONDARY_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_main);

        displayButton = findViewById(R.id.display);
        text1 = findViewById(R.id.editText1);
        text2 = findViewById(R.id.editText2);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        textView = findViewById(R.id.textView);
        navigate = findViewById(R.id.navigate);


        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "";
                boolean start = true;
                if (checkBox1.isChecked()) {
                    if (text1.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Eroare", Toast.LENGTH_SHORT).show();
                    } else {
                        text += text1.getText().toString() + " ";
                    }
                } else {
                    start = false;
                }
                if (checkBox2.isChecked()) {
                    if (text2.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Eroare", Toast.LENGTH_SHORT).show();
                    } else {
                        text += text2.getText().toString();
                    }
                } else {
                    start = false;
                }
                textView.setText(text);
                if (start) {
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
                    intent.putExtra("info1", text1.getText().toString());
                    intent.putExtra("info2", text2.getText().toString());
                    getApplicationContext().startService(intent);
                }
            }
        });
        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03SecondaryActivity.class);
                intent.putExtra("info1", text1.getText().toString());
                intent.putExtra("info2", text2.getText().toString());
                startActivityForResult(intent, SECONDARY_CODE);
                service_started = true;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("textNume", text1.getText().toString());
        outState.putString("textGrupa", text2.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("textNume")) {
                text1.setText(savedInstanceState.getString("textNume"));
            }
            if (savedInstanceState.containsKey("textGrupa")) {
                text2.setText(savedInstanceState.getString("textGrupa"));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SECONDARY_CODE) {
            Toast.makeText(this, resultCode == 1 ? "OK" : "Cancel", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var03Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, new IntentFilter("actiune"));
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }


    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("[Message]", intent.getStringExtra("message"));
        }
    }
}
