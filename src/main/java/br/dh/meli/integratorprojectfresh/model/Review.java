package br.dh.meli.integratorprojectfresh.model;

import br.dh.meli.integratorprojectfresh.dto.request.ReviewDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * The type Review.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long reviewId;

    @Column(name = "grade", nullable = false)
    private Integer grade;

    @Column(name = "comment", nullable = false, length = 300)
    private String comment;

    @Column(name = "announcement_id", nullable = false)
    private Long announcementId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "announcement_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("review")
    private Announcement announcement;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("review")
    private User user;

    /**
     * Instantiates a new Review.
     *
     * @param review the review
     */
    public Review(ReviewDTO review) {
        this.grade = review.getGrade();
        this.comment = review.getComment();
        this.announcementId = review.getAnnouncementId();
        this.userId = review.getUserId();
    }

    /**
     * Instantiates a new Review.
     *
     * @param reviewDTO the review dto
     * @param reviewId  the review id
     */
    public Review(ReviewDTO reviewDTO, Long reviewId) {
        this.reviewId = reviewId;
        this.grade = reviewDTO.getGrade();
        this.comment = reviewDTO.getComment();
        this.announcementId = reviewDTO.getAnnouncementId();
        this.userId = reviewDTO.getUserId();
    }
}
