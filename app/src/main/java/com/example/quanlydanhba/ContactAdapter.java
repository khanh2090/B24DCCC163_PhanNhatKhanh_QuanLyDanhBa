package com.example.quanlydanhba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

/** Adapter hiển thị mỗi liên hệ theo layout item_contact.xml. */
public class ContactAdapter extends ArrayAdapter<Contact> {

    public ContactAdapter(@NonNull Context context, @NonNull List<Contact> contacts) {
        super(context, 0, contacts);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_contact, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contact contact = getItem(position);
        ContactViews.bindAvatar(holder.tvAvatar, contact);
        holder.tvName.setText(contact.getName());
        holder.tvPhone.setText(ContactViews.formatPhone(contact.getPhone()));
        holder.tvGroup.setText(contact.getGroup());
        // Chỉ hiện dấu sao với liên hệ yêu thích
        holder.tvStar.setVisibility(contact.isFavorite() ? View.VISIBLE : View.INVISIBLE);
        return convertView;
    }

    /** Giữ sẵn các view của một dòng để không phải findViewById lại khi cuộn. */
    private static class ViewHolder {
        final TextView tvAvatar, tvName, tvPhone, tvGroup, tvStar;

        ViewHolder(View row) {
            tvAvatar = row.findViewById(R.id.tvAvatar);
            tvName = row.findViewById(R.id.tvName);
            tvPhone = row.findViewById(R.id.tvPhone);
            tvGroup = row.findViewById(R.id.tvGroup);
            tvStar = row.findViewById(R.id.tvStar);
        }
    }
}
