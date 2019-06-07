package com.example.win10.personality_newsapp.user;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.win10.personality_newsapp.R;
import com.example.win10.personality_newsapp.video_list.LoadListView;
import com.example.win10.personality_newsapp.video_list.MyAdapter;
import com.example.win10.personality_newsapp.video_list.VideoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class videoFragment extends Fragment implements LoadListView.ILoadListerner{
    public static ArrayList<VideoItem> videoList;
    MyAdapter myAdapter;
    RequestQueue requestQueue;
    LoadListView lv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_video,container,false);
        Button top=(Button)view.findViewById(R.id.toTop);
        lv = (LoadListView) view.findViewById(R.id.list);
        lv.setInterface(this);
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.setSelection(0);
            }
        });
        requestQueue = Volley.newRequestQueue(getActivity());
        videoList = new ArrayList<VideoItem>();
        RequestsData();
        myAdapter = new MyAdapter(getContext(), requestQueue, videoList, getActivity(), lv);
        lv.setAdapter(myAdapter);
        return view;
    }

    public void RequestsData() {
        try {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    "http://120.77.144.237/app/getVideoList/", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    try {
                        JSONArray data = response.getJSONArray("data");
                        Integer code = (Integer) response.get("code");
                        if (code == 0) {
                            for (int i = 0; i < 10; i++) {
                                VideoItem vi = new VideoItem();
                                JSONObject item = data.getJSONObject(i);
                                vi.set_id(item.getString("_id"));
                                vi.setTitle(item.getString("title"));
                                vi.setFrom(item.getString("from"));
                                vi.setPic(item.getString("pic"));
                                vi.setUrl(item.getString("url"));
                                vi.setTimestamp(item.getString("timestamp"));
                                videoList.add(vi);
                            }

                        }
                        myAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(), "获取失败", Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onload() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMoreData();
                lv.LoadingComplete();
            }
        }, 1000);

    }

    public void loadMoreData() {

        try {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    "http://120.77.144.237/app/getVideoList/", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    try {
                        JSONArray data = response.getJSONArray("data");
                        Integer code = (Integer) response.get("code");
                        if (code == 0) {
                            for (int i = 0; i < 6; i++) {
                                VideoItem vi = new VideoItem();
                                JSONObject item = data.getJSONObject(i);
                                vi.set_id(item.getString("_id"));
                                vi.setTitle(item.getString("title"));
                                vi.setFrom(item.getString("from"));
                                vi.setPic(item.getString("pic"));
                                vi.setUrl(item.getString("url"));
                                vi.setTimestamp(item.getString("timestamp"));
                                videoList.add(vi);
                            }
                        }
                        myAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(), "获取失败", Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
