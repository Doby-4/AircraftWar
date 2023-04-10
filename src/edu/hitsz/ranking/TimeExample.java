package edu.hitsz.ranking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeExample {
    public static void main(String[] args) {
        // 创建一个本地日期时间
        LocalDateTime dateTime = LocalDateTime.now();

        // 格式化日期时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);

        // 打印日期、时间和日期时间

        System.out.println("DateTime: " + formattedDateTime);
    }
}
