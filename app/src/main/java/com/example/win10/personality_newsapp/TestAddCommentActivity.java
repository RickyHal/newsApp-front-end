package com.example.win10.personality_newsapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class TestAddCommentActivity extends AppCompatActivity {
    int ischecked=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_add_comment);
        final TextView textview=(TextView)findViewById(R.id.comment_text);
        final LinearLayout showstar=(LinearLayout)findViewById(R.id.show_star);
        final LinearLayout showinput=(LinearLayout)findViewById(R.id.show_input);
        final ImageView imagestar=(ImageView)findViewById(R.id.check_Is_Checked);
        final EditText edittext=(EditText)findViewById(R.id.comment_input);
        final Button sendcomment=(Button)findViewById(R.id.send_comment);
        if(ischecked==1){
            imagestar.setImageResource(R.mipmap.yes_collection);
        }else{
            imagestar.setImageResource(R.mipmap.no_collection);
        }
        imagestar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(ischecked==1){
                    imagestar.setImageResource(R.mipmap.no_collection);
                    ischecked=0;
                }else{
                    imagestar.setImageResource(R.mipmap.yes_collection);
                    ischecked=1;
                }
            }
        });
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showstar.setVisibility(View.GONE);
                showinput.setVisibility(View.VISIBLE);
                edittext.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(edittext, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        sendcomment.setEnabled(false);
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    sendcomment.setEnabled(true);
                }else{
                    sendcomment.setEnabled(false);
                }
            }
        });


    }

}
