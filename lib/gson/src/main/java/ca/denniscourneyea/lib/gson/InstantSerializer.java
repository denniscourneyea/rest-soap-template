package ca.denniscourneyea.lib.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.temporal.ChronoField;

public class InstantSerializer implements JsonSerializer<Instant> {

    @Override
    public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
        return instant == null ? null : new JsonPrimitive(instant.with(ChronoField.MILLI_OF_SECOND, 0).toString());
    }
}
