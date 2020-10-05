package com.elux.appliancecontrol.controller;

import com.elux.appliancecontrol.exception.ApplianceNotFoundException;
import com.elux.appliancecontrol.model.Appliance;
import com.elux.appliancecontrol.service.AppliancesService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ApplianceRegisterControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AppliancesService appliancesService;

    private final static String baseApplianceId = "946910-785-99";
    private static Appliance expectedAppliance;

    @BeforeAll
    static void prepareAppliance(){
        expectedAppliance = new Appliance(baseApplianceId, "Owen");
    }

    @Test
    void when_appliance_registered_then_ok() throws Exception {

        MvcResult result = mvc.perform(post("/appliances")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"applianceId\": \"" + baseApplianceId + "\", \"applianceType\": \"Owen\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        String responseMessage = result.getResponse().getContentAsString();
        assertEquals(String.format("Appliance was registered - %s", baseApplianceId), responseMessage);
    }

    @Test
    void when_appliance_already_registered_then_bad_request() throws Exception {

        when(appliancesService.existByApplianceId(baseApplianceId)).thenReturn(true);

        MvcResult result = mvc.perform(post("/appliances")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"applianceId\": \"" + baseApplianceId + "\", \"applianceType\": \"Owen\"}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseMessage = result.getResponse().getContentAsString();
        assertEquals(String.format("Appliance - %s was already registered", baseApplianceId), responseMessage);
    }

    @Test
    void when_get_all_appliance_then_ok() throws Exception {
        List<Appliance> listAppliances = new ArrayList<>();
        listAppliances.add(new Appliance("12345-678-00", "Owen"));
        listAppliances.add(new Appliance("98876-123-09", "Owen"));
        listAppliances.add(new Appliance("12309-423-19", "Owen"));

        when(appliancesService.getAll()).thenReturn(listAppliances);

        mvc.perform(get("/appliances")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void when_get_appliance_then_ok() throws Exception {

        when(appliancesService.get(baseApplianceId)).thenReturn(expectedAppliance);

        mvc.perform(get("/appliances/" + baseApplianceId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.applianceId", is(baseApplianceId)))
                .andExpect(jsonPath("$.applianceType", is("Owen")));
    }

    @Test
    void when_appliance_not_registered_then_not_found_exception() throws Exception {
        when(appliancesService.get(baseApplianceId)).thenThrow(new ApplianceNotFoundException("Appliance not found"));

        mvc.perform(get("/appliances/" + baseApplianceId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertNotNull(result.getResolvedException()))
                .andExpect(result -> assertEquals("Appliance not found", result.getResolvedException().getMessage()));
    }

    @Test
    void when_unregister_appliance_then_ok() throws Exception {

        when(appliancesService.existByApplianceId(baseApplianceId)).thenReturn(true);

        MvcResult result = mvc.perform(delete("/appliances/" + baseApplianceId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseMessage = result.getResponse().getContentAsString();
        assertEquals(String.format("Appliance %s was unregistered", baseApplianceId), responseMessage);
    }

    @Test
    void when_appliance_already_unregistered_then_not_found_exception() throws Exception {

        when(appliancesService.get(baseApplianceId)).thenThrow(new ApplianceNotFoundException("Appliance not found"));

        mvc.perform(get("/appliances/"+baseApplianceId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertNotNull(result.getResolvedException()))
                .andExpect(result -> assertEquals("Appliance not found", result.getResolvedException().getMessage()));
    }

}