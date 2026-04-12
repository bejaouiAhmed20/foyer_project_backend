package com.example.demo.controller;

import com.example.demo.dto.ReservationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testValidationError400() throws Exception {
        ReservationDTO invalidDto = new ReservationDTO();
        invalidDto.setEtudiantIds(Collections.emptySet());
        invalidDto.setAnneeUniversitaire(LocalDate.now());

        mockMvc.perform(MockMvcRequestBuilders.post("/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testNotFound404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chambre/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message", containsString("Chambre not found")))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testConflict409() throws Exception {
        ReservationDTO dto = new ReservationDTO();
        dto.setChambreId(999L);
        dto.setEtudiantIds(Set.of(1L));
        dto.setAnneeUniversitaire(LocalDate.now());

        mockMvc.perform(MockMvcRequestBuilders.post("/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}
