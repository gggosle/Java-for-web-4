package com.example.webapp.controller;

import com.example.webapp.config.TestSecurityConfig;
import com.example.webapp.model.dto.ProductDTO;
import com.example.webapp.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Import(TestSecurityConfig.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private ProductService service;

    @Test
    void getById_returnsProduct() throws Exception {
        ProductDTO dto = new ProductDTO(1L, "Widget", "Nice widget", new BigDecimal("9.99"));
        given(service.getById(1L)).willReturn(dto);

        mvc.perform(get("/products/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Widget"));

        verify(service).getById(1L);
    }

    @Test
    void create_update_delete_flow() throws Exception {
        ProductDTO input = new ProductDTO(null, "Widget", "Nice widget", new BigDecimal("9.99"));
        ProductDTO created = new ProductDTO(2L, "Widget", "Nice widget", new BigDecimal("9.99"));
        given(service.create(any(ProductDTO.class))).willReturn(created);

        mvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Widget\",\"description\":\"Nice widget\",\"price\":9.99}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));

        given(service.update(2L, input)).willReturn(created);
        mvc.perform(put("/products/2").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Widget\",\"description\":\"Nice widget\",\"price\":9.99}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));

        mvc.perform(delete("/products/2")).andExpect(status().isOk());
        verify(service).delete(2L);
    }
}

