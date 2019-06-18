package com.example.win10.personality_newsapp.user;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.win10.personality_newsapp.R;

import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {
    private List<Setting> mSettingList;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);

        void onItemLongClick(View v);
    }

    public OnItemClickListener mOnItemClickListener;//第二步：声明自定义的接口

    //第三步：定义方法并暴露给外面的调用者
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View settingView;
        TextView settingname;
        ImageView settingImage;

        public ViewHolder(View view){
            super(view);
            settingView=view;
            settingname=(TextView) view.findViewById(R.id.text_user_setting);
            settingImage=(ImageView) view.findViewById(R.id.image_user_setting);
            settingView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v,getAdapterPosition());
            }
        }
    }


    public SettingAdapter(List<Setting> settingList){
        mSettingList=settingList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_body,parent,false);
       final ViewHolder holder = new ViewHolder(view);

       return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Setting setting=mSettingList.get(position);
        holder.settingImage.setImageResource(setting.getImageId());
        holder.settingname.setText(setting.getName());
    }

    @Override
    public int getItemCount() {
        return mSettingList.size();
    }
}
