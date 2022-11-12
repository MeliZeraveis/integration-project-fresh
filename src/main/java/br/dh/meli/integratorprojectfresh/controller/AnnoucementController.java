package br.dh.meli.integratorprojectfresh.controller;


import br.dh.meli.integratorprojectfresh.dto.response.AnnoucementGetResponseDTO;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.service.IAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class AnnoucementController {
    @Autowired
    private IAnnouncementService service;

    @GetMapping("/list")
    ResponseEntity<AnnoucementGetResponseDTO> getAnnouncementByAnnouncementId(@RequestParam Long id) {
        AnnoucementGetResponseDTO announcementList = service.getAnnouncementByAnnouncementId(id);
        return new ResponseEntity<>(announcementList, HttpStatus.OK);
    }
}
