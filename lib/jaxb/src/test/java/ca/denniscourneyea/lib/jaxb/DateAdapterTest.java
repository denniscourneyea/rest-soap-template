package ca.denniscourneyea.lib.jaxb;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.assertEquals;

public class DateAdapterTest {

    private final DateAdapter adapter = new DateAdapter();

    @Test
    public void testMarshallDate() throws Exception {
        assertEquals("2017-10-24", adapter.marshal(LocalDate.of(2017, 10, 24)));
    }

    @Test
    public void testMarshallNullDate() throws Exception {
        assertEquals(null, adapter.marshal(null));
    }

    @Test
    public void testUnmarshallIsoDate() throws Exception {
        assertEquals(LocalDate.of(2017, 10, 24), adapter.unmarshal("2017-10-24"));
    }

    @Test(expected = DateTimeParseException.class)
    public void testUnmarshallIsoDateWithOffset() throws Exception {
        adapter.unmarshal("2017-10-24+04:00");
    }

    @Test(expected = DateTimeParseException.class)
    public void testUnmarshallNonIsoDate() throws Exception {
        adapter.unmarshal("2017-24-10");
    }

    @Test
    public void testUnmarshallEmptyDate() throws Exception {
        assertEquals(null, adapter.unmarshal(""));
    }

    @Test
    public void testUnmarshallNullDate() throws Exception {
        assertEquals(null, adapter.unmarshal(null));
    }

}
