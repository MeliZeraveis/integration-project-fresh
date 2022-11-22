package br.dh.meli.integratorprojectfresh.controller;


import br.dh.meli.integratorprojectfresh.dto.request.UserDTO;
import br.dh.meli.integratorprojectfresh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/user")

    public ResponseEntity<UserDTO> save(@RequestBody @Valid UserDTO user) {
        return new ResponseEntity<>(service.save(user), HttpStatus.CREATED);
    }

    @PutMapping("/user")
    public ResponseEntity<UserDTO> update(@RequestBody @Valid UserDTO userDTO, @RequestParam Long id) {
        return new ResponseEntity<>(service.update(userDTO, id), HttpStatus.CREATED);
    }

    @GetMapping("/user/role")
    public ResponseEntity<List<UserDTO>> findByRole(@RequestParam String role) {
        return new ResponseEntity<>(service.findByRole(role), HttpStatus.OK);
    }

    @GetMapping("/user/findAll")
    public ResponseEntity<List<UserDTO>> findAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
}
