package com.github.yuihzo.hibernatesandbox.domain.repositories;

import com.github.yuihzo.hibernatesandbox.domain.entities.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Integer> {
}
