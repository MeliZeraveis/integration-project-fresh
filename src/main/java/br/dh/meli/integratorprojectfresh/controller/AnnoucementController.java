package br.dh.meli.integratorprojectfresh.controller;


import br.dh.meli.integratorprojectfresh.dto.response.AnnoucementGetResponseDTO;
import br.dh.meli.integratorprojectfresh.service.IAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/fresh-products")
public class AnnoucementController {
    @Autowired
    private IAnnouncementService service;

    @GetMapping("/list")
    ResponseEntity<AnnoucementGetResponseDTO> getAnnouncementByAnnouncementId(@RequestParam Long id) {
        AnnoucementGetResponseDTO announcement = service.getAnnouncementByAnnouncementId(id);
        return new ResponseEntity<>(announcement, HttpStatus.OK);
    }

    @GetMapping("/list/batch")
    ResponseEntity<AnnoucementGetResponseDTO> findAnnouncementByBatchStockNumber(@RequestParam Long id, @RequestParam String letra) {
        AnnoucementGetResponseDTO announcement= service.findAnnouncementByBatchStockNumber(id, letra);
        return new ResponseEntity<>(announcement, HttpStatus.OK);
    }
}
