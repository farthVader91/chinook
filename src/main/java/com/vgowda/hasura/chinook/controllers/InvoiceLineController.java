package com.vgowda.hasura.chinook.controllers;

import com.vgowda.hasura.chinook.assemblers.InvoiceLineModelAssembler;
import com.vgowda.hasura.chinook.models.InvoiceLine;
import com.vgowda.hasura.chinook.repositories.InvoiceLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/invoice-lines")
public class InvoiceLineController {
  private final InvoiceLineRepository invoiceLineRepository;
  private final InvoiceLineModelAssembler invoiceLineModelAssembler;
  private final PagedResourcesAssembler<InvoiceLine> pagedResourcesAssembler;

  @Autowired
  public InvoiceLineController(
      InvoiceLineRepository invoiceLineRepository,
      InvoiceLineModelAssembler invoiceLineModelAssembler,
      PagedResourcesAssembler<InvoiceLine> pagedResourcesAssembler) {
    this.invoiceLineRepository = invoiceLineRepository;
    this.invoiceLineModelAssembler = invoiceLineModelAssembler;
    this.pagedResourcesAssembler = pagedResourcesAssembler;
  }

  @RequestMapping("/{invoiceLineId}")
  public EntityModel<InvoiceLine> getInvoiceLineById(@PathVariable Long invoiceLineId) {
    InvoiceLine invoiceLine = invoiceLineRepository.findById(invoiceLineId)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "invoice-line does not exist"));

    return invoiceLineModelAssembler.toModel(invoiceLine);
  }

  @RequestMapping
  public CollectionModel<EntityModel<InvoiceLine>> getAllInvoiceLines(Pageable pageable) {
    Page<InvoiceLine> page = invoiceLineRepository.findAll(pageable);

    return pagedResourcesAssembler.toModel(page, invoiceLineModelAssembler);
  }
}
