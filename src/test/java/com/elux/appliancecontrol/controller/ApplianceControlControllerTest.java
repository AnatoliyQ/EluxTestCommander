package com.elux.appliancecontrol.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ApplianceControlControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void when_execute_command_then_ok() throws Exception {

        MvcResult result = mvc.perform(post("/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"applianceId\": \"946910-785-99\", \"command\": \"Start\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String responseMessage = result.getResponse().getContentAsString();
        assertEquals("Command was executed", responseMessage);
    }

    @Test
    void when_appliance_not_exist_then_not_found_exception() throws Exception {
        mvc.perform(post("/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"applianceId\": \"00000-000-00\", \"command\": \"Start\"}"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertNotNull(result.getResolvedException()))
                .andExpect(result -> assertEquals("Appliance not found", result.getResolvedException().getMessage()));
    }

}