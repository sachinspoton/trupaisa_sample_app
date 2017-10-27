package com.cyferpay.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.LinearLayout.VERTICAL;

public class PaymentMethodActivity extends BaseActivity implements OnItemClickListener {

    RecyclerView.LayoutManager mLayManSavedCards, mLayManOtherPaymentMethods;
    SavedCardsAdapter mSavedCardsAdapter;
    OtherPaymentMethodsAdapter mOtherPaymentMethodsAdapter;
    @BindView(R.id.rv_saved_methods)
    RecyclerView rvSavedCardMethods;
    @BindView(R.id.rv_other_payment_methods)
    RecyclerView rvOtherPaymentMethods;
    @BindView(R.id.btn_pay) Button btnPay;
    String strAmount;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, PaymentMethodActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);
        ButterKnife.bind(this);

        getExtra();
        initUi();
    }

    public void getExtra() {
        strAmount = getIntent().getStringExtra("amount");
    }

    private void initUi() {
//        initializeToolbarUpEnabled();
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnPay.setText("Pay " + CommonMethods.getDecimalFormatted(strAmount));

        mLayManSavedCards = new LinearLayoutManager(this, VERTICAL, false);
        rvSavedCardMethods.setLayoutManager(mLayManSavedCards);
        mSavedCardsAdapter = new SavedCardsAdapter(this, this);
        rvSavedCardMethods.setAdapter(mSavedCardsAdapter);
        rvSavedCardMethods.setHasFixedSize(true);
        DividerItemDecoration divider = new DividerItemDecoration(rvSavedCardMethods.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_horizontal));
        rvSavedCardMethods.addItemDecoration(divider);
        mSavedCardsAdapter.setList(SavedCardModel.getData(this));

        mLayManOtherPaymentMethods = new MyLinearLayoutManager(this, VERTICAL, false);

        mOtherPaymentMethodsAdapter = new OtherPaymentMethodsAdapter(this, this);
        rvOtherPaymentMethods.setAdapter(mOtherPaymentMethodsAdapter);
        rvOtherPaymentMethods.setHasFixedSize(true);
        rvOtherPaymentMethods.setNestedScrollingEnabled(false);
        DividerItemDecoration divider1 = new DividerItemDecoration(rvOtherPaymentMethods.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_horizontal));
        rvOtherPaymentMethods.addItemDecoration(divider1);
        mOtherPaymentMethodsAdapter.setList(OtherPaymentMethodModel.getData(this));
        rvOtherPaymentMethods.setLayoutManager(mLayManOtherPaymentMethods);
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
        Intent intentToLaunch = new Intent(this, TopupPaymentExpandActivity.class);
        intentToLaunch.putExtra("amount", strAmount);
        startActivity(intentToLaunch);
        finish();
    }

    @Override
    public void onClick(int position, View pView) {

    }

    public class MyLinearLayoutManager extends LinearLayoutManager {

        private int[] mMeasuredDimension = new int[2];

        public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state,
                              int widthSpec, int heightSpec) {
            final int widthMode = View.MeasureSpec.getMode(widthSpec);
            final int heightMode = View.MeasureSpec.getMode(heightSpec);
            final int widthSize = View.MeasureSpec.getSize(widthSpec);
            final int heightSize = View.MeasureSpec.getSize(heightSpec);
            int width = 0;
            int height = 0;
            for (int i = 0; i < getItemCount(); i++) {
                measureScrapChild(recycler, i,
                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                        mMeasuredDimension);

                if (getOrientation() == HORIZONTAL) {
                    width = width + mMeasuredDimension[0];
                    if (i == 0) {
                        height = mMeasuredDimension[1];
                    }
                } else {
                    height = height + mMeasuredDimension[1];
                    if (i == 0) {
                        width = mMeasuredDimension[0];
                    }
                }
            }
            switch (widthMode) {
                case View.MeasureSpec.EXACTLY:
                    width = widthSize;
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
            }

            switch (heightMode) {
                case View.MeasureSpec.EXACTLY:
                    height = heightSize;
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
            }

            setMeasuredDimension(width, height);
        }

        private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec,
                                       int heightSpec, int[] measuredDimension) {
            try {
                View view = recycler.getViewForPosition(position);
                if (view != null) {
                    RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
                    int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                            getPaddingLeft() + getPaddingRight(), p.width);
                    int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                            getPaddingTop() + getPaddingBottom(), p.height);
                    view.measure(childWidthSpec, childHeightSpec);
                    measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
                    measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
                    recycler.recycleView(view);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
