package com.example.callendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.widget.DatePicker;


public class AutoSingleSchedule extends AppCompatActivity {
    boolean date_spinner_b = true;
    boolean time_spinner_b = true;
    boolean loc_spinner_b = true;
    boolean pers_spinner_b = true;
    EditText date_p;
    EditText time_p;

    DatePickerDialog picker;
    TimePickerDialog t_picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_auto_schedule);
        String[]time_list = new String[]{"13:12", "12:30", "15:20"};
        String[]date_list = new String[]{"2020-03-12", "2020-04-23", "2020-04-21"};
        String[]location_list = new String[]{"restaurant", "home"};
        String[]person_list = new String[]{"Jone", "Peter", "Alan"};

        date_p=(EditText) findViewById(R.id.text_date);
        date_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_spinner_b = false;
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AutoSingleSchedule.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date_p.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        time_p =(EditText) findViewById(R.id.text_time);
        time_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_spinner_b = false;
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                t_picker = new TimePickerDialog(AutoSingleSchedule.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                time_p.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                t_picker.show();
            }
        });




        final android.widget.Spinner time_s = findViewById(R.id.spinner_time);
        final android.widget.Spinner date_s = findViewById(R.id.spinner_date);
        final android.widget.Spinner loc_s = findViewById(R.id.spinner_loc);
        final android.widget.Spinner per_s = findViewById(R.id.spinner_pers);



        ArrayAdapter<String> adapter_time = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item , time_list);
        time_s.setAdapter(adapter_time);
        ArrayAdapter<String> adapter_date = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, date_list);
        date_s.setAdapter(adapter_date);
        ArrayAdapter<String> adapter_loc = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, location_list);
        loc_s.setAdapter(adapter_loc);
        ArrayAdapter<String> adapter_pers = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, person_list);
        per_s.setAdapter(adapter_pers);

        final TextView loc_t_m = (TextView) findViewById(R.id.text_loc);
        final TextView pers_t_m = (TextView) findViewById(R.id.text_pers);

        loc_t_m.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               loc_spinner_b = false;
            }
        });
        pers_t_m.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                pers_spinner_b = false;
            }
        });

        final TextView eve_t_m = (TextView) findViewById(R.id.text_eve);

        final Button btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AutoSingleSchedule.this, Added.class);
                if (date_spinner_b) {
                    intent.putExtra("date", date_s.getSelectedItem().toString());
                } else{
                    intent.putExtra("date",date_p.getText().toString());
                }
                if (time_spinner_b){
                    intent.putExtra("time",time_s.getSelectedItem().toString());
                }else{
                    intent.putExtra("time",time_p.getText().toString());
                }


                if (loc_spinner_b){
                    intent.putExtra("loc",loc_s.getSelectedItem().toString());
                } else {

                    intent.putExtra("loc",loc_t_m.getText().toString());
                }
                if (pers_spinner_b){
                    intent.putExtra("person",per_s.getSelectedItem().toString());
                } else{
                    intent.putExtra("person",pers_t_m.getText().toString());
                }

                intent.putExtra("event",eve_t_m.getText().toString());
                startActivity(intent);
            }
        });

    }


}