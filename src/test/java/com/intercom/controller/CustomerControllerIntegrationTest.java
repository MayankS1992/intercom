package com.intercom.controller;

import com.intercom.IntercomDistanceCalc;
import com.intercom.model.Customer;
import com.intercom.service.CustomerService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntercomDistanceCalc.class)
class CustomerControllerIntegrationTest {

    MockMvc mockMvc;
    @Mock
    private CustomerService service;
    @InjectMocks
    private CustomerController controller;

    @Before
    public void preTest() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void getCustomerWithinRange() {
        try {
            when(service.getCustomersWithinRadius(100,"DUBLIN")).thenReturn(new ArrayList<Customer>());
            mockMvc
                    .perform(get("/distance/100/DUBLIN")
                            .contentType("application/json"))
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}