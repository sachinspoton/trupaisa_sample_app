package com.cyferpay.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sachin on 29/8/17.
 */

public class OtherPaymentMethodsAdapter extends RecyclerView.Adapter<OtherPaymentMethodsAdapter.ViewHolder> {

    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private List<OtherPaymentMethodModel> mOtherPaymentMethodModels;

    public OtherPaymentMethodsAdapter(Context context, OnItemClickListener pOnItemClickListener) {
        super();
        this.context = context;
        mOnItemClickListener = pOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_other_payment_methods, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        OtherPaymentMethodModel lOtherPaymentMethodModel = mOtherPaymentMethodModels.get(i);

        viewHolder.tvTitle.setCompoundDrawablesWithIntrinsicBounds(lOtherPaymentMethodModel.icon, 0, 0, 0);
        viewHolder.tvTitle.setText(lOtherPaymentMethodModel.title);

        viewHolder.mItemView.setOnClickListener(pView -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onClick(i, pView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOtherPaymentMethodModels.size();
    }

    public void setList(List<OtherPaymentMethodModel> pOtherPaymentMethodModels) {
        mOtherPaymentMethodModels = pOtherPaymentMethodModels;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title) TextView tvTitle;
        private View mItemView;

        ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}