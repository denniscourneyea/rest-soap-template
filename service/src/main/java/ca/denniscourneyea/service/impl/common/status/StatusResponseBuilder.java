package ca.denniscourneyea.service.impl.common.status;

import ca.denniscourneyea.service.api.v1.StatusResponse;
import ca.denniscourneyea.service.impl.common.AbstractResponseBuilder;

class StatusResponseBuilder extends AbstractResponseBuilder<StatusResponse> {

    StatusResponseBuilder() {
        super(new StatusResponse());
    }

    StatusResponseBuilder version(String version) {
        this.response.setVersion(version);
        return this;
    }

}
