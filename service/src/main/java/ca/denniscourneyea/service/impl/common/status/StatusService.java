package ca.denniscourneyea.service.impl.common.status;

import ca.denniscourneyea.service.api.v1.StatusResponse;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    private String version;

    public StatusService() {
    }

    // Allows unit tests to avoid dependency on MANIFEST.MF which doesn't exist until later in build lifecycle
    StatusService(String version) {
        this();
        this.version = version;
    }

    public StatusResponse getStatus() {
        if (version == null) {
            synchronized (this) {
                // Version may have been set by another thread between first check and synchronized() statement
                if (version == null) {
                    version = getClass().getPackage().getImplementationVersion();
                }
            }
        }

        return new StatusResponseBuilder()
                .version(version)
                .toResponse();
    }
}
