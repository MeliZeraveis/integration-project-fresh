package br.dh.meli.integratorprojectfresh.controller;

import br.dh.meli.integratorprojectfresh.dto.request.ReviewRequestDTO;
//import br.dh.meli.integratorprojectfresh.dto.response.ReviewListPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewGetResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewListGetResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewPutResponseDTO;
import br.dh.meli.integratorprojectfresh.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-product")

public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/review")

    public ResponseEntity<ReviewPostResponseDTO> save(@RequestBody @Valid ReviewRequestDTO review) {
        return new ResponseEntity<>(reviewService.save(review), HttpStatus.CREATED);
    }

    @PutMapping("/review/{id}")
    public ResponseEntity<ReviewPutResponseDTO> update(@RequestBody @Valid ReviewRequestDTO review, @PathVariable Long id) {
        return new ResponseEntity<>(reviewService.update(review, id), HttpStatus.CREATED);
    }

    @GetMapping("/review")
    public ResponseEntity<ReviewGetResponseDTO> getReviewByReviewId(@RequestParam Long id) {
        ReviewGetResponseDTO review = reviewService.getReviewByReviewId(id);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @GetMapping("/review/list/{announcementId}")
    public ResponseEntity<List<ReviewGetResponseDTO>> getAllReviews(@PathVariable Long announcementId) {
        return new ResponseEntity<>(reviewService.getAllReviews(announcementId), HttpStatus.OK);
    }
}
