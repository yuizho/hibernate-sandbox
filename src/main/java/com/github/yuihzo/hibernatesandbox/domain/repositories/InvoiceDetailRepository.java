package com.github.yuihzo.hibernatesandbox.domain.repositories;

import com.github.yuihzo.hibernatesandbox.domain.entities.InvoiceDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceDetailRepository extends CrudRepository<InvoiceDetail, Integer> {
    @Query(
            "FROM InvoiceDetail id" +
                    " LEFT JOIN FETCH id.invoice i" +
                    " WHERE i.id = :id"
    )
    Iterable<InvoiceDetail> findByInvoiceIdLeftJoinFetch(int id);
}
