package ca.denniscourneyea.lib.jaxb;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DateTimeAdapterTest {

    private final DateTimeAdapter adapter = new DateTimeAdapter();

    @Test
    public void testMarshallDateTime() {
        assertEquals(
                "2017-10-24T01:02:03Z",
                adapter.marshal(OffsetDateTime
                        .of(2017, 10, 24, 1, 2, 3, 0, ZoneOffset.UTC)
                        .toInstant()));
    }

    @Test
    public void testMarshallDateTime_withZeroSeconds() {
        assertEquals(
                "2017-10-24T01:02:00Z",
                adapter.marshal(OffsetDateTime
                        .of(LocalDateTime.of(2017, 10, 24, 1, 2), ZoneOffset.UTC)
                        .toInstant()));
    }

    @Test
    public void testMarshallDateTime_withFractionalSeconds() {
        assertEquals(
                "2017-10-24T01:02:03Z",
                adapter.marshal(OffsetDateTime
                        .of(2017, 10, 24, 1, 2, 3, 4, ZoneOffset.UTC)
                        .toInstant()));
    }

    @Test
    public void testMarshallDateTime_withOffset() {
        assertEquals(
                "2017-10-23T21:02:03Z",
                adapter.marshal(OffsetDateTime
                        .of(2017, 10, 24, 1, 2, 3, 0, ZoneOffset.ofHours(4))
                        .toInstant()));
    }

    @Test
    public void testMarshallNullDateTime() {
        assertNull(adapter.marshal(null));
    }

    @Test
    public void testUnmarshallIsoDateTime() {
        assertEquals(
                OffsetDateTime.of(2017, 10, 24, 1, 2, 3, 0, ZoneOffset.UTC).toInstant(),
                adapter.unmarshal("2017-10-24T01:02:03Z"));
    }

    @Test
    public void testUnmarshallIsoDateTimeWithFractionalSeconds() {
        assertEquals(
                OffsetDateTime.of(2017, 10, 24, 1, 2, 3, 4000000, ZoneOffset.UTC).toInstant(),
                adapter.unmarshal("2017-10-24T01:02:03.004Z"));
    }

    @Test
    public void testUnmarshallIsoDateTime_withPositiveOffset() {
        assertEquals(
                OffsetDateTime.of(2017, 10, 23, 20, 32, 3, 0, ZoneOffset.UTC).toInstant(),
                adapter.unmarshal("2017-10-24T01:02:03+04:30"));
    }

    @Test
    public void testUnmarshallIsoDateTime_withNegativeOffset() {
        assertEquals(
                OffsetDateTime.of(2017, 10, 24, 5, 32, 3, 0, ZoneOffset.UTC).toInstant(),
                adapter.unmarshal("2017-10-24T01:02:03-04:30"));
    }

    @Test(expected = DateTimeParseException.class)
    public void testUnmarshallIsoDateTimeWithoutSeconds() {
        adapter.unmarshal("2017-10-24T00:38Z");
    }

    @Test
    public void testUnmarshallIsoDateTime_withoutOffset() {
        assertEquals(
                OffsetDateTime.of(2017, 10, 24, 1, 2, 3, 0, OffsetDateTime.now().getOffset()).toInstant(),
                adapter.unmarshal("2017-10-24T01:02:03"));
    }

    @Test(expected = DateTimeParseException.class)
    public void testUnmarshallIsoTime_withInvalidHour() {
        adapter.unmarshal("2017-24-10T25:02:03Z");
    }

    @Test(expected = DateTimeParseException.class)
    public void testUnmarshallIsoTime_withInvalidMinute() {
        adapter.unmarshal("2017-24-10T01:61:03Z");
    }

    @Test(expected = DateTimeParseException.class)
    public void testUnmarshallIsoTime_withInvalidSeconds() {
        adapter.unmarshal("2017-24-10T02:03:61Z");
    }

    @Test(expected = DateTimeParseException.class)
    public void testUnmarshallNonIsoDateTime() {
        adapter.unmarshal("2017-24-10T00:38:09");
    }

    @Test
    public void testUnmarshallEmptyDateTime() {
        assertNull(adapter.unmarshal(""));
    }

    @Test
    public void testUnmarshallNullDateTime() {
        assertNull(adapter.unmarshal(null));
    }
}
