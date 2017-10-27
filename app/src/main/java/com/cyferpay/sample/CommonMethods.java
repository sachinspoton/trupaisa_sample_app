package com.cyferpay.sample;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sachin on 10/8/17.
 */

public class CommonMethods {

    public static final String CAMERA_FILE_NAME_PREFIX = "CAMERA_";

    public static int getRandomNumber() {
        Random rnd = new Random();
        return 100000 + rnd.nextInt(900000);
    }

    public static boolean validatePassword(String pPassword) {
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(pPassword);

        return matcher.matches();
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static String getDecimalFormatted(String strAmount) {
        Float lAmount = Float.parseFloat(strAmount);
        return String.format(Locale.US, "%.2f", lAmount);
    }

    public static Uri uriFromFile(Context pContext, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(pContext,
                    BuildConfig.APPLICATION_ID + ".provider",
                    file);
        } else {
            return Uri.fromFile(file);
        }
    }
}
