package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

    @Autowired
    private SaleService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<SaleDTO> findById(@PathVariable Long id) {
        SaleDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }


    @GetMapping(value = "/report")
    public ResponseEntity<Page<SaleDTO>> getReport(@RequestParam(name = "minDate", defaultValue = "") String minDate,
                                                   @RequestParam(name = "maxDate", defaultValue = "") String maxDate,
                                                   @RequestParam(name = "name", defaultValue = "") String name,
                                                   Pageable pageable) {
        Page<SaleDTO> salesDTOs = service.getReport(minDate, maxDate, name, pageable);
        return ResponseEntity.ok((salesDTOs));
    }



    @GetMapping(value = "/summary")
    public ResponseEntity<Page<SummaryDTO>> getSummary(@RequestParam(name = "minDate", defaultValue = "") String minDate,
                                                    @RequestParam(name = "maxDate", defaultValue = "") String maxDate,
                                                    Pageable pageable) {

        Page<SummaryDTO> summaryDTOS = service.getSummary(minDate, maxDate, pageable);
        return ResponseEntity.ok((summaryDTOS));
    }
}
