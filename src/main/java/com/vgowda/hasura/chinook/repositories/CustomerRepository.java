package com.vgowda.hasura.chinook.repositories;

import com.vgowda.hasura.chinook.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
  Page<Customer> findAllBySupportRepId(Long supportRepId, Pageable pageable);
}
