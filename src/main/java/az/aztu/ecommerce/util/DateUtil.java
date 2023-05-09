package az.aztu.ecommerce.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static java.time.format.DateTimeFormatter.ofPattern;

public interface DateUtil {

    static String getDayAndMonth() {

        return LocalDate.now().format(ofPattern("ddMM"));
    }

    static Date toDate(LocalDateTime localDateTime) {

        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }
}
