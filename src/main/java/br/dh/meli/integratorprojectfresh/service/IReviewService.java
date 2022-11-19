package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.ReviewRequestDTO;
//import br.dh.meli.integratorprojectfresh.dto.response.ReviewListPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewPostResponseDTO;

public interface IReviewService {
    ReviewPostResponseDTO save(ReviewRequestDTO review);

    ReviewPostResponseDTO update(ReviewRequestDTO review);

}
