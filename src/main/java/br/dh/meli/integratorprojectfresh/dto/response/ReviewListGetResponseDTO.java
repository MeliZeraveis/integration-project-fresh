package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ReviewListGetResponseDTO {
   private Long announcementId;
   private List<ReviewListGetDTO> reviews;

    public ReviewListGetResponseDTO(List<Review> reviews) {
           this.announcementId = announcementId;
            this.reviews = reviews.stream().map(ReviewListGetDTO::new).collect(Collectors.toList());
        }


}
