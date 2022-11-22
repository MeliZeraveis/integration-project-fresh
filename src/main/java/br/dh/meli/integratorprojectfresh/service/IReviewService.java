package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.ReviewRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewGetResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewPutResponseDTO;

import java.util.List;

/**
 * The interface Review service.
 */
public interface IReviewService {
    /**
     * Save review post response dto.
     *
     * @param review the review
     * @return the review post response dto
     */
    ReviewPostResponseDTO save(ReviewRequestDTO review);

    /**
     * Update review put response dto.
     *
     * @param review the review
     * @param id     the id
     * @return the review put response dto
     */
    ReviewPutResponseDTO update(ReviewRequestDTO review, Long id);

    /**
     * Gets review by review id.
     *
     * @param id the id
     * @return the review by review id
     */
    ReviewGetResponseDTO getReviewByReviewId(Long id);

    /**
     * Gets all reviews.
     *
     * @param announcementId the announcement id
     * @return the all reviews
     */
    List<ReviewGetResponseDTO> getAllReviews(Long announcementId);

}
