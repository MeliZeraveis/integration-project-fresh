package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Review post response dto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewPostResponseDTO {
    private Long reviewId;
    private Integer grade;
    private String comment;
    private Long announcementId;
    private Long userId;

    /**
     * Instantiates a new Review post response dto.
     *
     * @param review the review
     */
    public ReviewPostResponseDTO(Review review) {
        this.reviewId = review.getReviewId();
        this.grade = review.getGrade();
        this.comment = review.getComment();
        this.userId = review.getUserId();
        this.announcementId = review.getAnnouncementId();
    }
}
