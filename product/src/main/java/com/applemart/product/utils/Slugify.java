package com.applemart.product.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Slugify {
    public static String slugify(String input) {
        // Bước 1: Thay thế các ký tự tiếng Việt có dấu bằng các ký tự tương ứng không dấu
        String[][] vietnameseCharacters = {
                {"a", "á", "à", "ả", "ã", "ạ", "ă", "ắ", "ằ", "ẳ", "ẵ", "ặ", "â", "ấ", "ầ", "ẩ", "ẫ", "ậ"},
                {"d", "đ"},
                {"e", "é", "è", "ẻ", "ẽ", "ẹ", "ê", "ế", "ề", "ể", "ễ", "ệ"},
                {"i", "í", "ì", "ỉ", "ĩ", "ị"},
                {"o", "ó", "ò", "ỏ", "õ", "ọ", "ô", "ố", "ồ", "ổ", "ỗ", "ộ", "ơ", "ớ", "ờ", "ở", "ỡ", "ợ"},
                {"u", "ú", "ù", "ủ", "ũ", "ụ", "ư", "ứ", "ừ", "ử", "ữ", "ự"},
                {"y", "ý", "ỳ", "ỷ", "ỹ", "ỵ"}
        };

        for (String[] vietnameseChar : vietnameseCharacters) {
            for (int i = 1; i < vietnameseChar.length; i++) {
                input = input.replace(vietnameseChar[i], vietnameseChar[0]);
                input = input.replace(vietnameseChar[i].toUpperCase(), vietnameseChar[0].toUpperCase());
            }
        }

        // Bước 2: Chuyển đổi chuỗi thành Unicode Normal Form (NFD) và loại bỏ dấu
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String noDiacritics = pattern.matcher(normalized).replaceAll("");

        // Bước 3: Chuyển đổi chuỗi thành chữ thường
        String lowerCase = noDiacritics.toLowerCase();

        // Bước 4: Thay thế ký tự đặc biệt và khoảng trắng bằng dấu gạch ngang
        String slug = lowerCase.replaceAll("[^a-z0-9\\s-]", "-").replaceAll("[\\s/]+", "-").replaceAll("-+", "-");

        // Bước 5: Loại bỏ dấu gạch ngang ở đầu và cuối chuỗi
        slug = slug.replaceAll("^-|-$", "");

        // Bước 6: Trả về chuỗi slug
        return slug;
    }
}
