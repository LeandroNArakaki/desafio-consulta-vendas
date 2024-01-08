package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    public SaleDTO findById(Long id) {
        Optional<Sale> result = repository.findById(id);
        Sale entity = result.get();
        return new SaleDTO(entity);
    }

    public List<SaleDTO> getReport() {
        LocalDate dateMin = LocalDate.now().minusMonths(11L);
        LocalDate dateMax = LocalDate.now();

        List<Sale> sales = repository.getReport(dateMin, dateMax);
        return sales.stream().map(x -> new SaleDTO(x)).toList();
    }

    public Page<SaleDTO> getReport(String minDate, String maxDate, String name, Pageable pageable) {
        LocalDate dateMin = LocalDate.parse(minDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate dateMax = LocalDate.parse(maxDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (Objects.isNull(dateMin) && Strings.isBlank(dateMin.toString())) {
            dateMin = dateMax.minusYears(1L);
        }

        if (Objects.isNull(dateMax) && Strings.isBlank(dateMax.toString())) {
            dateMax = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        }

        Page<Sale> entity = repository.getReport(dateMin, dateMax, name, pageable);
        return entity.map((x -> new SaleDTO(x)));
    }

    public List<SaleDTO> getSummary() {
        LocalDate dateMin = LocalDate.now().minusMonths(11L);
        LocalDate dateMax = LocalDate.now();

        List<Sale> sales = repository.getSummary(dateMin, dateMax);
        return sales.stream().map(x -> new SaleDTO(x)).toList();
    }

    public Page<SaleDTO> getSummary(String minDate, String maxDate, Pageable pageable) {
        LocalDate dateMin = LocalDate.parse(minDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate dateMax = LocalDate.parse(maxDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (Objects.isNull(dateMin) && Strings.isBlank(dateMin.toString())) {
            dateMin = dateMax.minusYears(1L);
        }

        if (Objects.isNull(dateMax) && Strings.isBlank(dateMax.toString())) {
            dateMax = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        }

        Page<Sale> entity = repository.getSummary(dateMin, dateMax, pageable);
        return entity.map((x -> new SaleDTO(x)));
    }
}
