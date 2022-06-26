/**
 * @author Puchko Konstantsin S19575
 */

package zad1;


import java.time.*;
import java.time.format.*;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Time {
    public static String passed(String s_from, String s_to) {
        try {
            if (!s_from.contains("T") && !s_to.contains("T")) {
                LocalDate from = LocalDate.parse(s_from);
                LocalDate to = LocalDate.parse(s_to);
                Period period = Period.between(from, to);
                String from_day = from.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PL"));
                String to_day = to.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PL"));
                long days = ChronoUnit.DAYS.between(from, to);
                double weeks = days / 7.0;
                StringBuilder builder = convert(period);
                return "Od " + from.getDayOfMonth() + " "
                        + from.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PL")) + " " +
                        from.getYear() + " (" + from_day + ")" + " do " + to.getDayOfMonth() + " "
                        + to.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PL")) + " " +
                        to.getYear() + " (" + to_day + ")\n - mija: " + days + (days == 1 ? " dzień" : " dni") + ", tygodni " + String.format("%.2f", weeks) +
                        "\n - kalendarzowo: " + builder.toString();

            } else {
                ZonedDateTime from = ZonedDateTime.of(LocalDateTime.parse(s_from), ZoneId.systemDefault());
                ZonedDateTime to = ZonedDateTime.of(LocalDateTime.parse(s_to), ZoneId.systemDefault());
                Period period = Period.between(from.toLocalDate(), to.toLocalDate());
                String fromDay = from.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PL"));
                String toDay = to.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PL"));
                long days = ChronoUnit.DAYS.between(from.toLocalDate(), to.toLocalDate());
                double weeks = days / 7.0;
                StringBuilder stringBuilder = convert(period);
                return "Od " + from.getDayOfMonth() + " "
                        + from.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PL")) + " " +
                        from.getYear() + " (" + fromDay + ") godz. " + from.getHour() + ":" +
                        ((String.valueOf(from.getMinute()).length() == 1) ? "0" + from.getMinute() : from.getMinute())
                        + " do " + to.getDayOfMonth() + " "
                        + to.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("PL")) + " " +
                        to.getYear() + " (" + toDay + ") godz. " + to.getHour() + ":" +
                        ((String.valueOf(to.getMinute()).length() == 1) ? "0" + to.getMinute() : to.getMinute())
                        + "\n - mija: " + days + (days != 1 ? " dni" : " dzień")
                        + ", tygodni " + String.format("%.2f", weeks)
                        + "\n - godzin: " + ChronoUnit.HOURS.between(from, to)
                        + ", minut: " + ChronoUnit.MINUTES.between(from, to) +
                        "\n - kalendarzowo: " + stringBuilder.toString();
            }
        } catch (Exception e) {
            return "*** java.time.format.DateTimeParseException: " + e.getMessage();
        }
    }

    private static StringBuilder convert(Period period){
        StringBuilder stringBuilder = new StringBuilder();
        boolean b = false;
        if (period.getYears() > 0) {
            stringBuilder.append(period.getYears()).append(period.getYears() == 1 ? " rok" : " lata");
            b = true;
        }
        if (period.getMonths() > 0) {
            stringBuilder.append(b ? ", " : "").append(period.getMonths()).append(period.getMonths() == 1 ? " miesiąc" : " miesiące");
            b = true;
        }
        if (period.getDays() > 0) {
            stringBuilder.append(b ? ", " : "").append(period.getDays()).append(period.getDays() == 1 ? " dzień" : " dni");
        }
        return stringBuilder;
    }
}
