package com.example.quanlydanhba;

import android.content.res.ColorStateList;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

/** Cách hiển thị dùng chung cho danh sách và màn hình chi tiết. */
public final class ContactViews {

    private static final int[] AVATAR_COLORS = {
            R.color.avatar_1, R.color.avatar_2, R.color.avatar_3, R.color.avatar_4, R.color.avatar_5
    };

    private ContactViews() {
    }

    /** Ảnh đại diện: chữ cái đầu của tên trên nền tròn, màu nền cố định theo chữ cái đó. */
    public static void bindAvatar(TextView avatar, Contact contact) {
        String initial = contact.getInitial();
        avatar.setText(initial);
        int color = ContextCompat.getColor(avatar.getContext(),
                AVATAR_COLORS[initial.codePointAt(0) % AVATAR_COLORS.length]);
        ViewCompat.setBackgroundTintList(avatar, ColorStateList.valueOf(color));
    }

    /** "0901234567" -> "0901 234 567" cho dễ đọc. */
    public static String formatPhone(String phone) {
        if (phone.length() != 10) {
            return phone;
        }
        return phone.substring(0, 4) + " " + phone.substring(4, 7) + " " + phone.substring(7);
    }
}
