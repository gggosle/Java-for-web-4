package com.example.webapp.controller;

import com.example.webapp.config.TestSecurityConfig;
import com.example.webapp.model.dto.OrderDTO;
import com.example.webapp.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@Import(TestSecurityConfig.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private OrderService service;

    @Test
    void getById_returnsOrder() throws Exception {
        OrderDTO dto = new OrderDTO(1L, "NEW", Instant.now().toString(), 5L, Set.of(1L,2L));
        given(service.getById(1L)).willReturn(dto);

        mvc.perform(get("/orders/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("NEW"));

        verify(service).getById(1L);
    }

    @Test
    void create_update_delete_flow() throws Exception {
        OrderDTO created = new OrderDTO(2L, "NEW", Instant.now().toString(), 5L, Set.of(1L));
        given(service.create(any(OrderDTO.class))).willReturn(created);

        mvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"NEW\",\"customerId\":5,\"productIds\":[1]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));

        given(service.update(eq(2L), any(OrderDTO.class))).willReturn(created);

        mvc.perform(put("/orders/2").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"NEW\",\"customerId\":5,\"productIds\":[1]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));

        mvc.perform(delete("/orders/2")).andExpect(status().isOk());
        verify(service).delete(2L);
    }
}

