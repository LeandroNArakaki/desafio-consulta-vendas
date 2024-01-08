package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s FROM Sale s WHERE s.date BETWEEN :minDate and :maxDate")
    List<Sale> getReport(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate);

    @Query("SELECT s FROM Sale s  WHERE upper(s.seller.name) LIKE UPPER(concat('%', :name, '%')) AND s.date BETWEEN :minDate and :maxDate ")
    Page<Sale> getReport(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, @Param("name") String name, Pageable pageable);

    @Query("SELECT s.seller.name, sum(s.amount) FROM Sale s  WHERE s.date BETWEEN :minDate and :maxDate GROUP BY s.seller.name ")
    List<Sale> getSummary(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate);

    @Query("SELECT s.seller.name, sum(s.amount) FROM Sale s WHERE s.date BETWEEN :minDate and :maxDate GROUP BY s.seller.name ORDER BY s.seller.name ")
    Page<Sale> getSummary(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, Pageable pageable);
}
