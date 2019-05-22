package ca.denniscourneyea.lib.jaxb;

import org.junit.Test;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.assertEquals;

public class TimeAdapterTest {

    private final TimeAdapter adapter = new TimeAdapter();

    @Test
    public void testMarshallTime() throws Exception {
        assertEquals(
                "01:02:03Z",
                adapter.marshal(OffsetTime.of(1, 2, 3, 0, ZoneOffset.UTC)));
    }

    @Test
    public void testMarshallTime_withZeroSeconds() throws Exception {
        assertEquals(
                "01:02:00Z",
                adapter.marshal(OffsetTime.of(LocalTime.of(1, 2), ZoneOffset.UTC)));
    }

    @Test
    public void testMarshallTime_withFractionalSeconds() throws Exception {
        assertEquals(
                "01:02:03Z",
                adapter.marshal(OffsetTime.of(1, 2, 3, 4, ZoneOffset.UTC)));
    }

    @Test
    public void testMarshallTime_withOffset() throws Exception {
        assertEquals(
                "01:02:03+04:00",
                adapter.marshal(OffsetTime.of(1, 2, 3, 0, ZoneOffset.ofHours(4))));
    }

    @Test
    public void testMarshallTime_null() throws Exception {
        assertEquals(null, adapter.marshal(null));
    }

    @Test
    public void testUnmarshallIsoTime() throws Exception {
        assertEquals(
                OffsetTime.of(1, 2, 3, 0, ZoneOffset.UTC),
                adapter.unmarshal("01:02:03Z"));
    }

    @Test
    public void testUnmarshallIsoTime_withFractionalSeconds() throws Exception {
        assertEquals(
                OffsetTime.of(1, 2, 3, 4000000, ZoneOffset.UTC),
                adapter.unmarshal("01:02:03.004Z"));
    }

    @Test
    public void testUnmarshallIsoTime_withPositiveOffset() throws Exception {
        assertEquals(
                OffsetTime.of(1, 2, 3, 0, ZoneOffset.ofHoursMinutes(4, 30)),
                adapter.unmarshal("01:02:03+04:30"));
    }

    @Test
    public void testUnmarshallIsoTime_withNegativeOffset() throws Exception {
        assertEquals(
                OffsetTime.of(1, 2, 3, 0, ZoneOffset.ofHoursMinutes(-4, -30)),
                adapter.unmarshal("01:02:03-04:30"));
    }

    @Test
    public void testUnmarshallIsoTime_withoutSeconds() throws Exception {
        assertEquals(
                OffsetTime.of(1, 2, 0, 0, ZoneOffset.UTC),
                adapter.unmarshal("01:02Z"));
    }

    @Test
    public void testUnmarshallIsoTime_withoutOffset() throws Exception {
        assertEquals(
                OffsetTime.of(1, 2, 3, 0, OffsetDateTime.now().getOffset()),
                adapter.unmarshal("01:02:03"));
    }

    @Test(expected = DateTimeParseException.class)
    public void testUnmarshallIsoTime_withInvalidHour() throws Exception {
        adapter.unmarshal("25:02:03Z");
    }

    @Test(expected = DateTimeParseException.class)
    public void testUnmarshallIsoTime_withInvalidMinute() throws Exception {
        adapter.unmarshal("01:61:03Z");
    }

    @Test(expected = DateTimeParseException.class)
    public void testUnmarshallIsoTime_withInvalidSeconds() throws Exception {
        adapter.unmarshal("02:03:61Z");
    }

    @Test
    public void testUnmarshallEmptyTime() throws Exception {
        assertEquals(null, adapter.unmarshal(""));
    }

    @Test
    public void testUnmarshallNullTime() throws Exception {
        assertEquals(null, adapter.unmarshal(null));
    }

}
