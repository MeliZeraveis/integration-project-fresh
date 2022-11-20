package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.ReviewDTO;
import br.dh.meli.integratorprojectfresh.dto.request.ReviewRequestDTO;
//import br.dh.meli.integratorprojectfresh.dto.response.ReviewListPostResponseDTO;
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
import br.dh.meli.integratorprojectfresh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepo;
    private final AnnouncementRepository announcementRepo;
    private final UserRepository userRepo;
    private final PurchaseOrderRepository purchaseOrderRepo;

    void validIfAnnouncementExist(Long announcementId) {
        Optional<Announcement> announcement = announcementRepo.findById(announcementId);
        if (announcement.isEmpty()) {
            throw new NotFoundException(Msg.ANNOUNCEMENT_NOT_FOUND);
        }
    }

    void validIfPurchaseOrderIsApproved(Long announcementId, Long buyerId) {
        List<PurchaseOrder> purchaseOrder = purchaseOrderRepo.findByAnnouncementIdAndBuyerId(announcementId, buyerId);
        if (purchaseOrder.isEmpty()) {
            throw new ActionNotAllowedException("Purchase order not approved");
        }
    }

    void validIfReviewExist(Long reviewId) throws NotFoundException {
        if(reviewRepo.findById(reviewId).isEmpty()) {
            throw new NotFoundException(Msg.REVIEW_NOT_FOUND);
        }
    }

    @Override
    public ReviewPostResponseDTO save(ReviewRequestDTO reviewRequest) {
        Review review = new Review(reviewRequest.getReview());
        validIfAnnouncementExist(reviewRequest.getReview().getAnnouncementId());
        validIfPurchaseOrderIsApproved(reviewRequest.getReview().getAnnouncementId(), reviewRequest.getReview().getUserId());
        reviewRepo.save(review);
        return new ReviewPostResponseDTO(review);

    }

    @Override
    public ReviewPutResponseDTO update(ReviewRequestDTO reviewUpdate) {
        ReviewDTO reviewDTO = reviewUpdate.getReview();

        validIfAnnouncementExist(reviewUpdate.getReview().getAnnouncementId());
        validIfPurchaseOrderIsApproved(reviewUpdate.getReview().getAnnouncementId(), reviewUpdate.getReview().getUserId());

        validIfReviewExist(reviewDTO.getReviewId());

        Review review = new Review(reviewDTO, reviewDTO.getReviewId());

        Review reviewUpdated = reviewRepo.save(review);

        return new ReviewPutResponseDTO(reviewUpdated);


    }
}

