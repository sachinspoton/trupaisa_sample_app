package com.cyferpay.sample;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sachin on 29/8/17.
 */

public class SavedCardModel {

    public String id;
    public String title;
    public String type;
    public Drawable icon;

    public static List<SavedCardModel> getData(Context pContext) {
        SavedCardModel lSavedCardModel = new SavedCardModel();
        lSavedCardModel.id = "1";
        lSavedCardModel.title = "Axis Bank ( xxxx 7820 )";
        lSavedCardModel.type = "Net Banking";
        lSavedCardModel.icon = ContextCompat.getDrawable(pContext, R.drawable.ic_axis_bank);

        SavedCardModel lSavedCardModel1 = new SavedCardModel();
        lSavedCardModel1.id = "2";
        lSavedCardModel1.title = "Visa Card ( xxxx 7820 )";
        lSavedCardModel1.type = "Debit Card";
        lSavedCardModel1.icon = ContextCompat.getDrawable(pContext, R.drawable.ic_visa_card);

        SavedCardModel lSavedCardModel2 = new SavedCardModel();
        lSavedCardModel2.id = "3";
        lSavedCardModel2.title = "SBI Bank ( xxxx 7820 )";
        lSavedCardModel2.type = "Net Banking";
        lSavedCardModel2.icon = ContextCompat.getDrawable(pContext, R.drawable.ic_sbi_bank);

        List<SavedCardModel> lSavedCardModels = new ArrayList<>();
        lSavedCardModels.add(lSavedCardModel);
        lSavedCardModels.add(lSavedCardModel1);
        lSavedCardModels.add(lSavedCardModel2);

        return lSavedCardModels;
    }
}
