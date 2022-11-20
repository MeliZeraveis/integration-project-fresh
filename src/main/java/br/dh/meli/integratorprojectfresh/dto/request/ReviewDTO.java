package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class ReviewDTO {

    @NotNull(message = Msg.GRADE_REQUIRED)
    @Max(value = 5, message = Msg.GRADE_MAX)
    @Positive(message = Msg.GRADE_POSITIVE)
    private Integer grade;

    @NotNull(message = Msg.COMMENT_REQUIRED)
    private String comment;

    @NotNull(message = Msg.ANNOUNCEMENT_ID_REQUIRED)
    private Long announcementId;

    @NotNull(message = Msg.USER_ID_REQUIRED)
    private Long userId;

    public ReviewDTO(Integer grade, String comment, Long announcementId, Long userId) {
        this.grade = grade;
        this.comment = comment;
        this.announcementId = announcementId;
        this.userId = userId;
    }


}
