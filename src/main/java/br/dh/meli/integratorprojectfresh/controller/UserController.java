package br.dh.meli.integratorprojectfresh.controller;

import br.dh.meli.integratorprojectfresh.dto.response.SellerDTO;
import br.dh.meli.integratorprojectfresh.enums.Routes;
import br.dh.meli.integratorprojectfresh.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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

    @GetMapping(value = "/seller/{id}", params = {"date1","date2"})
    public ResponseEntity<SellerDTO>getUserSalesByDate(@PathVariable Long id,
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                       @RequestParam LocalDate date1,
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                       @RequestParam LocalDate date2){
        SellerDTO seller = service.getSalesByDate(id, date1, date2);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }
}
