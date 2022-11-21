package br.dh.meli.integratorprojectfresh.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seller_section")
public class SellerSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long sellerSectionId;

    @Column(name = "max_capacity", nullable = false)
    private Float maxCapacity;

    @Column(name = "used_capacity", nullable = false)
    private Float usedCapacity;

    @Column(name = "rental_price_month", nullable = false)
    private BigDecimal rentalPriceMonth;

    @Column(name = "section_code", nullable = false)
    private Long sectionCode;

    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("sellerSection")
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_code", insertable = false, updatable = false)
    @JsonIgnoreProperties("section")
    private Section section;

}
