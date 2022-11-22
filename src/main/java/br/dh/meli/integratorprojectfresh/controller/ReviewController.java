package br.dh.meli.integratorprojectfresh.controller;

import br.dh.meli.integratorprojectfresh.dto.request.ReviewRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.*;
import br.dh.meli.integratorprojectfresh.enums.Routes;
import br.dh.meli.integratorprojectfresh.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Review controller.
 */
@RestController
@RequestMapping("/api/v1/fresh-product")

public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * Save response entity.
     *
     * @param review the review
     * @return the response entity
     */
    @PostMapping("/review")

    public ResponseEntity<ReviewPostResponseDTO> save(@RequestBody @Valid ReviewRequestDTO review) {
        return new ResponseEntity<>(reviewService.save(review), HttpStatus.CREATED);
    }

    /**
     * Update response entity.
     *
     * @param review the review
     * @param id     the id
     * @return the response entity
     */
    @PutMapping("/review/{id}")
    public ResponseEntity<ReviewPutResponseDTO> update(@RequestBody @Valid ReviewRequestDTO review, @PathVariable Long id) {
        return new ResponseEntity<>(reviewService.update(review, id), HttpStatus.CREATED);
    }

    /**
     * Gets review by review id.
     *
     * @param id the id
     * @return the review by review id
     */
    @GetMapping("/review")
    public ResponseEntity<ReviewGetResponseDTO> getReviewByReviewId(@RequestParam Long id) {
        ReviewGetResponseDTO review = reviewService.getReviewByReviewId(id);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    /**
     * Gets all reviews.
     *
     * @param announcementId the announcement id
     * @return the all reviews
     */
    @GetMapping("/review/list")
    public ResponseEntity<List<ReviewGetResponseDTO>> getAllReviews(@RequestParam Long announcementId) {
        return new ResponseEntity<>(reviewService.getAllReviews(announcementId), HttpStatus.OK);
    }

    /**
     * Find review by review grade response entity.
     *
     * @param announcementId the announcement id
     * @param reviewGrade    the review grade
     * @return the response entity
     */
    @GetMapping("review/list/grade")
    @ResponseBody
    public ResponseEntity<List<ReviewGetResponseDTO>> findReviewByReviewGrade(@RequestParam Long announcementId, @RequestParam Integer reviewGrade) {
        List<ReviewGetResponseDTO> review = reviewService.findReviewByReviewGrade(announcementId, reviewGrade);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }
}
