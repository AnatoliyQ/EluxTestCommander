package com.elux.appliancecontrol.service;

import com.elux.appliancecontrol.exception.ApplianceNotFoundException;
import com.elux.appliancecontrol.model.Appliance;
import com.elux.appliancecontrol.repository.ApplianceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AppliancesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppliancesService.class);

    @Autowired
    private ApplianceRepository applianceRepository;

    public void register(Appliance appliance) {
        LOGGER.info("register");
        applianceRepository.save(appliance);
    }

    public Appliance get(String applianceId) throws ApplianceNotFoundException {
        LOGGER.info("Get appliance {}", applianceId);

        Optional<Appliance> applianceOptional = applianceRepository.findByApplianceId(applianceId);

        if (applianceOptional.isPresent()) {
            return applianceOptional.get();
        } else {
            throw new ApplianceNotFoundException("Appliance not found");
        }
    }

    @Transactional
    public void delete(String applianceId) throws ApplianceNotFoundException {
        LOGGER.info("Delete appliance {}", applianceId);

        if (applianceRepository.existsByApplianceId(applianceId)) {
            applianceRepository.deleteByApplianceId(applianceId);
        } else {
            throw new ApplianceNotFoundException("Appliance not found");
        }
    }

    public List<Appliance> getAll() {
        LOGGER.info("Get all appliances");

        return applianceRepository.findAll();
    }

    public boolean existByApplianceId(String applianceId){
        return applianceRepository.existsByApplianceId(applianceId);
    }
}