package com.baidu.location.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.baidu.location.demo.util.Http;
import com.baidu.location.demo.util.HttpResponse;
import com.baidu.location.demo.vo.LoginBase;


// Login page
public class LoginActivity extends AppCompatActivity {

    // controllers
    private EditText et_username;   // username controller
    private EditText et_password;   // password controller
    private Button btn_login;       // login button

    private Button btn_register;       // register button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        final SharedPreferences sharedPreferences = getSharedPreferences("wms", Context.MODE_PRIVATE);
        et_username.setText(sharedPreferences.getString("username", ""));

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginBase loginBase = new LoginBase();
                loginBase.userCode = String.valueOf(et_username.getText());
                loginBase.loginPwd = String.valueOf(et_password.getText());

                HttpResponse<LoginBase> response = Http.Request("login", loginBase, LoginBase.class);
                if (response.Code.equals("200")) {
                    sharedPreferences
                            .edit()
                            .putString("username", loginBase.userCode)
                            .putString("identity", loginBase.loginPwd)
                            .commit();
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, response.Msg, Toast.LENGTH_SHORT).show();
                }
                et_password.setText("");
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginBase loginBase = new LoginBase();
                loginBase.userCode = String.valueOf(et_username.getText());
                loginBase.loginPwd = String.valueOf(et_password.getText());

                HttpResponse<LoginBase> response = Http.Request("register", loginBase, LoginBase.class);
                if (response.Code.equals("200")) {
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, response.Msg, Toast.LENGTH_SHORT).show();
                }
                et_password.setText("");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        et_password.clearFocus();
    }
}