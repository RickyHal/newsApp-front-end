package com.example.win10.personality_newsapp.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.win10.personality_newsapp.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private EditText et_username;
    private EditText et_userpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
          button=(Button) findViewById(R.id.click_in);
          et_username = (EditText) findViewById(R.id.et_username);
          et_userpwd = (EditText) findViewById(R.id.et_password);
          button.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.click_in:
                String username = et_username.getText().toString();
                String userpwd = et_userpwd.getText().toString();
                if (username.equals("admin") && userpwd.equals("123456")) {
                    AlertDialog.Builder dialog=new AlertDialog.Builder(LoginActivity.this);
                    dialog.setMessage("登录成功");
                    dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(LoginActivity.this,LoginedActivity.class);
                            startActivity(intent);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "账号和密码不匹配，请重新输入", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
