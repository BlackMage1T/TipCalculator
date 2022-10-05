package com.example.tiptime;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiptime.databinding.ActivityMainBinding;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.calculateButton.setOnClickListener(view -> calculateTip());
        binding.costOfServiceEditText.setOnKeyListener((view, i, keyEvent) -> handleKeyEvent(view, i));
    }

    private void calculateTip() {
        String stringInTextField = binding.costOfServiceEditText.getText().toString();
        Double cost = Double.parseDouble(stringInTextField);

        if (cost == null || cost == 0.0) {
            displayTip(0.0);
            return;
        }

        Double tipPercentage = 0.0;
        switch (binding.tipOptions.getCheckedRadioButtonId()){
            case R.id.option_twenty_percent:
                tipPercentage = 0.20;
            case R.id.option_eighteen_percent:
                tipPercentage = 0.18;
            case R.id.option_fifteen_percent:
                tipPercentage = 0.15;
        }

        Double tip = tipPercentage*cost;
        Boolean roundUp = binding.roundUpSwitch.isChecked();
        if (roundUp == Boolean.TRUE){
            tip = Math.ceil(tip);
        }

        displayTip(tip);
    }

    private static double parseStringToDouble(String value, double defaultValue) {
        return value == null || value.isEmpty() ? defaultValue : Double.parseDouble(value);
    }

    private void displayTip(double tip){
        String formattedTip = NumberFormat.getCurrencyInstance().format(tip);
        binding.tipResult.setText(getString(R.string.tip_amount, formattedTip));
    }
    private boolean handleKeyEvent(View view, int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return true;
        }
        return false;
    }

}
