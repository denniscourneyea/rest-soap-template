package ca.denniscourneyea.lib.gson;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.Assert.*;

public class InstantSerializerTest {

    private final InstantSerializer serializer = new InstantSerializer();

    @Test
    public void testMarshallDateTime() {
        Instant i = OffsetDateTime
                .of(2017, 10, 24, 1, 2, 3, 0, ZoneOffset.UTC)
                .toInstant();
        assertEquals("2017-10-24T01:02:03Z", serializer.serialize(i, null, null).getAsString());
    }

    @Test
    public void testMarshallDateTime_withZeroSeconds() {
        Instant i = OffsetDateTime
                .of(LocalDateTime.of(2017, 10, 24, 1, 2), ZoneOffset.UTC)
                .toInstant();
        assertEquals("2017-10-24T01:02:00Z", serializer.serialize(i, null, null).getAsString());
    }

    @Test
    public void testMarshallDateTime_withFractionalSeconds() {
        Instant i = OffsetDateTime
                .of(2017, 10, 24, 1, 2, 3, 4, ZoneOffset.UTC)
                .toInstant();
        assertEquals("2017-10-24T01:02:03Z", serializer.serialize(i, null, null).getAsString());
    }

    @Test
    public void testMarshallDateTime_withOffset() {
        Instant i = OffsetDateTime
                .of(2017, 10, 24, 1, 2, 3, 0, ZoneOffset.ofHours(4))
                .toInstant();
        assertEquals("2017-10-23T21:02:03Z", serializer.serialize(i, null, null).getAsString());
    }

    @Test
    public void testMarshallNullDateTime() {
        assertNull(serializer.serialize(null, null, null));
    }

}