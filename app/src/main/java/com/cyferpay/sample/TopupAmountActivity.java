package com.cyferpay.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TopupAmountActivity extends BaseActivity {

    @BindView(R.id.et_amount) EditText etAmount;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, TopupAmountActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_amount);
        ButterKnife.bind(this);

        initUi();
    }

    private void initUi() {
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        initializeToolbarUpEnabled();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_next)
    public void showPaymentMethodScreen() {
        if (etAmount.getText().toString().trim().length() > 0) {
            Intent intentToLaunch = new Intent(this, PaymentMethodActivity.class);
            intentToLaunch.putExtra("amount", etAmount.getText().toString());
            startActivity(intentToLaunch);
            finish();
        } else {
            etAmount.setError("Please enter amount");
        }
    }
}
