package com.vgowda.hasura.chinook.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.vgowda.hasura.chinook.controllers.EmployeeController;
import com.vgowda.hasura.chinook.models.Employee;
import com.vgowda.hasura.chinook.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<
    Employee, EntityModel<Employee>> {
  private final CustomerRepository customerRepository;

  @Autowired
  public EmployeeModelAssembler(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public EntityModel<Employee> toModel(Employee entity) {
    EntityModel<Employee> employee = EntityModel.of(entity,
        linkTo(methodOn(EmployeeController.class)
            .getEmployeeById(entity.getId()))
            .withSelfRel());
    if (entity.getReportsTo() != null) {
      employee.add(linkTo(methodOn(EmployeeController.class)
          .getEmployeeById(entity.getReportsTo()))
          .withRel("manager"));
    }
    PageRequest pageRequest = PageRequest.of(0, 20);
    if (customerRepository
        .findAllBySupportRepId(entity.getId(), pageRequest).getTotalElements() > 0) {
      employee.add(linkTo(methodOn(EmployeeController.class)
          .getSupportedCustomersByEmployeeId(entity.getId(), pageRequest))
          .withRel("supported-customers"));
    }

    return employee;
  }
}
