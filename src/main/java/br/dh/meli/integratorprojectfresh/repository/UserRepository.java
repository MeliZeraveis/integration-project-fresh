package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface User repository.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
