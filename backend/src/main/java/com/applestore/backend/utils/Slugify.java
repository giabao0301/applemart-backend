package com.applestore.backend.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Slugify {
    public static String slugify(String text) {
            // Bước 1: Chuyển đổi chuỗi thành Unicode Normal Form (NFD)
            String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);

            // Bước 2: Loại bỏ các ký tự dấu tiếng Việt bằng cách sử dụng biểu thức chính quy
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String noDiacritics = pattern.matcher(normalized).replaceAll("");

            // Bước 3: Chuyển đổi chuỗi thành chữ thường
            String lowerCase = noDiacritics.toLowerCase();

            // Bước 4: Loại bỏ các ký tự đặc biệt không cần thiết và thay thế khoảng trắng bằng dấu gạch ngang
            String slug = lowerCase.replaceAll("[^a-z0-9\\s-]", "").replaceAll("\\s+", "-").replaceAll("-+", "-");

            // Bước 5: Trả về chuỗi slug
            return slug;
        }
}
