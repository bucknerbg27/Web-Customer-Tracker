package com.buckner.com.buckner.springdemo.controller;

import com.buckner.com.buckner.springdemo.dao.CustomerDAO;
import com.buckner.com.buckner.springdemo.entity.Customer;
import com.buckner.com.buckner.springdemo.service.CustomerService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    // nned to inject the customer dao
    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public String listCustomers(Model theModel){

        // get the customer from the dao
        List<Customer> theCustomers = customerService.getCustomers();

        // add customers to the model
        theModel.addAttribute("customers",theCustomers);
        return "list-customers";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel){
        // create model attribute to bind form data
        Customer theCustomer = new Customer();

        theModel.addAttribute("customer", theCustomer);

        return "customer-form";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer theCustomer){

        // save the customer using our service

        customerService.saveCustomer(theCustomer);



        return "redirect:/customer/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel){


        // get the customer from the service
        Customer theCustomer = customerService.getCustomer(theId);

        // set the customer as a model attribute to pre-populate the form
        theModel.addAttribute("customer",theCustomer);

        //send over to our form
        return "customer-form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("customerId") int theId){

        // delete the customer
        customerService.deleteCustomer(theId);

        return "redirect:/customer/list";
    }

    @PostMapping("/search")
    public String searchCustomers(@RequestParam("theSearchName") String theSearchName, Model theModel){

        // search customers from the service
        List<Customer> theCustomer = customerService.searchCustomers(theSearchName);

        // add the customers to the model
        theModel.addAttribute("customers", theCustomer);

        return "list-customers";
    }
}
