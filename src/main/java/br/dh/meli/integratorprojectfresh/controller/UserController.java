package br.dh.meli.integratorprojectfresh.controller;

import br.dh.meli.integratorprojectfresh.dto.response.SellerDTO;
import br.dh.meli.integratorprojectfresh.enums.Routes;
import br.dh.meli.integratorprojectfresh.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.BASE_ROUTE)
public class UserController {

    @Autowired
    private IUserService service;

    @GetMapping("/seller/{id}")
    public ResponseEntity<SellerDTO> getAllUserSales(@PathVariable Long id){
        SellerDTO seller = service.getAllUserSales(id);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }
}
