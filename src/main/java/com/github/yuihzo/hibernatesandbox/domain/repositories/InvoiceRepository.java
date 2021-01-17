package com.github.yuihzo.hibernatesandbox.domain.repositories;

import com.github.yuihzo.hibernatesandbox.domain.entities.Invoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Integer> {
    @Query(
            "FROM Invoice i" +
                    " LEFT JOIN FETCH i.invoiceDetails id" +
                    " WHERE i.id = :id"
    )
    Iterable<Invoice> findByIdLeftJoinFetch(int id);
}
