package com.example.calculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView Result,Calculation;
    MaterialButton buttonC, buttonOpenBracket, buttonCloseBracket;
    MaterialButton buttonDivide, buttonMultiply, buttonAdd, buttonSubtract, buttonEquals;
    MaterialButton button1, button2, button3, button4, button5, button6, button7, button8, button9, button0;
    MaterialButton buttonAC, buttonDot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Result = findViewById(R.id.Result);
        Calculation = findViewById(R.id.Calculation);

        assign(buttonC, R.id.button_c);
        assign(buttonOpenBracket, R.id.button_open_bracket);
        assign(buttonCloseBracket, R.id.button_close_bracket);
        assign(buttonDivide, R.id.button_divide);
        assign(buttonMultiply, R.id.button_multiply);
        assign(buttonAdd, R.id.button_plus);
        assign(buttonSubtract, R.id.button_subtract);
        assign(buttonEquals, R.id.button_equal);
        assign(button1, R.id.button_1);
        assign(button2, R.id.button_2);
        assign(button3, R.id.button_3);
        assign(button4, R.id.button_4);
        assign(button5, R.id.button_5);
        assign(button6, R.id.button_6);
        assign(button7, R.id.button_7);
        assign(button8, R.id.button_8);
        assign(button9, R.id.button_9);
        assign(button0, R.id.button_0);
        assign(buttonAC, R.id.button_AC);
        assign(buttonDot, R.id.button_dot);
    }

    void assign(MaterialButton btn, int id)
    {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MaterialButton button = (MaterialButton) v;
        String buttonText = button.getText().toString();
        String operatedData = Calculation.getText().toString();

        if(buttonText.equals("AC"))
        {
            Calculation.setText("");
            Result.setText("0");
            return;
        }

        if(buttonText.equals("="))
        {
            if(operatedData.isEmpty()){
                return;
            }
            Calculation.setText(Result.getText());
            return;
        }

        if(buttonText.equals("C"))
        {
            if (!operatedData.isEmpty()) {
                operatedData = operatedData.substring(0, operatedData.length() - 1);
                Calculation.setText(operatedData);
                if (operatedData.isEmpty()) {
                    Result.setText("0");
                } else {
                    String finalResult = getResult(operatedData);
                    if (!finalResult.equals("Error")) {
                        Result.setText(finalResult);
                    }
                }
            }
            return;
        }
        else
        {
            operatedData = operatedData + buttonText;
        }

        Calculation.setText(operatedData);

        String finalResult = getResult(operatedData);

        if(!finalResult.equals("Error"))
        {
            Result.setText(finalResult);
        }
    }

    String getResult(String data)
    {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript",1,null).toString();
            if(finalResult.endsWith(".0"))
            {
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        }
        catch(Exception e){
            return "Error";
        }
    }
}