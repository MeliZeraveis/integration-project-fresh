package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewListGetDTO {

    private Integer grade;
    private String comment;
    private Long userId;


    public ReviewListGetDTO(ReviewListGetDTO reviewListGetDTO) {
        this.grade = reviewListGetDTO.getGrade();
        this.comment = reviewListGetDTO.getComment();
        this.userId = reviewListGetDTO.getUserId();
    }

    public ReviewListGetDTO(Review review) {
        this.grade = review.getGrade();
        this.comment = review.getComment();
        this.userId = review.getUserId();
    }
}
