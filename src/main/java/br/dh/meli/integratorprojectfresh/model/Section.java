package br.dh.meli.integratorprojectfresh.model;

import br.dh.meli.integratorprojectfresh.enums.Sections;
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
@Table(name = "section")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long sectionCode;

    @Column(name = "type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Sections type;

    @Column(name = "max_capacity", nullable = false)
    private Float maxCapacity;

    @Column(name = "used_capacity", nullable = false)
    private Float usedCapacity;

    @OneToMany(mappedBy = "section")
    @JsonIgnoreProperties("section")
    private List<InboundOrder> inboundOrder;
}
