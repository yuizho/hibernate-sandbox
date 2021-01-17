package com.github.yuihzo.hibernatesandbox.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id")
    @OrderBy(value = "id ASC")
    private List<InvoiceDetail> invoiceDetails;

    private LocalDateTime created;
}
