package com.elux.appliancecontrol.service;

import com.elux.appliancecontrol.exception.ApplianceNotFoundException;
import com.elux.appliancecontrol.repository.ApplianceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AppliancesServiceTest {

    @MockBean
    private ApplianceRepository applianceRepository;
    @Autowired
    private AppliancesService appliancesService;

    private String applianceId = "123445-012-01";

    @Test
    void when_get_not_existed_appliance_than_throw_exception() {

        when(applianceRepository.findByApplianceId(applianceId)).thenReturn(Optional.empty());

        ApplianceNotFoundException ex = assertThrows(ApplianceNotFoundException.class, () -> {
            appliancesService.get(applianceId);
        });

        assertEquals("Appliance not found", ex.getMessage());

    }

    @Test
    void when_delete_not_existed_appliance_than_throw_exception() {

        when(applianceRepository.existsByApplianceId(applianceId)).thenReturn(false);

        ApplianceNotFoundException ex = assertThrows(ApplianceNotFoundException.class, () -> {
            appliancesService.delete(applianceId);
        });

        assertEquals("Appliance not found", ex.getMessage());

    }
}