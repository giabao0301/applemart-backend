package com.applemart.notification.email;

import com.applemart.notification.NotificationRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmailBuilder {
    public static String buildEmail(NotificationRequest data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm a");

        String name = data.getToUserName();

        String subject = data.getSubject();


        if (data.getToUserName() == null) {
            name = data.getToUserEmail();
        }

        LocalDateTime sentAt = LocalDateTime.now();

        String otp = data.getMessage();

        if (subject.equals("%s là mã xác nhận email của bạn".formatted(otp))) {
            return "<body style=\"margin: 0; background: #ffffff; font-size: 14px;\">" +
                    "<div style=\"font-family: 'Poppins', sans-serif; max-width: 680px; margin: 0 auto; padding: 45px 30px 60px; background: #f4f7ff; background-image: url(https://4kwallpapers.com/images/walls/thumbs_3t/9729.jpg); background-repeat: no-repeat; background-size: 800px 452px; background-position: top center; font-size: 14px; color: #434343;\">" +
                    "<header><table style=\"width: 100%;\"><tbody><tr style=\"height: 0;\"><td style=\"text-align: right;\"><span style=\"font-size: 16px; line-height: 30px; color: #ffffff;\">" + sentAt.format(formatter) + "</span></td></tr></tbody></table></header>" +
                    "<main><div style=\"margin: 0; margin-top: 70px; padding: 92px 30px 115px; background: #ffffff; border-radius: 30px; text-align: center;\"><div style=\"width: 100%; max-width: 489px; margin: 0 auto\">" +
                    "<h1 style=\"margin: 0; font-size: 28px; font-weight: 600; color: #1f1f1f;\">Chào mừng đến với Applemart!</h1>" +
                    "<p style=\"margin: 0; margin-top: 17px; font-size: 17px; font-weight: 500;\">Chào, " + name + ",</p>" +
                    "<p style=\"margin: 0; margin-top: 17px; font-weight: 500; font-size: 17px\">Sử dụng mã này để kích hoạt tài khoản Applemart của bạn <br>Mã này sẽ hết hạn sau <span style=\"font-weight: 600; color: #1f1f1f;\">24 giờ</span></p>" +
                    "<p style=\"margin: 0; margin-left: 30px; margin-top: 60px; font-size: 40px; font-weight: 600; letter-spacing: 25px; color: #000000;\">" + otp + "</p>" +
                    "</div></div>" +
                    "<p style=\"max-width: 400px; margin-left: 30px; margin: 0 auto; margin-top: 90px; text-align: center; font-weight: 500; color: #8c8c8c;\">This code will securely sign you up using<br><span style=\"color: #DEA97D; text-decoration: none;\">youremail@email.com</span></p>" +
                    "</main><footer style=\"width: 100%; max-width: 490px; margin: 20px auto 0; text-align: center; border-top: 1px solid #e6ebf1;\"><p style=\"margin: 0; margin-top: 40px; font-size: 16px; font-weight: 600; color: #434343;\">Applemart Company</p>" +
                    "<p style=\"margin: 0; margin-top: 16px; color: #434343;\">Copyright © 2025 Company. All rights reserved.</p></footer></div></body>";
        } else if (subject.equals("%s là mã khôi phục mật khẩu của bạn".formatted(otp))) {
            return "<body style=\"margin: 0; background: #ffffff; font-size: 14px;\">" +
                    "<div style=\"font-family: 'Poppins', sans-serif; max-width: 680px; margin: 0 auto; padding: 45px 30px 60px; background: #f4f7ff; background-image: url(https://4kwallpapers.com/images/walls/thumbs_3t/9729.jpg); background-repeat: no-repeat; background-size: 800px 452px; background-position: top center; font-size: 14px; color: #434343;\">" +
                    "<header><table style=\"width: 100%;\"><tbody><tr style=\"height: 0;\"><td style=\"text-align: right;\"><span style=\"font-size: 16px; line-height: 30px; color: #ffffff;\">" + sentAt.format(formatter) + "</span></td></tr></tbody></table></header>" +
                    "<main><div style=\"margin: 0; margin-top: 70px; padding: 92px 30px 115px; background: #ffffff; border-radius: 30px; text-align: center;\"><div style=\"width: 100%; max-width: 489px; margin: 0 auto\">" +
                    "<h1 style=\"margin: 0; font-size: 28px; font-weight: 600; color: #1f1f1f;\"></h1>" +
                    "<p style=\"margin: 0; margin-top: 17px; font-size: 17px; font-weight: 500;\">Chào, " + name + ",</p>" +
                    "<p style=\"margin: 0; margin-top: 17px; font-weight: 500; font-size: 17px\">Sử dụng mã này để đặt lại mật khẩu tài khoản Applemart của bạn <br>Mã này sẽ hết hạn sau <span style=\"font-weight: 600; color: #1f1f1f;\">30 phút</span></p>" +
                    "<p style=\"margin: 0; margin-left: 30px; margin-top: 60px; font-size: 40px; font-weight: 600; letter-spacing: 25px; color: #000000;\">" + otp + "</p>" +
                    "</div></div>" +
                    "<p style=\"max-width: 400px; margin-left: 30px; margin: 0 auto; margin-top: 90px; text-align: center; font-weight: 500; color: #8c8c8c;\">This code will allow you to reset password for account<br><span style=\"color: #DEA97D; text-decoration: none;\">youremail@email.com</span></p>" +
                    "</main><footer style=\"width: 100%; max-width: 490px; margin: 20px auto 0; text-align: center; border-top: 1px solid #e6ebf1;\"><p style=\"margin: 0; margin-top: 40px; font-size: 16px; font-weight: 600; color: #434343;\">Applemart Company</p>" +
                    "<p style=\"margin: 0; margin-top: 16px; color: #434343;\">Copyright © 2025 Company. All rights reserved.</p></footer></div></body>";
        }

        return "<body style=\"margin: 0; background: #ffffff; font-size: 14px;\">" +
                "<div style=\"font-family: 'Poppins', sans-serif; max-width: 680px; margin: 0 auto; padding: 45px 30px 60px; background: #f4f7ff; background-image: url(https://4kwallpapers.com/images/walls/thumbs_3t/9729.jpg); background-repeat: no-repeat; background-size: 800px 452px; background-position: top center; font-size: 14px; color: #434343;\">" +
                "<header><table style=\"width: 100%;\"><tbody><tr style=\"height: 0;\"><td style=\"text-align: right;\"><span style=\"font-size: 16px; line-height: 30px; color: #ffffff;\">" + sentAt.format(formatter) + "</span></td></tr></tbody></table></header>" +
                "<main><div style=\"margin: 0; margin-top: 70px; padding: 92px 30px 115px; background: #ffffff; border-radius: 30px; text-align: center;\"><div style=\"width: 100%; max-width: 489px; margin: 0 auto\">" +
                "<h1 style=\"margin: 0; font-size: 28px; font-weight: 600; color: #1f1f1f;\">Mã xác minh</h1>" +
                "<p style=\"margin: 0; margin-top: 17px; font-size: 17px; font-weight: 500;\">Chào, " + name + ",</p>" +
                "<p style=\"margin: 0; margin-top: 17px; font-weight: 500; font-size: 17px\"><br>Đây là mã xác minh của bạn<span style=\"font-weight: 600; color: #1f1f1f;\"></span></p>" +
                "<p style=\"margin: 0; margin-left: 30px; margin-top: 60px; font-size: 40px; font-weight: 600; letter-spacing: 25px; color: #000000;\">" + otp + "</p>" +
                "</div></div>" +
                "<p style=\"max-width: 400px; margin-left: 30px; margin: 0 auto; margin-top: 90px; text-align: center; font-weight: 500; color: #8c8c8c;\">This code will securely sign you up using<br><span style=\"color: #DEA97D; text-decoration: none;\">youremail@email.com</span></p>" +
                "</main><footer style=\"width: 100%; max-width: 490px; margin: 20px auto 0; text-align: center; border-top: 1px solid #e6ebf1;\"><p style=\"margin: 0; margin-top: 40px; font-size: 16px; font-weight: 600; color: #434343;\">Applemart Company</p>" +
                "<p style=\"margin: 0; margin-top: 16px; color: #434343;\">Copyright © 2025 Company. All rights reserved.</p></footer></div></body>";
    }
}
