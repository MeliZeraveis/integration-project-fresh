package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.ReviewDTO;
import br.dh.meli.integratorprojectfresh.dto.request.ReviewRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewGetResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.ReviewPutResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.ActionNotAllowedException;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import br.dh.meli.integratorprojectfresh.model.Review;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
import br.dh.meli.integratorprojectfresh.repository.PurchaseOrderRepository;
import br.dh.meli.integratorprojectfresh.repository.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;

    @Mock
    private AnnouncementRepository announcementRepository;

    ReviewDTO reviewDTO;

    ReviewRequestDTO reviewRequestDTO;

    ReviewPostResponseDTO reviewPostResponseDTO;

    ReviewPutResponseDTO reviewPutResponseDTO;

    ReviewGetResponseDTO reviewGetResponseDTO;
    Review review;

    Announcement announcement;

    PurchaseOrder purchaseOrder;



    @BeforeEach
    void setUp() {
        LocalDateTime date = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        announcement = new Announcement("maçã", "descricao", 3L, BigDecimal.valueOf(2.50), 1L);
        purchaseOrder = new PurchaseOrder(date,"Finalizado", BigDecimal.valueOf(100.00), 1L);
        reviewDTO = new ReviewDTO(4, "Muito bom", 2L, 1L);
        reviewRequestDTO = new ReviewRequestDTO(reviewDTO);
        review = new Review(reviewDTO);
        reviewRepository.save(review);
        reviewPostResponseDTO = new ReviewPostResponseDTO(review);
        reviewPutResponseDTO = new ReviewPutResponseDTO(review);
    }

   /* @Test
    void SaveMethod_ReturnNewReview_WhenParamsAreValid() {
        BDDMockito.when(announcementRepository.findById(2L))
                .thenReturn(java.util.Optional.ofNullable(announcement));
        BDDMockito.when(purchaseOrderRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(purchaseOrder));
        ReviewPostResponseDTO reviewPostResponseDTO = reviewService.save(reviewRequestDTO);
        assertThat(reviewPostResponseDTO).isNotNull();
    }

    */

    @Test
    void SaveMethod_ThrowNotFoundException_WhenAnnouncementIdIsInvalid() throws NotFoundException {

           BDDMockito.when(announcementRepository.findById(ArgumentMatchers.any()))
                    .thenReturn(java.util.Optional.ofNullable(ArgumentMatchers.any()));

           final var actualException = assertThrows(
                    NotFoundException.class,
                    () -> reviewService.validIfAnnouncementExist(2L));
           assertAll(
                    () -> Assertions.assertEquals(Msg.ANNOUNCEMENT_NOT_FOUND, actualException.getMessage()));
    }

    @Test
    void SaveMethod_ThrowNotFoundException_WhenPurchaseOrderIdIsInvalid() throws ActionNotAllowedException {

          // BDDMockito.when(purchaseOrderRepository.findByAnnouncementIdAndBuyerId(2L,1L))
                //   .thenReturn(List.of(purchaseOrder));

           final var actualException = assertThrows(
                    ActionNotAllowedException.class,
                    () -> reviewService.validIfPurchaseOrderIsApproved(2L, 1L));
           assertAll(
                    () -> Assertions.assertEquals(Msg.PURCHASE_ORDER_NOT_APPROVED, actualException.getMessage()));
    }


    @Test
    void SaveMethod_ThrowNotFoundException_WhenValidIfReviewExistIsInvalid() throws NotFoundException {

        //BDDMockito.when(announcementRepository.findById(ArgumentMatchers.any()))
               // .thenReturn(java.util.Optional.ofNullable(ArgumentMatchers.any()));

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> reviewService.validIfReviewExist(2L));
        assertAll(
                () -> Assertions.assertEquals(Msg.REVIEW_NOT_FOUND, actualException.getMessage()));
    }


}