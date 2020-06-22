package com.vgowda.hasura.chinook.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.vgowda.hasura.chinook.controllers.CustomerController;
import com.vgowda.hasura.chinook.controllers.InvoiceController;
import com.vgowda.hasura.chinook.models.Invoice;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class InvoiceModelAssembler implements RepresentationModelAssembler<
    Invoice, EntityModel<Invoice>> {
  @Override
  public EntityModel<Invoice> toModel(Invoice entity) {
    return EntityModel.of(entity,
        linkTo(methodOn(InvoiceController.class)
            .getInvoiceById(entity.getId()))
            .withSelfRel(),
        linkTo(methodOn(InvoiceController.class)
            .getLinesByInvoiceId(entity.getId(), PageRequest.of(0, 20)))
            .withRel("lines"),
        linkTo(methodOn(CustomerController.class)
            .getCustomerById(entity.getCustomerId()))
            .withRel("customer"));
  }
}
