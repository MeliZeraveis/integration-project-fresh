package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The type Review list get dto.
 */
@Getter
@NoArgsConstructor
public class ReviewListGetDTO {

    private Integer grade;
    private String comment;
    private Long userId;


    /**
     * Instantiates a new Review list get dto.
     *
     * @param reviewListGetDTO the review list get dto
     */
    public ReviewListGetDTO(ReviewListGetDTO reviewListGetDTO) {
        this.grade = reviewListGetDTO.getGrade();
        this.comment = reviewListGetDTO.getComment();
        this.userId = reviewListGetDTO.getUserId();
    }

    /**
     * Instantiates a new Review list get dto.
     *
     * @param review the review
     */
    public ReviewListGetDTO(Review review) {
        this.grade = review.getGrade();
        this.comment = review.getComment();
        this.userId = review.getUserId();
    }
}
