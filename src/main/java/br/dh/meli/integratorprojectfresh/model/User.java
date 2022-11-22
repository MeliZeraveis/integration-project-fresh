package br.dh.meli.integratorprojectfresh.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * The type User.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "role", nullable = false, length = 30)
    private String role;

    @OneToOne(mappedBy = "manager")
    @JsonIgnoreProperties("user")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "seller")
    @JsonIgnoreProperties("seller")
    private List<Announcement> announcement;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("user")
    private List<Review> review;

    /**
     * Instantiates a new User.
     *
     * @param id       the id
     * @param username the username
     * @param password the password
     * @param email    the email
     * @param role     the role
     */
    public User(long id, String username, String password, String email, String role) {
        this.userId = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
