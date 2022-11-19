/*
package br.dh.meli.integratorprojectfresh.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ReviewListPostResponseDTO {
    private List<ReviewPostResponseDTO> review;

    public ReviewListPostResponseDTO(List<ReviewPostResponseDTO> review) {
        this.review = review.stream().map(ReviewPostResponseDTO::new).collect(Collectors.toList());
    }
}
*/