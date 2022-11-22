package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.ReviewRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewGetResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewPutResponseDTO;

import java.util.List;

public interface IReviewService {
    ReviewPostResponseDTO save(ReviewRequestDTO review);

    ReviewPutResponseDTO update(ReviewRequestDTO review, Long id);

    ReviewGetResponseDTO getReviewByReviewId(Long id);

    List<ReviewGetResponseDTO> getAllReviews(Long announcementId);

    List<ReviewGetResponseDTO> findReviewByReviewGrade(Long announcementId, Integer reviewGrade);

}
