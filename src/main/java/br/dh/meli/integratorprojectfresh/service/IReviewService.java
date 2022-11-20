package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.ReviewRequestDTO;
//import br.dh.meli.integratorprojectfresh.dto.response.ReviewListPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewPutResponseDTO;

public interface IReviewService {
    ReviewPostResponseDTO save(ReviewRequestDTO review);
    ReviewPutResponseDTO update(ReviewRequestDTO review);

}
