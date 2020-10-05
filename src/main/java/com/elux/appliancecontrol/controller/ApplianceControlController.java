package com.elux.appliancecontrol.controller;

import com.elux.appliancecontrol.dto.request.CommandRequest;
import com.elux.appliancecontrol.exception.ApplianceNotFoundException;
import com.elux.appliancecontrol.service.AppliancesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("Remote Control")
@RestController
@RequestMapping("/execute")
public class ApplianceControlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplianceControlController.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private AppliancesService appliancesService;

    @ApiOperation(value = "Send JSON command")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> executeCommand(@RequestBody CommandRequest commandRequest) throws ApplianceNotFoundException {

        LOGGER.info("Command received for appliance ID {}", commandRequest.getApplianceId());

        appliancesService.get(commandRequest.getApplianceId());

        return new ResponseEntity<>("Command was executed", new HttpHeaders(), HttpStatus.OK);
    }
}
