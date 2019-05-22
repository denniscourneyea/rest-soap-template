package ca.denniscourneyea.lib.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoField;

public class DateTimeAdapter extends XmlAdapter<String, Instant> {

    @Override
    public Instant unmarshal(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        } else if (s.endsWith("Z")) {
            return Instant.parse(s);
        } else if (s.substring(s.length() - 6).matches("[-+]\\d{2}:\\d{2}")) {
            return OffsetDateTime.parse(s).toInstant();
        } else {
            return OffsetDateTime
                    .of(LocalDateTime.parse(s), OffsetDateTime.now().getOffset())
                    .toInstant();
        }
    }

    @Override
    public String marshal(Instant instant) {
        return instant == null ? null : instant.with(ChronoField.MILLI_OF_SECOND, 0).toString();
    }
}
