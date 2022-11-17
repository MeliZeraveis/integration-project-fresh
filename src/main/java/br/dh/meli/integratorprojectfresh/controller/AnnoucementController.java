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


/**
 * The type Annoucement controller.
 */
@RestController
@RequestMapping("/api/v1/fresh-products")
public class AnnoucementController {
    @Autowired
    private IAnnouncementService service;

    /**
     * Gets announcement by announcement id.
     *
     * @param id the id
     * @return the announcement by announcement id
     */
    @GetMapping("/list")
    ResponseEntity<AnnoucementGetResponseDTO> getAnnouncementByAnnouncementId(@RequestParam Long id) {
        AnnoucementGetResponseDTO announcement = service.getAnnouncementByAnnouncementId(id);
        return new ResponseEntity<>(announcement, HttpStatus.OK);
    }

    /**
     * Find announcement by batch stock number response entity.
     *
     * @param id    the id
     * @param letra the letra
     * @return the response entity
     */
    @GetMapping("/list/batch")
    ResponseEntity<AnnoucementGetResponseDTO> findAnnouncementByBatchStockNumber(@RequestParam Long id, @RequestParam String letra) {
        AnnoucementGetResponseDTO announcement= service.findAnnouncementByBatchStockNumber(id, letra);
        return new ResponseEntity<>(announcement, HttpStatus.OK);
    }
}
