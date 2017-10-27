package com.cyferpay.sample;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Random;

public class OtpWaitingActivity extends AppCompatActivity {

    CountDownTimer lTimer;
    int randomNumber;
    Handler handler;
    Runnable runnable;
    TextView tvCountDown;
    Handler handler2;
    Runnable runnable2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_waiting);
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvCountDown = findViewById(R.id.tv_count_down);
        initUi();
    }

    private void initUi() {
        initAnim();
        showOtpWaitingUi();
    }

    private void initAnim() {
        LottieAnimationView lottieAnimationView = findViewById(R.id.animation_view);
        lottieAnimationView.setImageAssetsFolder("images/");
        lottieAnimationView.setAnimation("data.json");
        lottieAnimationView.loop(true);
        lottieAnimationView.playAnimation();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showOtpWaitingUi() {
        handler2 = new Handler();
        runnable2 = () -> showPayScreen();
        handler2.postDelayed(runnable2, 11000);

        showCountDown();
        sendOtp();
    }

    private void showOTPFailUi() {
        tvCountDown.setText("00:00 secs left");
        tvCountDown.setTextColor(ContextCompat.getColor(this, R.color.faint_white));
    }

    private void sendOtp() {
        handler = new Handler();
        runnable = () -> {
            randomNumber = getRandomNumber();
            generateLocalNotification(randomNumber);
        };
        handler.postDelayed(runnable, 10000);
    }

    private void showCountDown() {

        lTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCountDown.setText("00:" + millisUntilFinished / 1000 + " secs left");
            }

            @Override
            public void onFinish() {
                showOTPFailUi();
            }
        }.start();
    }

    public void showPayScreen() {
        startActivity(new Intent(this, PayActivity.class));
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (lTimer != null) lTimer.cancel();
        if (handler != null && runnable != null) handler.removeCallbacks(runnable);
        if (handler2 != null && runnable2 != null) handler2.removeCallbacks(runnable2);
    }

    private void generateLocalNotification(int pRandomNumber) {

        Intent notificationIntent = new Intent(this, OtpWaitingActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(OtpWaitingActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        Notification notification = builder.setContentTitle("CyferPay SMS Message")
                .setContentText("Your CyferPay Activation code is:" + pRandomNumber)
                .setTicker("OTP")
                .setSmallIcon(android.R.drawable.sym_action_email)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    private int getRandomNumber() {
        Random rnd = new Random();
        return 100000 + rnd.nextInt(900000);
    }
}
