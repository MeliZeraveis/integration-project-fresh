package br.dh.meli.integratorprojectfresh.model;

import br.dh.meli.integratorprojectfresh.enums.Sections;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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
    private Sections section;

    @OneToOne(mappedBy = "announcement")
    @JsonIgnoreProperties("announcement")
    private BatchStock batchStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("announcement")
    private User seller;

    @OneToMany(mappedBy = "announcement")
    @JsonIgnoreProperties("announcement")
    private List<PurchaseOrderItems> purchaseOrderItems;
}
