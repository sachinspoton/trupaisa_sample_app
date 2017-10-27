package com.cyferpay.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.btn_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((Button) findViewById(R.id.btn_pay)).getText().toString().equalsIgnoreCase("Pay")) {
                    startActivity(new Intent(PayActivity.this, SuccessActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(PayActivity.this, TopupAmountActivity.class));
                }
            }
        });

        if (getIntent() != null && getIntent().hasExtra("pay_now")) {
            findViewById(R.id.ll_insufficient).setVisibility(View.GONE);
            ((Button) findViewById(R.id.btn_pay)).setText("Pay");
        }
    }
}
