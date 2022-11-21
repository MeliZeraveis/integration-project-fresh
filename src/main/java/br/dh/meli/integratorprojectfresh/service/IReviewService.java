package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.ReviewRequestDTO;
//import br.dh.meli.integratorprojectfresh.dto.response.ReviewListPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewGetResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewListGetResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewPutResponseDTO;

import java.util.List;

public interface IReviewService {
    ReviewPostResponseDTO save(ReviewRequestDTO review);
    ReviewPutResponseDTO update(ReviewRequestDTO review);
    ReviewGetResponseDTO getReviewByReviewId(Long id);
   List<ReviewGetResponseDTO> getAllReviews();

}
