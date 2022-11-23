package br.dh.meli.integratorprojectfresh.controller;


import br.dh.meli.integratorprojectfresh.dto.request.UserDTO;
import br.dh.meli.integratorprojectfresh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The type User controller.
 */
@RestController
@RequestMapping("/api/v1/fresh-products")
public class UserController {

    @Autowired
    private UserService service;

    /**
     * Save response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @PostMapping("/user")

    public ResponseEntity<UserDTO> save(@RequestBody @Valid UserDTO user) {
        return new ResponseEntity<>(service.save(user), HttpStatus.CREATED);
    }

    /**
     * Update response entity.
     *
     * @param userDTO the user dto
     * @param id      the id
     * @return the response entity
     */
    @PutMapping("/user")
    public ResponseEntity<UserDTO> update(@RequestBody @Valid UserDTO userDTO, @RequestParam Long id) {
        return new ResponseEntity<>(service.update(userDTO, id), HttpStatus.CREATED);
    }

    /**
     * Find by role response entity.
     *
     * @param role the role
     * @return the response entity
     */
    @GetMapping("/user/role")
    public ResponseEntity<List<UserDTO>> findByRole(@RequestParam String role) {
        return new ResponseEntity<>(service.findByRole(role), HttpStatus.OK);
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping("/user/findAll")
    public ResponseEntity<List<UserDTO>> findAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
}
