package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Review put response dto.
 */
@Getter
@Setter
@NoArgsConstructor
public class ReviewPutResponseDTO {

    private Integer grade;
    private String comment;

    /**
     * Instantiates a new Review put response dto.
     *
     * @param reviewUpdated the review updated
     */
    public ReviewPutResponseDTO(Review reviewUpdated) {

        this.grade = reviewUpdated.getGrade();
        this.comment = reviewUpdated.getComment();

    }
}
