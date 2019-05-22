package ca.denniscourneyea.service.impl.common;

import ca.denniscourneyea.service.api.v1.Response;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.time.Instant;

public abstract class AbstractResponseBuilder<R extends Response> {

    private static final DatatypeFactory DATATYPE_FACTORY;
    protected final R response;

    static {
        try {
            DATATYPE_FACTORY = DatatypeFactory.newInstance();
        }
        catch (DatatypeConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    protected AbstractResponseBuilder(R response) {
        this.response = response;
    }

    public R toResponse() {
        this.response.setTimestamp(Instant.now());
        return this.response;
    }

}
