package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.dto.request.UserDTO;
import br.dh.meli.integratorprojectfresh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find user by role list.
     *
     * @param role the role
     * @return the list
     */
    List<User> findUserByRole(String role);

    /**
     * Delete user by email.
     *
     * @param email the email
     */
    void deleteUserByEmail(String email);
}
