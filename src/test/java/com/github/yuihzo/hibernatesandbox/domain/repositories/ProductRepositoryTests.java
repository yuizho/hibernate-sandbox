package com.github.yuihzo.hibernatesandbox.domain.repositories;

import com.github.yuihzo.hibernatesandbox.domain.entities.Product;
import com.github.yuizho.dbraccoon.CleanupPhase;
import com.github.yuizho.dbraccoon.DbRaccoonExtension;
import com.github.yuizho.dbraccoon.annotation.CsvDataSet;
import com.github.yuizho.dbraccoon.annotation.CsvTable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductRepositoryTests {
    @RegisterExtension
    DbRaccoonExtension dbRaccoonExtension;

    private ProductRepository productRepository;

    @Autowired
    public ProductRepositoryTests(DataSource dataSource, ProductRepository productRepository) {
        // configure db-raccoon extension
        dbRaccoonExtension = new DbRaccoonExtension.Builder(dataSource)
                .cleanupPhase(CleanupPhase.BEFORE_TEST)
                .build();
        // configure test target objects
        this.productRepository = productRepository;
    }

    @Test
    @CsvDataSet(testData = {
            @CsvTable(name = "product", rows = {
                    "id, name,   value, created",
                    "1,  coffee, 200,   2018-03-13 00:45:00",
                    "2,  tea,    150,   2018-03-13 00:45:01"
            }, id = "id")
    })
    public void findByDescで降順にProductが取得できる() {
        // when
        var actual = productRepository.findByDesc();
        // then
        assertThat(actual).containsExactly(
                new Product(
                        2,
                        "tea",
                        150,
                        LocalDateTime.of(2018, 3, 13, 0, 45, 1)

                ),
                new Product(
                        1,
                        "coffee",
                        200,
                        LocalDateTime.of(2018, 3, 13, 0, 45, 0)

                )
        );
    }
}
