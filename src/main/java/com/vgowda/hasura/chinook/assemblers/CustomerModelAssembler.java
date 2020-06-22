package com.vgowda.hasura.chinook.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.vgowda.hasura.chinook.controllers.CustomerController;
import com.vgowda.hasura.chinook.controllers.EmployeeController;
import com.vgowda.hasura.chinook.models.Customer;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<
    Customer, EntityModel<Customer>> {
  @Override
  public EntityModel<Customer> toModel(Customer entity) {
    return EntityModel.of(entity,
        linkTo(methodOn(CustomerController.class)
            .getCustomerById(entity.getId()))
            .withSelfRel(),
        linkTo(methodOn(CustomerController.class)
            .getInvoicesByCustomerId(entity.getId(), PageRequest.of(0, 20)))
            .withRel("invoices"),
        linkTo(methodOn(EmployeeController.class)
            .getEmployeeById(entity.getSupportRepId()))
            .withRel("support-rep")
    );
  }
}
