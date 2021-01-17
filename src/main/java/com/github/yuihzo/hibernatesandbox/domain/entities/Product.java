package com.github.yuihzo.hibernatesandbox.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer division;
    private LocalDateTime created;
    private String name;

    public Product(int division, LocalDateTime created, String name) {
        this.division = division;
        this.created = created;
        this.name = name;
    }
}
