package com.example.quanlydanhba;

import java.util.ArrayList;

/**
 * Giữ danh sách liên hệ trong bộ nhớ khi ứng dụng đang chạy (không lưu xuống thiết bị).
 * Danh sách đặt ở biến static để không bị mất khi MainActivity được tạo lại (ví dụ xoay màn hình).
 */
public final class ContactStore {

    private static final ArrayList<Contact> contacts = new ArrayList<>();

    static {
        // Dữ liệu mẫu ban đầu theo đề bài
        contacts.add(new Contact("Nguyễn Văn An", "0901234567", "Bạn bè", "Nam", false, ""));
        contacts.add(new Contact("Trần Thị Bình", "0912345678", "Gia đình", "Nữ", true, "Chị gái, hay gọi buổi tối"));
        contacts.add(new Contact("Lê Minh Châu", "0987654321", "Công việc", "Nam", false, ""));
    }

    private ContactStore() {
    }

    public static ArrayList<Contact> getContacts() {
        return contacts;
    }

    public static int countFavorites() {
        int count = 0;
        for (Contact contact : contacts) {
            if (contact.isFavorite()) {
                count++;
            }
        }
        return count;
    }

    /** Vị trí của liên hệ có số điện thoại đã cho, hoặc -1 nếu không có. */
    public static int indexOfPhone(String phone) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getPhone().equals(phone)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean phoneExists(String phone) {
        return indexOfPhone(phone) >= 0;
    }
}
