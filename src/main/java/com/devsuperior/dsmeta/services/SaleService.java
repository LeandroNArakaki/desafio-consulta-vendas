package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SummaryProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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


    public Page<SaleDTO> getReport(String minDate, String maxDate, String name, Pageable pageable) {
        verificarDatas result = getVerificarDatas(minDate, maxDate);
        Page<Sale> entity = repository.getReport(result.minDate, result.maxDate, name, pageable);
        return entity.map((SaleDTO::new));
    }


    public List<SummaryDTO> getSummary(String minDate, String maxDate) {
        verificarDatas result = getVerificarDatas(minDate, maxDate);
        return repository.getSummary(result.minDate, result.maxDate);
    }


    private static verificarDatas getVerificarDatas(String minDate, String maxDate) {
        LocalDate dateMax = null;
        LocalDate dateMin = null;

        //ultimos 12 meses
        if ((Strings.isEmpty(minDate)) && (Objects.isNull(maxDate) || Strings.isEmpty(maxDate))) {
            dateMin = LocalDate.now().minusMonths(12L);
            dateMax = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        } else {
            //data inicial vazia
            if (Strings.isEmpty(minDate) && !Strings.isEmpty(maxDate) ) {
                dateMin = LocalDate.parse(maxDate).minusYears(1L);
                dateMax = LocalDate.parse(maxDate);
            } else {
                dateMin = LocalDate.parse(minDate);
                dateMax = LocalDate.parse(maxDate);
            }

        }

        return new verificarDatas(dateMin, dateMax);
    }

    private record verificarDatas(LocalDate minDate, LocalDate maxDate) {
    }
}
