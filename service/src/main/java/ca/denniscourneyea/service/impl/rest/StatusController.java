package ca.denniscourneyea.service.impl.rest;

import ca.denniscourneyea.service.api.v1.StatusResponse;
import ca.denniscourneyea.service.impl.common.status.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SuppressWarnings("unused") // Framework usage of endpoint class and its methods is invisible to IDE and compiler
public class StatusController {

    private final StatusService service;

    @Autowired
    public StatusController(StatusService service) {
        this.service = service;
    }

    @RequestMapping("status")
    @ResponseBody
    public StatusResponse status() {
        return service.getStatus();
    }
}
