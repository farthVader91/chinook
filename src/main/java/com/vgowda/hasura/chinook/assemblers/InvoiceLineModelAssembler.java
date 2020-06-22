package com.vgowda.hasura.chinook.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.vgowda.hasura.chinook.controllers.InvoiceController;
import com.vgowda.hasura.chinook.controllers.InvoiceLineController;
import com.vgowda.hasura.chinook.controllers.TrackController;
import com.vgowda.hasura.chinook.models.InvoiceLine;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class InvoiceLineModelAssembler implements RepresentationModelAssembler<
    InvoiceLine, EntityModel<InvoiceLine>> {
  @Override
  public EntityModel<InvoiceLine> toModel(InvoiceLine entity) {
    return EntityModel.of(entity,
        linkTo(methodOn(InvoiceLineController.class)
            .getInvoiceLineById(entity.getId()))
            .withSelfRel(),
        linkTo(methodOn(TrackController.class)
            .getTrackById(entity.getTrackId()))
            .withRel("track"),
        linkTo(methodOn(InvoiceController.class)
            .getInvoiceById(entity.getInvoiceId()))
            .withRel("invoice")
    );
  }
}
