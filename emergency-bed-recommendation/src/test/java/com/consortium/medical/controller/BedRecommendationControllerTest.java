package com.consortium.medical.controller;

import com.consortium.medical.dto.BedRecommendationResponse;
import com.consortium.medical.exception.NoAvailableBedException;
import com.consortium.medical.service.BedRecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class BedRecommendationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BedRecommendationService bedService;

    @InjectMocks
    private BedRecommendationController bedController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bedController)
                .setHandlerExceptionResolvers(getExceptionResolver())
                .build();
    }

    private HandlerExceptionResolver getExceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        exceptionResolver.setDefaultErrorView("error");
        return exceptionResolver;
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void testRecommendBed_Success() throws Exception {
        when(bedService.findAvailableBed(any())).thenReturn(new BedRecommendationResponse("1", "Hôpital A"));

        mockMvc.perform(post("/recommend-bed")
                        .param("patientId", "12345")
                        .param("latitude", "48.85")
                        .param("longitude", "2.35")
                        .param("symptoms", "Douleurs thoraciques"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("hospitalId", "1"))
                .andExpect(model().attribute("hospitalName", "Hôpital A"));
    }

    @Test
    public void testRecommendBed_InvalidInput() throws Exception {
        // Supprimer le stubbing inutile

        mockMvc.perform(post("/recommend-bed")
                        .param("patientId", "")
                        .param("latitude", "0")
                        .param("longitude", "0")
                        .param("symptoms", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMessage", "Le patientId est requis"));
    }

    @Test
    public void testRecommendBed_NoAvailableBed() throws Exception {
        doThrow(new NoAvailableBedException("Aucun hôpital disponible")).when(bedService).findAvailableBed(any());

        mockMvc.perform(post("/recommend-bed")
                        .param("patientId", "12345")
                        .param("latitude", "48.85")
                        .param("longitude", "2.35")
                        .param("symptoms", "Douleurs thoraciques"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMessage", "Aucun hôpital disponible"));
    }
}
