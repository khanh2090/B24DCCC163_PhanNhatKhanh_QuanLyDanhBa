package com.example.quanlydanhba;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import com.google.android.material.appbar.MaterialToolbar;

public class ContactDetailActivity extends AppCompatActivity {

    /** Khóa của liên hệ được gửi tới màn hình này và gửi trả về MainActivity. */
    public static final String EXTRA_CONTACT = "extra_contact";
    /** true khi người dùng đã nhấn Xóa liên hệ. */
    public static final String EXTRA_DELETED = "extra_deleted";

    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenHelper.setUpWithToolbar(this, R.layout.activity_contact_detail);

        // Nhận liên hệ được chọn từ MainActivity qua Intent
        contact = IntentCompat.getSerializableExtra(getIntent(), EXTRA_CONTACT, Contact.class);
        if (contact == null) {
            finish();
            return;
        }

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        TextView tvAvatar = findViewById(R.id.tvAvatar);
        TextView tvName = findViewById(R.id.tvName);
        TextView tvPhone = findViewById(R.id.tvPhone);
        TextView tvGroup = findViewById(R.id.tvGroup);
        TextView tvGender = findViewById(R.id.tvGender);
        TextView tvNote = findViewById(R.id.tvNote);
        CheckBox cbFavorite = findViewById(R.id.cbFavorite);
        Button btnCall = findViewById(R.id.btnCall);
        Button btnBack = findViewById(R.id.btnBack);
        Button btnDelete = findViewById(R.id.btnDelete);

        ContactViews.bindAvatar(tvAvatar, contact);
        tvName.setText(contact.getName());
        tvPhone.setText(ContactViews.formatPhone(contact.getPhone()));
        tvGroup.setText(contact.getGroup());
        tvGender.setText(contact.getGender());
        tvNote.setText(contact.getNote().isEmpty() ? getString(R.string.no_note) : contact.getNote());
        cbFavorite.setChecked(contact.isFavorite());

        // Đổi trạng thái yêu thích; MainActivity cập nhật khi màn hình này đóng lại
        cbFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> contact.setFavorite(isChecked));

        btnCall.setOnClickListener(v -> openDialer());
        btnBack.setOnClickListener(v -> finishWithResult(false));
        toolbar.setNavigationOnClickListener(v -> finishWithResult(false));
        btnDelete.setOnClickListener(v -> {
            Toast.makeText(this, getString(R.string.toast_deleted, contact.getName()), Toast.LENGTH_SHORT).show();
            finishWithResult(true);
        });

        // Nút Back của hệ thống cũng trả kết quả như nút Quay lại
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishWithResult(false);
            }
        });
    }

    /** Intent ngầm định ACTION_DIAL mở màn hình quay số với sẵn số của liên hệ. */
    private void openDialer() {
        Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact.getPhone()));
        try {
            startActivity(dial);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.error_no_dialer, Toast.LENGTH_SHORT).show();
        }
    }

    /** Gửi liên hệ (đã cập nhật hoặc cần xóa) về MainActivity rồi đóng màn hình. */
    private void finishWithResult(boolean deleted) {
        Intent result = new Intent();
        result.putExtra(EXTRA_CONTACT, contact);
        result.putExtra(EXTRA_DELETED, deleted);
        setResult(RESULT_OK, result);
        finish();
    }
}
