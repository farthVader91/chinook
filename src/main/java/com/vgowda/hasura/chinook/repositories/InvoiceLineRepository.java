package com.vgowda.hasura.chinook.repositories;

import com.vgowda.hasura.chinook.models.InvoiceLine;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InvoiceLineRepository extends PagingAndSortingRepository<InvoiceLine, Long> {
  Page<InvoiceLine> findAllByTrackId(Long trackId, Pageable pageable);

  Page<InvoiceLine> findAllByInvoiceId(Long invoiceId, Pageable pageable);
}
