package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.SummaryProjection;

public class SummaryDTO {

    private String sellerName;
    private Double total;


    public SummaryDTO(String sellerName, Double total) {
        this.sellerName = sellerName;
        this.total = total;
    }

    public SummaryDTO(SummaryProjection projection) {
        this.sellerName = projection.getSellerName();
        this.total = projection.getTotal();
    }


    public String getSellerName() {
        return sellerName;
    }

    public Double getTotal() {
        return total;
    }
}
