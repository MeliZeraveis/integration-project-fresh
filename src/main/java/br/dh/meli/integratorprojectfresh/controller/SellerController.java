package br.dh.meli.integratorprojectfresh.controller;

import br.dh.meli.integratorprojectfresh.dto.response.SalesSallerListDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SellerDTO;
import br.dh.meli.integratorprojectfresh.enums.Routes;
import br.dh.meli.integratorprojectfresh.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(Routes.BASE_ROUTE)
public class SellerController {

    @Autowired
    private ISellerService service;

    @GetMapping("/seller/{id}")
    public ResponseEntity<SellerDTO> getAllUserSales(@PathVariable Long id){
        SellerDTO seller = service.getSeller(id);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }


    @GetMapping(value = "/seller/{id}/details")
    public ResponseEntity<SalesSallerListDTO>getUserSalesByDate(@PathVariable Long id){
        SalesSallerListDTO seller = service.getAllSallesSales(id);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }
    @GetMapping(value = "/seller/{id}/details", params = {"date1","date2"})
    public ResponseEntity<SalesSallerListDTO>getUserSalesByDate(@PathVariable Long id,
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                       @RequestParam LocalDate date1,
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                       @RequestParam LocalDate date2){
        SalesSallerListDTO seller = service.getSalesByDate(id, date1, date2);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }
}
