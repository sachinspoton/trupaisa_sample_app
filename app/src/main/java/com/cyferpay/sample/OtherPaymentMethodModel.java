package com.cyferpay.sample;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sachin on 29/8/17.
 */

public class OtherPaymentMethodModel {

    public String id;
    public String title;
    public int icon;

    public static List<OtherPaymentMethodModel> getData(Context pContext) {
        OtherPaymentMethodModel lOtherPaymentMethodModel = new OtherPaymentMethodModel();
        lOtherPaymentMethodModel.id = "1";
        lOtherPaymentMethodModel.title = "Net Banking";
        lOtherPaymentMethodModel.icon = R.drawable.ic_net_banking;

        OtherPaymentMethodModel lOtherPaymentMethodModel1 = new OtherPaymentMethodModel();
        lOtherPaymentMethodModel1.id = "2";
        lOtherPaymentMethodModel1.title = "Debit Cards";
        lOtherPaymentMethodModel1.icon = R.drawable.ic_debit_card;

        OtherPaymentMethodModel lOtherPaymentMethodModel2 = new OtherPaymentMethodModel();
        lOtherPaymentMethodModel2.id = "3";
        lOtherPaymentMethodModel2.title = "UPI";
        lOtherPaymentMethodModel2.icon = R.drawable.ic_upi;

        List<OtherPaymentMethodModel> lOtherPaymentMethodModels = new ArrayList<>();
        lOtherPaymentMethodModels.add(lOtherPaymentMethodModel);
        lOtherPaymentMethodModels.add(lOtherPaymentMethodModel1);
        lOtherPaymentMethodModels.add(lOtherPaymentMethodModel2);

        return lOtherPaymentMethodModels;
    }
}
