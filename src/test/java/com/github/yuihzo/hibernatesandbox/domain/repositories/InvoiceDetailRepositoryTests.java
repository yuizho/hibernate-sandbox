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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InvoiceDetailRepositoryTests {
    @RegisterExtension
    DbRaccoonExtension dbRaccoonExtension;

    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    public InvoiceDetailRepositoryTests(DataSource dataSource, InvoiceDetailRepository invoiceDetailRepository) {
        // configure db-raccoon extension
        dbRaccoonExtension = new DbRaccoonExtension.Builder(dataSource)
                .cleanupPhase(CleanupPhase.BEFORE_TEST)
                .setUpQueries(List.of("SET FOREIGN_KEY_CHECKS = 0"))
                .tearDownQueries(List.of("SET FOREIGN_KEY_CHECKS = 1"))
                .build();
        // configure test target objects
        this.invoiceDetailRepository = invoiceDetailRepository;
    }

    @Test
    @CsvDataSet(testData = {
            @CsvTable(name = "invoice", rows = {
                    "id, created",
                    "1,  2018-03-13 00:45:00"
            }, id = "id"),
            @CsvTable(name = "product", rows = {
                    "id, name,   value, created",
                    "1,  coffee, 200,   2018-03-13 00:45:00",
                    "2,  tea,    150,   2018-03-13 00:45:00",
            }, id = "id"),
            @CsvTable(name = "invoice_detail", rows = {
                    "id, invoice_id, product_id, quantity, created",
                    "1,  1,          1,          1,        2018-03-13 00:45:00",
                    "2,  1,          2,          2,        2018-03-13 00:45:00",
            }, id = "id"),
    })
    public void InvoiceDetailから参照したInvoiceオブジェクトは同じものが参照される() {
        // when
        var actual = invoiceDetailRepository.findByInvoiceIdLeftJoinFetch(1);
        // then
        actual.forEach(System.out::println);
        assertThat(actual).hasSize(2);
        assertThat(actual)
                .extracting(InvoiceDetail::getInvoice)
                .extracting(Invoice::getId)
                // InvoiceDetail から参照したInvoiceオブジェクトは同じものが参照される
                .containsExactly(1, 1);
    }
}
