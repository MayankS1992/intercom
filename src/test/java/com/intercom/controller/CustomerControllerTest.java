package com.intercom.controller;

import com.intercom.spring.SpringIntegration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class CustomerControllerTest extends SpringIntegration {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    public MockMvc getMockMvc() {
        if (this.mockMvc == null) {
            this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        }
        return this.mockMvc;
    }

    @Test
    void getCustomerWithinRange() throws Exception {
        getMockMvc().perform(get("http://localhost:8080/customerInfo/distance/100/DUBLIN")
                .contentType("application/json"))
                .andExpect(status().isCreated());
        //  MatcherAssert.assertThat(response.getBody(),equalTo("CREATED"));
    }
}