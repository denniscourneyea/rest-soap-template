package ca.denniscourneyea.service.impl.soap;

import ca.denniscourneyea.service.api.v1.StatusResponse;
import ca.denniscourneyea.service.impl.common.status.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@SuppressWarnings("unused") // Framework usage of endpoint class and its methods is invisible to IDE and compiler
public class StatusEndpoint {

    private final StatusService service;

    @Autowired
    public StatusEndpoint(StatusService service) {
        this.service = service;
    }

    @PayloadRoot(localPart = "statusRequest", namespace = SpringWsConfiguration.TARGET_NAMESPACE)
    @ResponsePayload
    public StatusResponse status() {
        return service.getStatus();
    }
}
