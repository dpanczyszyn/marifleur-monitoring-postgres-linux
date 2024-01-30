package pl.marifleur.microservice.postgres.linux.util.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeFormatUtil {

    public DateTimeFormatter getYYYYMMDDHHMMWithHyphens() {
        String pattern = "yyyy-MM-dd HH:mm";
        return DateTimeFormatter.ofPattern(pattern);
    }

    public String formatYYYYMMDDHHMMWithHyphens(Date date) {
        DateTimeFormatter dateTimeFormatter = getYYYYMMDDHHMMWithHyphens();
        Instant instant = Instant.ofEpochMilli(date.getTime());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return dateTimeFormatter.format(localDateTime);
    }

    public Date getDateFromPostgresLog(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss.SSS z");

        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
