package br.dh.meli.integratorprojectfresh.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "warehouse")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long warehouseCode;

    @Column(name = "name", nullable = false, length = 50)
    private String warehouseName;

    @Column(name = "address", nullable = false, length = 100)
    private String warehouseAddress;

    @Column(name = "country_code", nullable = false, length = 2)
    private String warehouseCountryCode;

    @OneToMany(mappedBy = "warehouse")
    @JsonIgnoreProperties("warehouse")
    private List<InboundOrder> inboundOrder;

    @OneToOne
    @JsonIgnoreProperties("warehouse")
    @JoinColumn(name = "manager_id")
    private User manager;

}
