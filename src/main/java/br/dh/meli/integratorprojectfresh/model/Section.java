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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_code", insertable = false, updatable = false)
    @JsonIgnoreProperties("section")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "section")
    @JsonIgnoreProperties(value = {"section", "batchStock", "warehouse" })
    private List<InboundOrder> inboundOrder;

    public  Section(long sectionCode, String type, float maxCapacity, float usedCapacity, ArrayList<Announcement> listAnnoucement, Long warehouseCode) {
    this.sectionCode = sectionCode;
    this.type = type;
    this.maxCapacity = maxCapacity;
    this.usedCapacity = usedCapacity;
    this.announcement = listAnnoucement;
    this.warehouseCode =  warehouseCode;
    }
}
