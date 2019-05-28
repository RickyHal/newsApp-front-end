package com.example.win10.personality_newsapp.user;

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

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView settingname;
        ImageView settingImage;

        public ViewHolder(View view){
            super(view);
            settingname=(TextView) view.findViewById(R.id.text_user_setting);
            settingImage=(ImageView) view.findViewById(R.id.image_user_setting);
        }
    }

    public SettingAdapter(List<Setting> settingList){
        mSettingList=settingList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_body,parent,false);
       ViewHolder holder=new ViewHolder(view);
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
