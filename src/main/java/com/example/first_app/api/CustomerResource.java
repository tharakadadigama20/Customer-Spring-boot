package com.example.first_app.api;

import com.example.first_app.model.Customer;
import com.example.first_app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/customers")
public class CustomerResource {

    // to use the instance variable we should use the anotations @AutoWired in this
    // and @Component in the class in which this is used.
    @Autowired
    private CustomerService service;

    // to get the data from the front end we should use the anotation @RequestBody
    // customer json will be converted to customer object
    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer){
        return service.addCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers(){
        return service.getCustomerList();
    }

    @GetMapping(value = "/{id}")
    public Customer getCustomer(@PathVariable("id") int id){
        return service.getCustomer(id);
    }

    @PutMapping(value = "/{id}")
    public Customer updateCustomer(@PathVariable("id") int id, @RequestBody Customer customer){
        return service.updateCustomer(id,customer);
    }

    @DeleteMapping(value = "/{id}")
    public Map deleteCustomer(@PathVariable("id") int id){
        service.deleteCustomer(id);
        Map<String, Object> map = new HashMap<>();
        map.put("Status", true);
        return map;
    }

}
