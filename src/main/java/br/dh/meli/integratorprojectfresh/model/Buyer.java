package br.dh.meli.integratorprojectfresh.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "buyer")
public class Buyer extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long buyerId;

    private BigDecimal mediaCompras;

    @OneToMany(mappedBy = "buyer")
    @JsonIgnoreProperties("buyer")
    private List<Announcement> announcementList;

    @OneToMany(mappedBy = "buyer")
    @JsonIgnoreProperties("buyer")
    private List<User> userList;
}
