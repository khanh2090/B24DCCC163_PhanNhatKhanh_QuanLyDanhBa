package com.example.quanlydanhba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    // Tài khoản cố định (hard-code), ứng dụng không lưu danh sách tài khoản
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123456";

    private EditText edtUsername, edtPassword;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenHelper.setUp(this, R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        tvError = findViewById(R.id.tvError);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> login());
    }

    private void login() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            showError(getString(R.string.error_login_empty));
            return;
        }
        if (!username.equals(USERNAME) || !password.equals(PASSWORD)) {
            showError(getString(R.string.error_login_wrong));
            return;
        }

        tvError.setVisibility(View.GONE);
        startActivity(new Intent(this, MainActivity.class));
        // Đóng màn hình đăng nhập để nút Back ở MainActivity không quay lại đây
        finish();
    }

    private void showError(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }
}
