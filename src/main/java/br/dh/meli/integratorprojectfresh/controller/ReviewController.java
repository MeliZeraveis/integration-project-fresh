package br.dh.meli.integratorprojectfresh.controller;

import br.dh.meli.integratorprojectfresh.dto.request.ReviewRequestDTO;
//import br.dh.meli.integratorprojectfresh.dto.response.ReviewListPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewPutResponseDTO;
import br.dh.meli.integratorprojectfresh.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/fresh-product")

public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/review")

    public ResponseEntity<ReviewPostResponseDTO> save(@RequestBody @Valid ReviewRequestDTO review) {
        return new ResponseEntity<>(reviewService.save(review), HttpStatus.CREATED);
    }

    @PutMapping("/review")
    public ResponseEntity<ReviewPutResponseDTO> update(@RequestBody @Valid ReviewRequestDTO review) {
        return new ResponseEntity<>(reviewService.update(review), HttpStatus.CREATED);
    }
}
