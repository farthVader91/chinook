package com.vgowda.hasura.chinook.controllers;

import com.vgowda.hasura.chinook.assemblers.InvoiceLineModelAssembler;
import com.vgowda.hasura.chinook.assemblers.InvoiceModelAssembler;
import com.vgowda.hasura.chinook.models.Invoice;
import com.vgowda.hasura.chinook.models.InvoiceLine;
import com.vgowda.hasura.chinook.repositories.InvoiceLineRepository;
import com.vgowda.hasura.chinook.repositories.InvoiceRepository;
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
@RequestMapping("/api/invoices")
public class InvoiceController {
  private final InvoiceRepository invoiceRepository;
  private final InvoiceLineRepository invoiceLineRepository;
  private final InvoiceLineModelAssembler invoiceLineModelAssembler;
  private final InvoiceModelAssembler invoiceModelAssembler;
  private final PagedResourcesAssembler<Invoice> invoicePagedResourcesAssembler;
  private final PagedResourcesAssembler<InvoiceLine> invoiceLinePagedResourcesAssembler;

  @Autowired
  public InvoiceController(
      InvoiceRepository invoiceRepository,
      InvoiceLineRepository invoiceLineRepository,
      InvoiceLineModelAssembler invoiceLineModelAssembler,
      InvoiceModelAssembler invoiceModelAssembler,
      PagedResourcesAssembler<Invoice> invoicePagedResourcesAssembler,
      PagedResourcesAssembler<InvoiceLine> invoiceLinePagedResourcesAssembler) {
    this.invoiceRepository = invoiceRepository;
    this.invoiceLineRepository = invoiceLineRepository;
    this.invoiceLineModelAssembler = invoiceLineModelAssembler;
    this.invoiceModelAssembler = invoiceModelAssembler;
    this.invoicePagedResourcesAssembler = invoicePagedResourcesAssembler;
    this.invoiceLinePagedResourcesAssembler = invoiceLinePagedResourcesAssembler;
  }

  @GetMapping("/{invoiceId}")
  public EntityModel<Invoice> getInvoiceById(@PathVariable Long invoiceId) {
    Invoice invoice = invoiceRepository.findById(invoiceId)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "invoice does not exist"));

    return invoiceModelAssembler.toModel(invoice);

  }

  @GetMapping
  public CollectionModel<EntityModel<Invoice>> getAllInvoices(Pageable pageable) {
    Page<Invoice> page = invoiceRepository.findAll(pageable);

    return invoicePagedResourcesAssembler.toModel(page, invoiceModelAssembler);
  }

  @GetMapping("/{invoiceId}/lines")
  public CollectionModel<EntityModel<InvoiceLine>> getLinesByInvoiceId(
      @PathVariable Long invoiceId, Pageable pageable) {
    Page<InvoiceLine> lines = invoiceLineRepository.findAllByInvoiceId(invoiceId, pageable);

    return invoiceLinePagedResourcesAssembler.toModel(lines, invoiceLineModelAssembler);
  }
}
