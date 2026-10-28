package com.example.quanlydanhba;

import android.graphics.Color;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Cài đặt chung cho các màn hình: ứng dụng vẽ tràn dưới thanh trạng thái, nên cần chừa khoảng
 * cho thanh trạng thái, thanh điều hướng và bàn phím.
 */
public final class ScreenHelper {

    private ScreenHelper() {
    }

    /** Màn hình không có thanh tiêu đề (Đăng nhập): toàn bộ khoảng trống đặt vào root. */
    public static void setUp(AppCompatActivity activity, int layoutRes) {
        EdgeToEdge.enable(activity);
        activity.setContentView(layoutRes);
        applyInsets(activity.findViewById(R.id.main), null);
    }

    /**
     * Màn hình có thanh tiêu đề xanh: thanh tiêu đề kéo dài lên dưới thanh trạng thái
     * (chữ trạng thái màu trắng), phần còn lại chừa khoảng cho thanh điều hướng và bàn phím.
     */
    public static void setUpWithToolbar(AppCompatActivity activity, int layoutRes) {
        EdgeToEdge.enable(activity, SystemBarStyle.dark(Color.TRANSPARENT));
        activity.setContentView(layoutRes);
        applyInsets(activity.findViewById(R.id.main), activity.findViewById(R.id.toolbar));
    }

    private static void applyInsets(View root, View toolbar) {
        View top = toolbar != null ? toolbar : root;
        int topPadding = top.getPaddingTop();
        int left = root.getPaddingLeft();
        int right = root.getPaddingRight();
        int bottom = root.getPaddingBottom();
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, windowInsets) -> {
            Insets bars = windowInsets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            top.setPadding(top.getPaddingLeft(), topPadding + bars.top, top.getPaddingRight(), top.getPaddingBottom());
            v.setPadding(left + bars.left, v.getPaddingTop(), right + bars.right, bottom + bars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }
}
