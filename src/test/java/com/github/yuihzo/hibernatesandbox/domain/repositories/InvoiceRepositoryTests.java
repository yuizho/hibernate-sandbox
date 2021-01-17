package com.github.yuihzo.hibernatesandbox.domain.repositories;

import com.github.yuihzo.hibernatesandbox.domain.entities.Invoice;
import com.github.yuihzo.hibernatesandbox.domain.entities.InvoiceDetail;
import com.github.yuizho.dbraccoon.CleanupPhase;
import com.github.yuizho.dbraccoon.DbRaccoonExtension;
import com.github.yuizho.dbraccoon.annotation.CsvDataSet;
import com.github.yuizho.dbraccoon.annotation.CsvTable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InvoiceRepositoryTests {
    @RegisterExtension
    DbRaccoonExtension dbRaccoonExtension;

    private InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceRepositoryTests(DataSource dataSource, InvoiceRepository invoiceRepository) {
        // configure db-raccoon extension
        dbRaccoonExtension = new DbRaccoonExtension.Builder(dataSource)
                .cleanupPhase(CleanupPhase.BEFORE_TEST)
                .build();
        // configure test target objects
        this.invoiceRepository = invoiceRepository;
    }

    @Test
    @CsvDataSet(testData = {
            @CsvTable(name = "invoice", rows = {
                    "id, created",
                    "1,  2018-03-13 00:45:00"
            }, id = "id"),
            @CsvTable(name = "product", rows = {
                    "id, name,   value, created",
                    "1,  coffee, 200,   2018-03-13 00:45:00"
            }, id = "id"),
            @CsvTable(name = "invoice_detail", rows = {
                    "id, invoice_id, product_id, quantity, created",
                    "1,  1,          1,          1,        2018-03-13 00:45:00"
            }, id = "id"),
    })
    public void findAllでInvoiceが取得できる() {
        // when
        var actual = invoiceRepository.findAll();
        // then
        assertThat(actual)
                .flatExtracting(Invoice::getInvoiceDetails)
                .flatExtracting(InvoiceDetail::getId)
                .containsExactly(1);
    }
}
