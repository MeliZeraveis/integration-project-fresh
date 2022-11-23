package br.dh.meli.integratorprojectfresh.model;

import br.dh.meli.integratorprojectfresh.enums.Sections;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Section.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "section")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long sectionCode;

    @Column(name = "type", nullable = false, length = 50)
//    @Enumerated(EnumType.STRING)
    private String type;

    @Column(name = "max_capacity", nullable = false)
    private Float maxCapacity;

    @Column(name = "used_capacity", nullable = false)
    private Float usedCapacity;

    @Column(name = "warehouse_code", nullable = false)
    private Long warehouseCode;

    @OneToMany(mappedBy = "section")
    @JsonIgnoreProperties("section")
    private List<Announcement> announcement;

    @OneToMany(mappedBy = "section")
    @JsonIgnoreProperties(value = {"section", "batchStock", "warehouse" })
    private List<InboundOrder> inboundOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_code", insertable = false, updatable = false)
    @JsonIgnoreProperties("section")
    private Warehouse warehouse;

    /**
     * Instantiates a new Section.
     *
     * @param sectionCode     the section code
     * @param type            the type
     * @param maxCapacity     the max capacity
     * @param usedCapacity    the used capacity
     * @param listAnnoucement the list annoucement
     */
    public Section(long sectionCode, String type, float maxCapacity, float usedCapacity, ArrayList<Announcement> listAnnoucement) {
    this.sectionCode = sectionCode;
    this.type = type;
    this.maxCapacity = maxCapacity;
    this.usedCapacity = usedCapacity;
    this.announcement = listAnnoucement;

    }
}
