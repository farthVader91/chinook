package com.vgowda.hasura.chinook.controllers;

import com.vgowda.hasura.chinook.assemblers.CustomerModelAssembler;
import com.vgowda.hasura.chinook.assemblers.InvoiceModelAssembler;
import com.vgowda.hasura.chinook.models.Customer;
import com.vgowda.hasura.chinook.models.Invoice;
import com.vgowda.hasura.chinook.repositories.CustomerRepository;
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
@RequestMapping("/api/customers")
public class CustomerController {
  private final CustomerRepository customerRepository;
  private final CustomerModelAssembler customerModelAssembler;
  private final InvoiceRepository invoiceRepository;
  private final InvoiceModelAssembler invoiceModelAssembler;
  private final PagedResourcesAssembler<Customer> customerPagedResourcesAssembler;
  private final PagedResourcesAssembler<Invoice> invoicePagedResourcesAssembler;

  @Autowired
  public CustomerController(
      CustomerRepository customerRepository,
      CustomerModelAssembler customerModelAssembler,
      InvoiceRepository invoiceRepository,
      InvoiceModelAssembler invoiceModelAssembler,
      PagedResourcesAssembler<Customer> customerPagedResourcesAssembler,
      PagedResourcesAssembler<Invoice> invoicePagedResourcesAssembler) {
    this.customerRepository = customerRepository;
    this.customerModelAssembler = customerModelAssembler;
    this.invoiceRepository = invoiceRepository;
    this.invoiceModelAssembler = invoiceModelAssembler;
    this.customerPagedResourcesAssembler = customerPagedResourcesAssembler;
    this.invoicePagedResourcesAssembler = invoicePagedResourcesAssembler;
  }

  @GetMapping
  public CollectionModel<EntityModel<Customer>> getAllCustomers(Pageable pageable) {
    Page<Customer> page = customerRepository.findAll(pageable);

    return customerPagedResourcesAssembler.toModel(page, customerModelAssembler);
  }

  @GetMapping("/{customerId}")
  public EntityModel<Customer> getCustomerById(@PathVariable Long customerId) {
    Customer customer = customerRepository.findById(customerId)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer does not exist"));

    return customerModelAssembler.toModel(customer);
  }

  @GetMapping("/{customerId}/invoices")
  public CollectionModel<EntityModel<Invoice>> getInvoicesByCustomerId(
      @PathVariable Long customerId, Pageable pageable) {
    Page<Invoice> page = invoiceRepository.findAllByCustomerId(customerId, pageable);

    return invoicePagedResourcesAssembler.toModel(page, invoiceModelAssembler);
  }

}
