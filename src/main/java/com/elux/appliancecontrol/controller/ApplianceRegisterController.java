package com.elux.appliancecontrol.controller;

import com.elux.appliancecontrol.dto.request.RegisterApplianceRequest;
import com.elux.appliancecontrol.exception.ApplianceNotFoundException;
import com.elux.appliancecontrol.model.Appliance;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Appliances")
@RestController
@RequestMapping("/appliances")
public class ApplianceRegisterController {

    @Autowired
    private AppliancesService appliancesService;

    private final ObjectMapper mapper = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplianceRegisterController.class);

    @ApiOperation(value = "Register appliance")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerAppliance(@RequestBody RegisterApplianceRequest registerApplianceRequest) {


        if (appliancesService.existByApplianceId(registerApplianceRequest.getApplianceId())) {
            return new ResponseEntity<>(String.format("Appliance - %s was already registered", registerApplianceRequest.getApplianceId()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }


        appliancesService.register(new Appliance(registerApplianceRequest.getApplianceId(),
                registerApplianceRequest.getApplianceType()));

        return new ResponseEntity<>(String.format("Appliance was registered - %s", registerApplianceRequest.getApplianceId()), new HttpHeaders(), HttpStatus.CREATED);

    }

    @ApiOperation(value = "Unregister appliance")
    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> unregisterAppliance(@PathVariable("id") String id) throws ApplianceNotFoundException {

        appliancesService.delete(id);
        return new ResponseEntity<>(String.format("Appliance %s was unregistered", id), new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get appliance by appliance ID")
    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Appliance> getAppliance(@PathVariable("id") String id) throws ApplianceNotFoundException {

        Appliance appliance = appliancesService.get(id);

        return new ResponseEntity<>(appliance, new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all appliances")
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Appliance>> getAllAppliances() {
        List<Appliance> listAppliances = appliancesService.getAll();
        LOGGER.info("Get all appliances");
        return new ResponseEntity<>(listAppliances, HttpStatus.OK);
    }
}
