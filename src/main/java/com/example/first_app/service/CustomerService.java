package com.example.first_app.service;

import com.example.first_app.model.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//business logic
@Component
public class CustomerService {

    private int customerIdCount = 1;
    private List<Customer> customerList = new CopyOnWriteArrayList<>();


    public Customer addCustomer(Customer customer) {
        customer.setId(customerIdCount);
        customerList.add(customer);
        customerIdCount++;
        return customer;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public Customer getCustomer(int id) {
        // streams referred : https://www.geeksforgeeks.org/stream-in-java/
        return customerList.stream().filter(c -> c.getId() == id).findFirst().get();
    }

    public Customer updateCustomer(int id, Customer customer) {
//        for (Customer c : customerList) {
//            if (customerIdCount == id) {
//                c.setFirstName(customer.getFirstName());
//                c.setLastName(customer.getLastName());
//                c.setEmail(customer.getEmail());
//                return c;
//            }
//        }
//        return null;
        Customer c = null;
        for (int i = 0 ; i<customerList.size();i++){
            if (customerList.get(i).getId() == id){
                customerList.get(i).setFirstName(customer.getFirstName());
                customerList.get(i).setLastName(customer.getLastName());
                customerList.get(i).setEmail(customer.getEmail());
                c = customerList.get(i);
            }
        }
        return c;
//        return customerList.stream().filter(c -> c.getId() == id).findFirst().get();

    }

    public void deleteCustomer(int id) {

        customerList.stream().forEach(c -> {
            if (c.getId() == id) {
                customerList.remove(c);
            }
        });

    }

}
