
//  Mohsenul Kabir Mithun
//  ID# 2019-3-60-046

package edu.ewubd.simplecalculator;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    private TextView tvInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInput = findViewById(R.id.tvInput);
        setButtonClickListeners();
    }

    private void setButtonClickListeners() {
        int[] buttonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnDot, R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDiv
        };

        for (int buttonId : buttonIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String buttonText = button.getText().toString();
                    String currentText = tvInput.getText().toString();
                    tvInput.setText(currentText + buttonText);
                }
            });
        }

        Button deleteButton = findViewById(R.id.resultRemoveButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = tvInput.getText().toString();
                if (!currentText.isEmpty()) {
                    tvInput.setText(currentText.substring(0, currentText.length() - 1));
                }
            }
        });

        deleteButton.setOnLongClickListener(new View.OnLongClickListener() {
            private Handler handler = new Handler();
            private boolean isLongClick = false;

            @Override
            public boolean onLongClick(View v) {
                isLongClick = true;
                handler.postDelayed(characterDeletionRunnable, 20);
                return true;
            }

            private Runnable characterDeletionRunnable = new Runnable() {
                @Override
                public void run() {
                    if (isLongClick) {
                        String currentText = tvInput.getText().toString();
                        if (!currentText.isEmpty()) {
                            tvInput.setText(currentText.substring(0, currentText.length() - 1));
                            handler.postDelayed(this, 20);
                        }
                    }
                }
            };
        });

        findViewById(R.id.btnEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expressionStr = tvInput.getText().toString();
                try {
                    Expression expression = new ExpressionBuilder(expressionStr).build();
                    double result = expression.evaluate();
                    tvInput.setText(String.valueOf(result));
                } catch (ArithmeticException e) {
                    tvInput.setText("Error: Arithmetic error");
                } catch (IllegalArgumentException e) {
                    tvInput.setText("Error: Invalid expression");
                } catch (Exception e) {
                    tvInput.setText("Error occurred while evaluating the expression");
                }
            }
        });
    }
}
