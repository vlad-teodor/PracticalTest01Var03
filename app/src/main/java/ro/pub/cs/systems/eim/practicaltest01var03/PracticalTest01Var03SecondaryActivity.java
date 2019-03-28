package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var03SecondaryActivity extends AppCompatActivity {
    Button ok = null, cancel = null;
    TextView info1 = null, info2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_secondary);

        ok = findViewById(R.id.ok);
        cancel = findViewById(R.id.cancel);

        info1 = findViewById(R.id.info1);
        info2 = findViewById(R.id.info2);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getExtras() != null && intent.getExtras().containsKey("info1")) {
                info1.setText(intent.getExtras().getString("info1"));
            }
            if (intent.getExtras() != null && intent.getExtras().containsKey("info2")) {
                info2.setText(intent.getExtras().getString("info2"));
            }
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });
    }
}
