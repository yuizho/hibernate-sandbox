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
public class InvoiceRepositoryTests {
    @RegisterExtension
    DbRaccoonExtension dbRaccoonExtension;

    private InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceRepositoryTests(DataSource dataSource, InvoiceRepository invoiceRepository) {
        // configure db-raccoon extension
        dbRaccoonExtension = new DbRaccoonExtension.Builder(dataSource)
                .cleanupPhase(CleanupPhase.BEFORE_TEST)
                .setUpQueries(List.of("SET FOREIGN_KEY_CHECKS = 0"))
                .tearDownQueries(List.of("SET FOREIGN_KEY_CHECKS = 1"))
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
                    "1,  coffee, 200,   2018-03-13 00:45:00",
                    "2,  tea   , 100,   2018-03-13 00:45:00"
            }, id = "id"),
            @CsvTable(name = "invoice_detail", rows = {
                    "id, invoice_id, product_id, quantity, created",
                    "1,  1,          1,          1,        2018-03-13 00:45:00",
                    "2,  1,          2,          1,        2018-03-13 00:45:00"
            }, id = "id"),
    })
    public void findAllでInvoiceが取得できる() {
        // when
        var actual = invoiceRepository.findAll();
        // then
        assertThat(actual)
                .flatExtracting(Invoice::getInvoiceDetails)
                .flatExtracting(InvoiceDetail::getId)
                .containsExactly(1, 2);
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
    public void OneToManyのリレーションがあるEntityでleft_joinするとInvoiceDetailのレコードの分Invoiceの行数も増える() {
        // when
        var actual = invoiceRepository.findByIdLeftJoinFetch(1);
        // then
        actual.forEach(System.out::println);
        // One-to-ManyのリレーションがあるEntityでleft joinしているので、InvoiceDetailのレコードの分Invoiceの行数が増える
        // https://stackoverflow.com/questions/18753245/one-to-many-relationship-gets-duplicate-objects-without-using-distinct-why/18753303
        assertThat(actual).hasSize(2);
    }
}
