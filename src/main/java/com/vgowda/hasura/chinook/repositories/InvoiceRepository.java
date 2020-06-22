package com.vgowda.hasura.chinook.repositories;

import com.vgowda.hasura.chinook.models.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, Long> {
  Page<Invoice> findAllByCustomerId(Long customerId, Pageable pageable);
}
