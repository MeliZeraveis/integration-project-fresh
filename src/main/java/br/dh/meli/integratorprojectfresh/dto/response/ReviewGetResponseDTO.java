package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The type Review get response dto.
 */
@Getter
@NoArgsConstructor
public class ReviewGetResponseDTO {
    private Long reviewId;
    private Integer grade;
    private String comment;
    private Long userId;
    private Long announcementId;

    /**
     * Instantiates a new Review get response dto.
     *
     * @param review the review
     */
    public ReviewGetResponseDTO(Review review) {
        this.reviewId = review.getReviewId();
        this.grade = review.getGrade();
        this.comment = review.getComment();
        this.userId = review.getUserId();
        this.announcementId = review.getAnnouncementId();
    }

}
