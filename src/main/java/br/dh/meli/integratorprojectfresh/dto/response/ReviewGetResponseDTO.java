package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
//@AllArgsConstructor
public class ReviewGetResponseDTO {
    private Long reviewId;
    private Integer grade;
    private String comment;
    private Long userId;
    private Long announcementId;

    public ReviewGetResponseDTO(Review review) {
        this.reviewId = review.getReviewId();
        this.grade = review.getGrade();
        this.comment = review.getComment();
        this.userId = review.getUserId();
        this.announcementId = review.getAnnouncementId();
    }

}
