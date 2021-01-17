package com.github.yuihzo.hibernatesandbox.domain.repositories;

import com.github.yuihzo.hibernatesandbox.domain.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query("select p from Product p order by p.name desc")
    Iterable<Product> findByDesc();
}
