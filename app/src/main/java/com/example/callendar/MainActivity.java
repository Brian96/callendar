package com.example.callendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> startList = new ArrayList<String>();
        startList.add("Appointment 1");
        startList.add("Appointment 2");

        ((Appointments)this.getApplication()).set_appointment(startList);

        final Button btn = (Button) findViewById(R.id.bigButton);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn.setText("dfdf");
                Intent intent = new Intent(MainActivity.this, ScheduleLists.class);
                startActivity(intent);
            }
        });

    }
}
