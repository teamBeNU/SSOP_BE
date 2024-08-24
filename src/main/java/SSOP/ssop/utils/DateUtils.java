package SSOP.ssop.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

    public static LocalDate parseBirthdate(String birthyear, String birthday) {
        try {
            // Assuming birthyear is in "yyyy" format and birthday is in "MMdd" format
            String formattedDate = birthyear + "-" + birthday.substring(0, 2) + "-" + birthday.substring(2);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(formattedDate, formatter);
        } catch (DateTimeParseException e) {
            // Handle parse exception, e.g., log it or rethrow it as a custom exception
            e.printStackTrace();
            return null;
        }
    }
}
