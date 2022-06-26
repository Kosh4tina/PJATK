/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Time {

    private static final Map<Month, String> months = Stream.of(new Object[][]{
            {Month.JANUARY, "stycznia"},
            {Month.FEBRUARY, "lutego"},
            {Month.MARCH, "marca"},
            {Month.APRIL, "kwietnia"},
            {Month.MAY, "maja"},
            {Month.JUNE, "czerwca"},
            {Month.JULY, "lipca"},
            {Month.AUGUST, "sierpnia"},
            {Month.SEPTEMBER, "września"},
            {Month.OCTOBER, "października"},
            {Month.NOVEMBER, "listopada"},
            {Month.DECEMBER, "grudnia"}
    }).collect(Collectors.toMap(data -> (Month) data[0], data -> (String) data[1]));

    private static final Map<DayOfWeek, String> daysOfWeek = Stream.of(new Object[][]{
            {DayOfWeek.SUNDAY, "(niedziela)"},
            {DayOfWeek.MONDAY, "(poniedziałek)"},
            {DayOfWeek.TUESDAY, "(wtorek)"},
            {DayOfWeek.WEDNESDAY, "(środa)"},
            {DayOfWeek.THURSDAY, "(czwartek)"},
            {DayOfWeek.FRIDAY, "(piątek)"},
            {DayOfWeek.SATURDAY, "(sobota)"},
    }).collect(Collectors.toMap(data -> (DayOfWeek) data[0], data -> (String) data[1]));

    public static String passed(String from, String to) {
        Locale.setDefault(Locale.ENGLISH);
        try {
            if (from.contains("T") && to.contains("T"))
                return getInfo(LocalDateTime.parse(from), LocalDateTime.parse(to));
            return getInfo(LocalDate.parse(from), LocalDate.parse(to));
        } catch (DateTimeParseException ex) {
            return "*** java.time.format.DateTimeParseException: " + ex.getMessage();
        }
    }

    private static String getInfo(LocalDate from, LocalDate to) {
        long daysBetween = ChronoUnit.DAYS.between(from, to);
        double weeksBetween = daysBetween / 7.0;

        return "Od " + from.getDayOfMonth() + " " + months.get(from.getMonth()) + " " + from.getYear() + " " +
                daysOfWeek.get(from.getDayOfWeek()) + " do " + to.getDayOfMonth() + " " + months.get(to.getMonth()) +
                " " + to.getYear() + " " + daysOfWeek.get(to.getDayOfWeek()) + "\n- mija: " + daysBetween +
                (daysBetween == 1 ? " dzień, " : " dni, ") + "tygodni " + (daysBetween % 7.0 == 0 ?
                String.valueOf(weeksBetween).split("\\.")[0] : String.format("%.2f", weeksBetween)) +
                getByCalendar(from, to);
    }

    private static String getInfo(LocalDateTime from, LocalDateTime to) {
        ZonedDateTime zonedFrom = ZonedDateTime.of(from, ZoneId.of("Europe/Warsaw"));
        ZonedDateTime zonedTo = ZonedDateTime.of(to, ZoneId.of("Europe/Warsaw"));

        long daysBetween = ChronoUnit.DAYS.between(zonedFrom.toLocalDate(), zonedTo.toLocalDate());
        double weeksBetween = daysBetween / 7.0;

        long hours = Duration.between(zonedFrom, zonedTo).toHours();
        long minutes = Duration.between(zonedFrom, zonedTo).toMinutes();

        return "Od " + zonedFrom.getDayOfMonth() + " " + months.get(zonedFrom.getMonth()) + " " + zonedFrom.getYear()
                + " " + daysOfWeek.get(zonedFrom.getDayOfWeek()) + " godz. " +
                zonedFrom.format(DateTimeFormatter.ofPattern("hh:mm")) + " do " + zonedTo.getDayOfMonth() + " " +
                months.get(zonedTo.getMonth()) + " " + zonedTo.getYear() + " " + daysOfWeek.get(zonedTo.getDayOfWeek())
                + " godz. " + zonedTo.format(DateTimeFormatter.ofPattern("hh:mm")) + "\n- mija: " + daysBetween +
                (daysBetween == 1 ? " dzień, " : " dni, ") + "tygodni " + (daysBetween % 7.0 == 0 ?
                String.valueOf(weeksBetween).split("\\.")[0] : String.format("%.2f", weeksBetween)) +
                "\n- godzin: " + hours + ", minut: " + minutes +
                getByCalendar(zonedFrom.toLocalDate(), zonedTo.toLocalDate());
    }

    private static String getByCalendar(LocalDate from, LocalDate to) {
        long daysBetween = ChronoUnit.DAYS.between(from, to);
        String byCalendar = "";

        if (daysBetween != 0) {
            int y = Period.between(from, to).getYears();
            int m = Period.between(from, to).getMonths();
            int d = Period.between(from, to).getDays();

            byCalendar = "\n- kalendarzowo: ";
            if (y != 0)
                byCalendar += y == 1 ? y + " rok, " : y < 5 ? y + " lata, " : y + " lat, ";
            if (m != 0)
                byCalendar += m == 1 ? m + " miesiąc, " : m < 5 ? m + " miesiące, " : m + " miesięcy, ";
            if (d != 0)
                byCalendar += d == 1 ? d + " dzień, " : d + " dni, ";

            byCalendar = byCalendar.substring(0, byCalendar.length() - 2);
        }
        return byCalendar;
    }
}