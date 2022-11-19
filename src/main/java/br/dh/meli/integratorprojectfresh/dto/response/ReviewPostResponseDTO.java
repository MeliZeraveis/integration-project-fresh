package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewPostResponseDTO {
    private Long id;
    private Integer rating;
    private String comments;
    private Long announcementId;
    private Long userId;

    public ReviewPostResponseDTO(Review review) {
        this.id = review.getReviewId();
        this.rating = review.getGrade();
        this.comments = review.getComment();
        this.userId = review.getUserId();
        this.announcementId = review.getAnnouncementId();
    }
}
