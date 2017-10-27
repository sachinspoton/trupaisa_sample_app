package com.cyferpay.sample;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class TopupPaymentExpandActivity extends BaseActivity implements TextWatcher {

    final int MY_SCAN_REQUEST_CODE = 3423;
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.et_card_number)
    EditText etCardNumber;
    @BindView(R.id.et_exp_month)
    EditText etExpMonth;
    @BindView(R.id.et_exp_year)
    EditText etExpYear;
    @BindView(R.id.et_cvv)
    EditText etCvv;
    String strAmount;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, TopupPaymentExpandActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);
        ButterKnife.bind(this);

        getExtra();
        initUi();
    }

    public void getExtra() {
        strAmount = getIntent().getStringExtra("amount");
    }

    private void initUi() {
//        initializeToolbarUpEnabled();
        btnPay.setText("Pay " + CommonMethods.getDecimalFormatted(strAmount));
        etCardNumber.addTextChangedListener(this);
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_pay)
    public void showTopUpPaymentExpandScreen() {
        if (etCardNumber.getText().toString().trim().length() >= 16) {
            if (etExpMonth.getText().toString().trim().length() == 2) {
                if (etExpYear.getText().toString().trim().length() == 2) {
                    if (etCvv.getText().toString().trim().length() >= 3) {
                        showDummyProgress(1);
                    } else {
                        etCvv.setError("not valid CVV");
                    }
                } else {
                    etExpYear.setError("not valid year");
                }
            } else {
                etExpMonth.setError("not valid month");
            }
        } else {
            etCardNumber.setError("Not a valid card number");
        }
    }

    @Override
    public synchronized void performAction() {
        super.performAction();
        Toast.makeText(this, "Your account has been topped up with Rs. " + strAmount, Toast.LENGTH_SHORT).show();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        Notification notification = builder.setContentTitle("CyferPay")
                .setContentText("Your account has been topped up with Rs. " + strAmount)
                .setTicker("Success")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
        finishAffinity();
        Intent intent = new Intent(this, PayActivity.class);
        intent.putExtra("pay_now", true);
        startActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String initial = s.toString();
        // remove all non-digits characters
        String processed = initial.replaceAll("\\D", "");
        // insert a space after all groups of 4 digits that are followed by another digit
        processed = processed.replaceAll("(\\d{4})(?=\\d)", "$1 ");
        // to avoid stackoverflow errors, check that the processed is different from what's already
        //  there before setting
        if (!initial.equals(processed)) {
            // set the value
            s.replace(0, initial.length(), processed);
        }
    }

    @OnClick(R.id.btn_scan)
    void scanCard() {
        Intent scanIntent = new Intent(this, CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_SCAN_REQUEST_CODE) {
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
//                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";
                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );
                if (scanResult.isExpiryValid()) {
//                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                    etCardNumber.setText(scanResult.getFormattedCardNumber());
                    String month = String.valueOf(scanResult.expiryMonth);
                    if (month.length() < 2) month = "0" + month;
                    etExpMonth.setText(month);
                    etExpYear.setText(String.valueOf(scanResult.expiryYear).substring(1, String.valueOf(scanResult.expiryYear).length()));
                    etCvv.setText(scanResult.cvv);
                } else {
                    Toast.makeText(this, "Card is not valid anymore.", Toast.LENGTH_LONG).show();
                }
//                if (scanResult.cvv != null) {
//                    // Never log or display a CVV
//                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
//                }
//                if (scanResult.postalCode != null) {
//                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
//                } else {
//                    resultDisplayStr = "Scan was canceled.";
//                }
                // do something with resultDisplayStr, maybe display it in a textView
                // resultTextView.setText(resultDisplayStr);
            }
            // else handle other activity results
        }
    }
}
