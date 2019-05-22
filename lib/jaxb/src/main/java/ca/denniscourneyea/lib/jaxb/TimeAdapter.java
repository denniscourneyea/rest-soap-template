package ca.denniscourneyea.lib.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

public class TimeAdapter extends XmlAdapter<String, OffsetTime> {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ssXXX");

    @Override
    public OffsetTime unmarshal(String s) throws Exception {
        if (s == null || s.isEmpty()) {
            return null;
        } else if (s.endsWith("Z") || s.substring(s.length() - 6).matches("[-+]\\d{2}:\\d{2}")) {
            return OffsetTime.parse(s);
        } else {
            return OffsetTime.of(LocalTime.parse(s), OffsetDateTime.now().getOffset());
        }
    }

    @Override
    public String marshal(OffsetTime time) throws Exception {
        return time == null ? null : formatter.format(time.with(ChronoField.MILLI_OF_SECOND, 0));
    }
}
