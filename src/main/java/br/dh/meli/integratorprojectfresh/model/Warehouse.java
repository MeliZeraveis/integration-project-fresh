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

    @Column(name = "warehouse_name", nullable = false, length = 50)
    private String warehouseName;

    @OneToMany(mappedBy = "warehouse")
    @JsonIgnoreProperties("warehouse")
    private List<InboundOrder> inboundOrder;

    @OneToOne
    @JsonIgnoreProperties("warehouse")
    @JoinColumn(name = "id_user")
    private User user;

}
