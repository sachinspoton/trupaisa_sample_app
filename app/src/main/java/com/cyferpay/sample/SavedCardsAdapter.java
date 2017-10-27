package com.cyferpay.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sachin on 29/8/17.
 */

public class SavedCardsAdapter extends RecyclerView.Adapter<SavedCardsAdapter.ViewHolder> {

    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private List<SavedCardModel> mSavedCardModels;
    private int lastCheckedPosition = -1;

    public SavedCardsAdapter(Context context, OnItemClickListener pOnItemClickListener) {
        super();
        this.context = context;
        mOnItemClickListener = pOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_saved_methods, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        final SavedCardModel lSavedCardModel = this.mSavedCardModels.get(i);

        viewHolder.tvTitle.setText(lSavedCardModel.title);
        viewHolder.tvType.setText(lSavedCardModel.type);
        viewHolder.ivIcon.setImageDrawable(lSavedCardModel.icon);

        if (i == lastCheckedPosition) {
            viewHolder.cbSavedMethods.setChecked(true);
        } else {
            viewHolder.cbSavedMethods.setChecked(false);
        }

        viewHolder.mItemView.setOnClickListener(pView -> {
            if (mOnItemClickListener != null) {
                lastCheckedPosition = i;
                mOnItemClickListener.onClick(i, pView);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSavedCardModels.size();
    }

    public void setList(List<SavedCardModel> pSavedCardModels) {
        mSavedCardModels = pSavedCardModels;
    }

    public SavedCardModel getSelectedItem() {
        return mSavedCardModels.get(lastCheckedPosition);
    }

    public int selectedPosition() {
        return lastCheckedPosition;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_icon) ImageView ivIcon;
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_type) TextView tvType;
        @BindView(R.id.cb_saved_methods) CheckBox cbSavedMethods;
        private View mItemView;

        ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}