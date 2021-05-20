package com.example.first_app.service;

import com.example.first_app.exceptions.CustomerNotFoundException;
import com.example.first_app.model.Customer;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//business logic
@Component
public class CustomerService {

    private int customerIdCount = 1;
    private List<Customer> customerList = new CopyOnWriteArrayList<>();
    private final String FILE_NAME = "customers.txt";


    public Customer addCustomer(Customer customer) {
        if (new File("customers.txt").exists()) {
            try {
                loadFromFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        customer.setId(customerIdCount);
        customerList.add(customer);
        customerIdCount++;
        try {
            saveToFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return customer;
    }

    public List<Customer> getCustomerList() {
        if (new File("customers.txt").exists()) {
            try {
                loadFromFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return customerList;
    }

    public Customer getCustomer(int id) {
        if (new File("customers.txt").exists()) {
            try {
                loadFromFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        Customer customer = null;

        for (Customer c : customerList){
            if (c.getId() == id){
                customer = c;
            }
        }

        if (customer == null){
            throw new CustomerNotFoundException("Customer not present");
        }

        // streams referred : https://www.geeksforgeeks.org/stream-in-java/
//        return customerList.stream().filter(c -> c.getId() == id).findFirst().get();
        return customer;

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
        if (new File("customers.txt").exists()) {
            try {
                loadFromFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        Customer c = null;
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getId() == id) {
                customerList.get(i).setFirstName(customer.getFirstName());
                customerList.get(i).setLastName(customer.getLastName());
                customerList.get(i).setEmail(customer.getEmail());
                c = customerList.get(i);
                System.out.println("Ha");
            }
        }
        try {
            saveToFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        if (c == null){
            throw new CustomerNotFoundException("Not found");
        }

        return c;
//        return customerList.stream().filter(c -> c.getId() == id).findFirst().get();

    }

    public void deleteCustomer(int id) {

        if (new File("customers.txt").exists()) {
            try {
                loadFromFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
//        customerList.stream().forEach(c -> {
//            if (c.getId() == id) {
//                customerList.remove(c);
//            }
//        });
        boolean isDeleted = false;

        for (Customer c : customerList){
            if (c.getId() == id){
                customerList.remove(c);
                isDeleted = true;
            }
        }

        try {
            saveToFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        if (!isDeleted){
            throw new CustomerNotFoundException("not found");
        }

    }

    public void saveToFile() throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        for (Customer c : customerList) {
            objectOutputStream.writeObject(c);
//            System.out.println("Saved");
        }

        objectOutputStream.close();
        fileOutputStream.close();

    }

    public void loadFromFile() throws IOException {
        customerList = new CopyOnWriteArrayList<>();
        FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        while (true) {
            try {
                customerList.add((Customer) objectInputStream.readObject());
            } catch (Exception e) {
                break;
            }
        }

        customerIdCount = customerList.size() + 1;

//        System.out.println(customerList.size());
        objectInputStream.close();
        fileInputStream.close();

    }


}
