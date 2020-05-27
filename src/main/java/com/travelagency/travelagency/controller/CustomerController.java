package com.travelagency.travelagency.controller;

import com.travelagency.travelagency.domain.CustomerDto;
import com.travelagency.travelagency.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/travelagency/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET, value = "getCustomers")
    public List<CustomerDto> getCustomers() {
        return customerService.getCustomers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "getCustomer")
    public CustomerDto getCustomer(@RequestParam Long customerId) {
        return customerService.getCustomer(customerId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteCustomer")
    public void deleteCustomer(@RequestParam Long customerId) {
        customerService.deleteCustomer(customerId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateCustomer")
    public CustomerDto updateCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.update(customerDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "createCustomer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createCustomer(@RequestBody CustomerDto customerDto) {
        customerService.create(customerDto);
    }
}
