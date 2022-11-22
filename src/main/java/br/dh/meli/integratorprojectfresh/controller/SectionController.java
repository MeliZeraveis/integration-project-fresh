package br.dh.meli.integratorprojectfresh.controller;

import br.dh.meli.integratorprojectfresh.dto.request.SectionRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SectionDetailResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SectionResponseDTO;
import br.dh.meli.integratorprojectfresh.model.Section;
import br.dh.meli.integratorprojectfresh.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class SectionController {
    @Autowired
    private SectionService sectionService;

    @PostMapping("/sections")
    public ResponseEntity<SectionResponseDTO> saveSection(@RequestBody @Valid SectionRequestDTO sectionRequest) {
        return new ResponseEntity<>(sectionService.saveSection(sectionRequest), HttpStatus.CREATED);
    }

    @PutMapping("sections/{id}")
    public ResponseEntity<SectionResponseDTO> updateSection(@RequestBody @Valid SectionRequestDTO sectionRequestDTO,
                                                            @PathVariable @Valid Long id) {
        return new ResponseEntity<>(sectionService.updateSection(id, sectionRequestDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("sections/{id}")
    public ResponseEntity<Void> deleteSection(@PathVariable @Valid Long id) {
        sectionService.deleteSection(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("sections/{id}")
    public ResponseEntity<SectionDetailResponseDTO> findById(@PathVariable @Valid Long id) {
        return new ResponseEntity<>(sectionService.findById(id),HttpStatus.OK);
    }



}
