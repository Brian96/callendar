package com.example.callendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleSchedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_schedule);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        String app_name = ((Appointments)this.getApplication()).get_appointment_at(position);

        final TextView temp_tv = findViewById(R.id.single_schedule_app_name);
        temp_tv.setText(app_name);

    }
}
