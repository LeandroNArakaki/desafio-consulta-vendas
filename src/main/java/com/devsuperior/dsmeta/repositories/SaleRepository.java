package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {


    @Query(value = "SELECT s FROM Sale s join fetch s.seller sl  WHERE upper(sl.name) LIKE UPPER(concat('%', :name, '%')) AND s.date BETWEEN :minDate and :maxDate",
            countQuery = "SELECT count(s) FROM Sale s join s.seller sl  WHERE upper(sl.name) LIKE UPPER(concat('%', :name, '%')) AND s.date BETWEEN :minDate and :maxDate")
    Page<Sale> getReport(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, @Param("name") String name, Pageable pageable);


//    @Query(value = "SELECT  sl.name AS sellerName, SUM(s.amount) AS total FROM tb_sales s INNER JOIN tb_seller sl ON sl.id = s.seller_id  " +
//            "WHERE s.date BETWEEN :minDate AND :maxDate GROUP BY sl.name",
//            nativeQuery = true)
//    Page<SummaryProjection> getSummary(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, Pageable pageable);

    @Query(value = "SELECT new com.devsuperior.dsmeta.dto.SummaryDTO(s.seller.name , SUM(s.amount)) FROM Sale s " +
            "WHERE s.date BETWEEN :minDate AND :maxDate GROUP BY s.seller.name")
    List<SummaryDTO> getSummary(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate);
}
