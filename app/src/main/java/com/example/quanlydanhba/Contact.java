package com.example.quanlydanhba;

import java.io.Serializable;

/**
 * Một liên hệ trong danh bạ. Cài đặt Serializable để truyền nguyên đối tượng qua Intent.
 * Số điện thoại là duy nhất trong danh bạ nên được dùng làm khóa nhận diện liên hệ.
 */
public class Contact implements Serializable {

    private String name;
    private String phone;
    private String group;
    private String gender;
    private boolean favorite;
    private String note;

    public Contact(String name, String phone, String group, String gender, boolean favorite, String note) {
        this.name = name;
        this.phone = phone;
        this.group = group;
        this.gender = gender;
        this.favorite = favorite;
        this.note = note;
    }

    /** Chữ cái đầu của tên (từ cuối trong họ tên tiếng Việt), dùng làm ảnh đại diện. */
    public String getInitial() {
        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            return "?";
        }
        String firstName = trimmed.substring(trimmed.lastIndexOf(' ') + 1);
        return firstName.substring(0, 1).toUpperCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
