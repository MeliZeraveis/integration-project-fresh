package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewPutResponseDTO {
    private Long reviewId;
    private Integer grade;
    private String comment;
    private Long announcementId;
    private Long userId;

    public ReviewPutResponseDTO(Review reviewUpdated) {
        this.reviewId = reviewUpdated.getReviewId();
        this.grade = reviewUpdated.getGrade();
        this.comment = reviewUpdated.getComment();
        this.userId = reviewUpdated.getUserId();
        this.announcementId = reviewUpdated.getAnnouncementId();
    }
}
