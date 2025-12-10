package com.example.webapp.controller;

import com.example.webapp.config.TestSecurityConfig;
import com.example.webapp.model.dto.CustomerDTO;
import com.example.webapp.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@WebMvcTest(CustomerController.class)
@Import(TestSecurityConfig.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private CustomerService service;

    @Test
    void getById_returnsCustomer() throws Exception {
        CustomerDTO dto = new CustomerDTO(1L, "Jane Doe", "jane@example.com", 10L);
        given(service.getById(1L)).willReturn(dto);

        mvc.perform(get("/customers/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullName").value("Jane Doe"));

        verify(service).getById(1L);
    }

    @Test
    void create_update_delete_flow() throws Exception {
        CustomerDTO input = new CustomerDTO(null, "Jane Doe", "jane@example.com", null);
        CustomerDTO created = new CustomerDTO(2L, "Jane Doe", "jane@example.com", null);
        given(service.create(any(CustomerDTO.class))).willReturn(created);

        mvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullName\":\"Jane Doe\",\"email\":\"jane@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));

        given(service.update(2L, input)).willReturn(created);
        mvc.perform(put("/customers/2").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullName\":\"Jane Doe\",\"email\":\"jane@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));

        mvc.perform(delete("/customers/2")).andExpect(status().isOk());
        verify(service).delete(2L);
    }
}

