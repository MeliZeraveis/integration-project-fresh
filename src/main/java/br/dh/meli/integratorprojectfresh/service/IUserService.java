package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.UserDTO;
import br.dh.meli.integratorprojectfresh.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public interface IUserService {
    UserDTO save(UserDTO user);
    UserDTO update(UserDTO user, Long id);
    List<UserDTO> findByRole(String role);

    void delete(Long id);
}
