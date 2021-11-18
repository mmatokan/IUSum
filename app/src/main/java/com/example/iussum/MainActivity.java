package com.example.iussum;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    Button calculate;
    EditText vpsInput;
    TextView bodovi;
    TextView nagrada;

    double vps;
    int bod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        calculate = findViewById(R.id.button_calculate);
        vpsInput = findViewById(R.id.input_vps);
        bodovi = findViewById(R.id.broj_bodova);
        nagrada = findViewById(R.id.nagrada);

        vpsInput.addTextChangedListener(new NumberTextWatcherForThousand(vpsInput));

        vpsInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpsInput.setText("");
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(MainActivity.this);
                start();
            }
        });

        vpsInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    start();
                }
                return false;
            }
        });

    }

    private void start() {
        if(NumberTextWatcherForThousand.trimCommaOfString(vpsInput.getText().toString()).equals("")){
            vps = 0;
        }
        else{
            vps = Double.parseDouble(NumberTextWatcherForThousand.trimCommaOfString(vpsInput.getText().toString()));
        }


        bod = calculatePoints(vps);
        bodovi.setText(String.valueOf(bod));
        nagrada.setText(bod*10 + " kn");
    }

    private int calculatePoints(double vps) {
        if(vps<=0){
            return 0;
        }
        else if(vps<2500.01){
            return 25;
        }
        else if(vps<5000.01){
            return 50;
        }
        else if(vps<10000.01){
            return 75;
        }
        else if(vps<100000.01){
            return 100;
        }
        else if(vps<250000.01){
            return 250;
        }
        else if(vps<500000.01){
            return 500;
        }
        else{
            int b = 500;
            if(vps<5000000.01){
                b += (vps-500000)/1000;
                return b;
            }
            else if(vps<10000000.01){
                b += 4500;
                b += (vps-5000000)/2000;
                return b;
            }
            else{
                b += 7000;
                b += (vps-10000000)/5000;
                return b;
            }
        }
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
