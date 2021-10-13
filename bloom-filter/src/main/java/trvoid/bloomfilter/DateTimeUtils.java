package trvoid.bloomfilter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    private static final DateTimeFormatter longDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter shortDateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static String getFormattedLongDateTimeNow() {
        return LocalDateTime.now().format(longDateTimeFormatter);
    }

    public static String getFormattedShortDateTimeNow() {
        return LocalDateTime.now().format(shortDateTimeFormatter);
    }

    public static String getFormattedLongDateTime(LocalDateTime dateTime) {
        return dateTime.format(longDateTimeFormatter);
    }

    public static String getFormattedShortDateTime(LocalDateTime dateTime) {
        return dateTime.format(shortDateTimeFormatter);
    }

    public static String getFormattedLongDateTime(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault()).format(longDateTimeFormatter);
    }

    public static String getFormattedShortDateTime(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault()).format(shortDateTimeFormatter);
    }

    public static long getMillisFromFormattedLongDateTime(String dateTimeStr) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr, longDateTimeFormatter);
        long millis = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        return millis;
    }

    public static long getMillisFromFormattedShortDateTime(String dateTimeStr) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr, shortDateTimeFormatter);
        long millis = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        return millis;
    }
}
