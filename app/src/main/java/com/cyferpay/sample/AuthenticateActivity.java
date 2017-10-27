package com.cyferpay.sample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import io.chirp.sdk.ChirpSDK;
import io.chirp.sdk.ChirpSDKListener;
import io.chirp.sdk.model.Chirp;
import io.chirp.sdk.model.ChirpError;

public class AuthenticateActivity extends BaseActivity {

    private static final int RESULT_REQUEST_RECORD_AUDIO = 0;
    private static final String TAG = "CyferPay";
    CountDownTimer lTimer;
    Handler handler;
    Runnable runnable;
    private ChirpSDK chirpSDK;
    private ChirpSDKListener chirpSDKListener = new ChirpSDKListener() {

        /*------------------------------------------------------------------------------
        * onChirpHeard is triggered when a Chirp tone is received.
        * Obtain the chirp's 10-character identifier with `getIdentifier`.
        *----------------------------------------------------------------------------*/
        @Override
        public void onChirpHeard(Chirp chirp) {
            Log.d(TAG, "onChirpHeard: " + chirp.getIdentifier());
            showDummyProgress(1);
        }

        /*------------------------------------------------------------------------------
        * onChirpHearStarted is triggered when the beginning of Chirp tone is heard
        *----------------------------------------------------------------------------*/
        @Override
        public void onChirpHearStarted() {
            Log.d(TAG, "Chirp Hear Started");
        }

        /*------------------------------------------------------------------------------
        * onChirpHearFailed is triggered when the beginning of Chirp tone is heard but it
        * subsequently fails to decode the identifier
        *----------------------------------------------------------------------------*/
        @Override
        public void onChirpHearFailed() {
            Log.d(TAG, "Chirp Hear Failed");
        }

        /*------------------------------------------------------------------------------
        * onChirpError is triggered when an error occurs -- for example,
        * authentication failure or muted device.
        *
        * See the documentation on ChirpError for possible error codes.
        *----------------------------------------------------------------------------*/
        @Override
        public void onChirpError(ChirpError chirpError) {
            Log.d(TAG, "onChirpError: " + chirpError.getMessage());
        }
    };

    private boolean doWeHaveRecordAudioPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RESULT_REQUEST_RECORD_AUDIO);
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (doWeHaveRecordAudioPermission()) {
            chirpSDK.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        chirpSDK.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);

        intiUi();
    }

    private void intiUi() {
        soundWaveAnim();
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        waitUI();
        initChirp();
    }

    private void soundWaveAnim() {
        LottieAnimationView lavSoundWave = findViewById(R.id.animation_view);
        lavSoundWave.setImageAssetsFolder("images/");
        lavSoundWave.setAnimation("sound_wave.json");
        lavSoundWave.loop(true);
        lavSoundWave.playAnimation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (lTimer != null) lTimer.cancel();
        if (handler != null && runnable != null) handler.removeCallbacks(runnable);
    }

    private void waitUI() {
        handler = new Handler();
        runnable = () -> {
            showOtpWaiting();
        };
        handler.postDelayed(runnable, 10000);
    }

    public void showOtpWaiting() {
        startActivity(new Intent(AuthenticateActivity.this, OtpWaitingActivity.class));
        finish();
    }

    private void initChirp() {
        chirpSDK = new ChirpSDK(this, getString(R.string.chirp_app_key), getString(R.string.chirp_app_secret));
        chirpSDK.setListener(chirpSDKListener);
    }

    @Override
    public synchronized void performAction() {
        super.performAction();
        Toast.makeText(this, "Authenticated Successfully.", Toast.LENGTH_SHORT).show();
        showOtpWaiting();
    }
}
