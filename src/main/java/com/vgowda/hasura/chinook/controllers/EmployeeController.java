package com.vgowda.hasura.chinook.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


import com.vgowda.hasura.chinook.assemblers.CustomerModelAssembler;
import com.vgowda.hasura.chinook.assemblers.EmployeeModelAssembler;
import com.vgowda.hasura.chinook.models.Customer;
import com.vgowda.hasura.chinook.models.Employee;
import com.vgowda.hasura.chinook.repositories.CustomerRepository;
import com.vgowda.hasura.chinook.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
  private final EmployeeRepository employeeRepository;
  private final EmployeeModelAssembler employeeModelAssembler;
  private final CustomerRepository customerRepository;
  private final PagedResourcesAssembler<Customer> customerPagedResourcesAssembler;
  private final CustomerModelAssembler customerModelAssembler;

  @Autowired
  public EmployeeController(
      EmployeeRepository employeeRepository,
      EmployeeModelAssembler employeeModelAssembler,
      CustomerRepository customerRepository,
      PagedResourcesAssembler<Customer> customerPagedResourcesAssembler,
      CustomerModelAssembler customerModelAssembler) {
    this.employeeRepository = employeeRepository;
    this.employeeModelAssembler = employeeModelAssembler;
    this.customerRepository = customerRepository;
    this.customerPagedResourcesAssembler = customerPagedResourcesAssembler;
    this.customerModelAssembler = customerModelAssembler;
  }

  @GetMapping
  public CollectionModel<EntityModel<Employee>> getAllEmployees() {
    return employeeModelAssembler.toCollectionModel(employeeRepository.findAll())
        .add(linkTo(EmployeeController.class)
            .withSelfRel());
  }

  @GetMapping("/{employeeId}")
  public EntityModel<Employee> getEmployeeById(@PathVariable Long employeeId) {
    Employee employee = employeeRepository.findById(employeeId)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "employee does not exist"));

    return employeeModelAssembler.toModel(employee);
  }

  @GetMapping("/{employeeId}/supported-customers")
  public CollectionModel<EntityModel<Customer>> getSupportedCustomersByEmployeeId(
      @PathVariable Long employeeId, Pageable pageable) {
    Page<Customer> page = customerRepository.findAllBySupportRepId(employeeId, pageable);

    return customerPagedResourcesAssembler.toModel(page, customerModelAssembler);
  }
}
