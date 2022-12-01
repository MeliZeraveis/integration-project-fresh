package br.dh.meli.integratorprojectfresh.model;

import br.dh.meli.integratorprojectfresh.enums.Sections;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

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

}
