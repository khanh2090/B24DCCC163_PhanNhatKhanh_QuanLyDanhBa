package com.example.quanlydanhba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<Contact> contacts = ContactStore.getContacts();
    private ContactAdapter adapter;
    private TextView tvTotal, tvFavorite, tvEmpty;
    private ListView lvContacts;

    // Nhận đối tượng Contact mới do AddContactActivity trả về
    private final ActivityResultLauncher<Intent> addContactLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() != RESULT_OK || result.getData() == null) {
                    return;
                }
                Contact contact = IntentCompat.getSerializableExtra(
                        result.getData(), AddContactActivity.EXTRA_CONTACT, Contact.class);
                if (contact != null) {
                    contacts.add(contact);
                    refresh();
                    // Cuộn tới liên hệ vừa thêm ở cuối danh sách
                    lvContacts.smoothScrollToPosition(contacts.size() - 1);
                }
            });

    // Nhận kết quả từ ContactDetailActivity: liên hệ đã đổi trạng thái yêu thích hoặc đã bị xóa
    private final ActivityResultLauncher<Intent> detailLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                Intent data = result.getData();
                if (result.getResultCode() != RESULT_OK || data == null) {
                    return;
                }
                Contact contact = IntentCompat.getSerializableExtra(
                        data, ContactDetailActivity.EXTRA_CONTACT, Contact.class);
                if (contact == null) {
                    return;
                }
                int index = ContactStore.indexOfPhone(contact.getPhone());
                if (index < 0) {
                    return;
                }
                if (data.getBooleanExtra(ContactDetailActivity.EXTRA_DELETED, false)) {
                    contacts.remove(index);
                } else {
                    contacts.set(index, contact);
                }
                refresh();
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenHelper.setUpWithToolbar(this, R.layout.activity_main);

        tvTotal = findViewById(R.id.tvTotal);
        tvFavorite = findViewById(R.id.tvFavorite);
        tvEmpty = findViewById(R.id.tvEmpty);
        lvContacts = findViewById(R.id.lvContacts);
        Button btnAdd = findViewById(R.id.btnAdd);

        // Dòng gợi ý ở cuối danh sách, không nhấn được
        TextView footer = (TextView) getLayoutInflater().inflate(R.layout.item_list_hint, lvContacts, false);
        lvContacts.addFooterView(footer, null, false);

        adapter = new ContactAdapter(this, contacts);
        lvContacts.setAdapter(adapter);

        // Nhấn vào một dòng: gửi liên hệ đó sang màn hình chi tiết qua Intent
        lvContacts.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, ContactDetailActivity.class);
            intent.putExtra(ContactDetailActivity.EXTRA_CONTACT, contacts.get(position));
            detailLauncher.launch(intent);
        });

        btnAdd.setOnClickListener(v ->
                addContactLauncher.launch(new Intent(this, AddContactActivity.class)));

        refresh();
    }

    /** Vẽ lại danh sách và cập nhật khối thống kê sau mỗi lần thêm, xóa, đổi yêu thích. */
    private void refresh() {
        adapter.notifyDataSetChanged();
        tvTotal.setText(String.valueOf(contacts.size()));
        tvFavorite.setText(String.valueOf(ContactStore.countFavorites()));
        boolean empty = contacts.isEmpty();
        tvEmpty.setVisibility(empty ? View.VISIBLE : View.GONE);
        lvContacts.setVisibility(empty ? View.GONE : View.VISIBLE);
    }
}
