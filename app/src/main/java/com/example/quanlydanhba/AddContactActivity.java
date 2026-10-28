package com.example.quanlydanhba;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class AddContactActivity extends AppCompatActivity {

    /** Khóa của đối tượng Contact trả về cho MainActivity. */
    public static final String EXTRA_CONTACT = "extra_new_contact";

    private EditText edtName, edtPhone, edtNote;
    private Spinner spnGroup;
    private RadioGroup rgGender;
    private CheckBox cbFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenHelper.setUpWithToolbar(this, R.layout.activity_add_contact);

        // Nút ← trên thanh tiêu đề: đóng màn hình, không lưu
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtNote = findViewById(R.id.edtNote);
        spnGroup = findViewById(R.id.spnGroup);
        rgGender = findViewById(R.id.rgGender);
        cbFavorite = findViewById(R.id.cbFavorite);
        Button btnSave = findViewById(R.id.btnSave);

        // Nạp các nhóm (Gia đình, Bạn bè, Công việc, Khác) từ strings.xml vào Spinner
        ArrayAdapter<CharSequence> groupAdapter = ArrayAdapter.createFromResource(
                this, R.array.contact_groups, android.R.layout.simple_spinner_item);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGroup.setAdapter(groupAdapter);

        btnSave.setOnClickListener(v -> saveContact());
    }

    private void saveContact() {
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();

        if (name.isEmpty()) {
            edtName.setError(getString(R.string.error_name_empty));
            edtName.requestFocus();
            return;
        }
        if (!isValidPhone(phone)) {
            edtPhone.setError(getString(R.string.error_phone_invalid));
            edtPhone.requestFocus();
            return;
        }
        if (ContactStore.phoneExists(phone)) {
            edtPhone.setError(getString(R.string.error_phone_exists));
            edtPhone.requestFocus();
            return;
        }

        String group = spnGroup.getSelectedItem().toString();
        RadioButton checkedGender = findViewById(rgGender.getCheckedRadioButtonId());
        String gender = checkedGender.getText().toString();
        String note = edtNote.getText().toString().trim();
        Contact contact = new Contact(name, phone, group, gender, cbFavorite.isChecked(), note);

        // Trả đối tượng Contact mới về MainActivity qua Intent
        Intent result = new Intent();
        result.putExtra(EXTRA_CONTACT, contact);
        setResult(RESULT_OK, result);
        Toast.makeText(this, getString(R.string.toast_saved, name), Toast.LENGTH_SHORT).show();
        finish();
    }

    /** Hợp lệ khi gồm đúng 10 chữ số và bắt đầu bằng 0. */
    private boolean isValidPhone(String phone) {
        return phone.matches("0\\d{9}");
    }
}
