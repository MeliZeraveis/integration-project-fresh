package br.dh.meli.integratorprojectfresh.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * The type Announcement.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "announcement")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long announcementId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Column(name = "seller_id", nullable = false, length = 50)
    private Long sellerId;

    @Column(name = "price", nullable = false, precision = 11, scale = 2)
    private BigDecimal price;

    @Column(name = "section", nullable = false, length = 50)
    private Long sectionCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "section", insertable = false, updatable = false)
    @JsonIgnoreProperties("announcement")
    private Section section;

    @OneToMany(mappedBy = "announcement")
    @JsonIgnoreProperties("announcement")
    private List<BatchStock> batchStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", insertable = false, updatable = false)
    @JsonIgnore
    @JsonIgnoreProperties("announcement")
    private User seller;

    @OneToMany(mappedBy = "announcement")
    @JsonIgnore
    @JsonIgnoreProperties("announcement")
    private List<PurchaseOrderItems> purchaseOrderItems;


    @OneToMany(mappedBy = "announcement", fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties("announcement")
    private List<Review> review;

    /**
     * Instantiates a new Announcement.
     *
     * @param name        the name
     * @param description the description
     * @param sellerId    the seller id
     * @param price       the price
     * @param sectionCode the section code
     */
    public Announcement(String name, String description, Long sellerId, BigDecimal price, Long sectionCode) {
        this.name = name;
        this.description = description;
        this.sellerId = sellerId;
        this.price = price;
        this.sectionCode = sectionCode;
    }
}
