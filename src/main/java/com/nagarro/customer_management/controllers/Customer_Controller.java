package com.nagarro.customer_management.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.nagarro.customer_management.models.Customer;
import com.nagarro.customer_management.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.validation.FieldError;

@RestController
@RequestMapping("/customers")
public class Customer_Controller {

    @Autowired
    private CustomerService customerService;
    
    
 // Exception handler for handling validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    	
 // Retrieve all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
    	 return customerService.getAllCustomers();

    }

 // Retrieve a customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        } else {
            String errorMessage = "Customer not found with id: " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @GetMapping("/welcome")
	public String getMessage() {
		return "Welcome to Customer Managament Service!!";
	}
    
 // Add a new customer
    @PostMapping
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) {
        try {
            Customer addedCustomer = customerService.addCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedCustomer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

 // Update an existing customer    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@Valid @PathVariable Long id, @RequestBody Customer customer) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(id, customer);
            return ResponseEntity.ok(updatedCustomer);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


 // Delete a customer by ID and account ID
    @DeleteMapping("/{id}/{accountId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id, @PathVariable Long accountId) {
        try {
            customerService.deleteCustomer(id, accountId);
            String successMessage = "Customer details with ID " + id + " and Account ID " + accountId + " deleted successfully";
            return ResponseEntity.ok(successMessage);
        } catch (Exception e) {
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
