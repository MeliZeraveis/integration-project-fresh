package br.dh.meli.integratorprojectfresh.controller;

import br.dh.meli.integratorprojectfresh.dto.request.SectionRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SectionDetailResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SectionListWarehouseResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SectionResponseDTO;
import br.dh.meli.integratorprojectfresh.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class SectionController {
    @Autowired
    private SectionService sectionService;

    @PostMapping("/sections")
    public ResponseEntity<SectionResponseDTO> saveSection(@RequestBody @Valid SectionRequestDTO sectionRequest) {
        return new ResponseEntity<>(sectionService.saveSection(sectionRequest), HttpStatus.CREATED);
    }

    @PutMapping("/sections/{id}")
    public ResponseEntity<SectionResponseDTO> updateSection(@RequestBody @Valid SectionRequestDTO sectionRequestDTO,
                                                            @PathVariable @Valid Long id) {
        return new ResponseEntity<>(sectionService.updateSection(id, sectionRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/sections/{id}")
    public ResponseEntity<SectionDetailResponseDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(sectionService.findById(id),HttpStatus.OK);
    }

    @GetMapping("/sections")
    public ResponseEntity<SectionListWarehouseResponseDTO> getAllSectionsByWarehouse(@RequestParam String warehouseName,
                                                                                     @RequestParam String orderBy) {
        return new ResponseEntity<>(sectionService.getAllSectionsByWarehouse(warehouseName, orderBy),HttpStatus.OK);
    }

}
