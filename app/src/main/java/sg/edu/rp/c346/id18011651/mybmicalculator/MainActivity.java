package sg.edu.rp.c346.id18011651.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etWeight;
    EditText etHeight;
    Button btnCalc;
    Button btnReset;
    TextView tvDate;
    TextView tvBMI;
    TextView tvHealth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etHeight=findViewById(R.id.editTextHeight);
        etWeight=findViewById(R.id.editTextWeight);
        btnCalc=findViewById(R.id.calcButton);
        btnReset=findViewById(R.id.resetButton);
        tvDate=findViewById(R.id.textViewDateinfo);
        tvBMI=findViewById(R.id.textViewBMIinfo);
        tvHealth=findViewById(R.id.textViewHealth);
        final SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor prefEdit=prefs.edit();
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now=Calendar.getInstance();
                String datetime=now.get(Calendar.DAY_OF_MONTH) + "/" + (now.get(Calendar.MONTH)+1) + "/" + now.get(Calendar.YEAR) + " " + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE);
                Float weight= Float.parseFloat(etWeight.getText().toString().trim());
                Float height=Float.parseFloat(etHeight.getText().toString().trim());
                Float BMI=weight/(height*height);
                prefEdit.putFloat("bmi",BMI);
                prefEdit.putString("date",datetime);
                prefEdit.commit();
                tvBMI.setText(String.format("%.2f",BMI));
                tvDate.setText(datetime);
                if(BMI<18.5){
                    String health="You are underweight";
                    prefEdit.putString("health",health);
                    tvHealth.setText(health);
                }else if(BMI<25.0){
                    String health="Your BMI is Normal";
                    prefEdit.putString("health",health);
                    tvHealth.setText(health);
                }else if(BMI<30.0){
                    String health="You are overweight";
                    prefEdit.putString("health",health);
                    tvHealth.setText(health);
                }else{
                    String health="You are obese";
                    prefEdit.putString("health",health);
                    tvHealth.setText(health);
                }
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now=Calendar.getInstance();
                String datetime=now.get(Calendar.DAY_OF_MONTH) + "/" + (now.get(Calendar.MONTH)+1) + "/" + now.get(Calendar.YEAR) + " " + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE);
                prefEdit.putFloat("bmi",0);
                prefEdit.putString("date",datetime);
                prefEdit.putString("health","");
                prefEdit.commit();
                tvBMI.setText("");
                tvDate.setText(datetime);
                tvHealth.setText("");
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        Float BMI=prefs.getFloat("bmi",0);
        String Date=prefs.getString("date","");
        tvBMI.setText(String.format("%.2f",BMI));
        tvDate.setText(Date);
        if(BMI<18.5){
            String health="You are underweight";
            tvHealth.setText(health);
        }else if(BMI<25.0){
            String health="Your BMI is Normal";
            tvHealth.setText(health);
        }else if(BMI<30.0){
            String health="You are overweight";
            tvHealth.setText(health);
        }else{
            String health="You are obese";
            tvHealth.setText(health);
        }

    }
}
