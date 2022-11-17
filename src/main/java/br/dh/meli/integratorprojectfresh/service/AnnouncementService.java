package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.BatchSotckAnnoucementDTO;
import br.dh.meli.integratorprojectfresh.dto.response.AnnoucementGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AnnouncementService implements IAnnouncementService{

    private final AnnouncementRepository repo;
    @Override
    public AnnoucementGetResponseDTO getAnnouncementByAnnouncementId(Long id) {

        Optional<Announcement> announcement = repo.findById(id);

        if(announcement.isEmpty()) {

            throw new NotFoundException(Msg.ANNOUNCEMENT_IS_EMPTY);
        }
        return new AnnoucementGetResponseDTO(announcement.get());
    }

    @Override
    public AnnoucementGetResponseDTO findAnnouncementByBatchStockNumber(Long id, String letra) {
        Optional<Announcement> announcement = repo.findById(id);

        if(announcement.isEmpty()) {
            throw new NotFoundException(Msg.ANNOUNCEMENT_IS_EMPTY);
        }
            if (letra.equalsIgnoreCase("Q") || letra.equalsIgnoreCase("L") || letra.equalsIgnoreCase("V")) {
                AnnoucementGetResponseDTO responseDTO = new AnnoucementGetResponseDTO(announcement.get(), letra);
                return responseDTO;
            } else {
                throw new NotFoundException(Msg.LETTER_NOT_VALID);
            }
    }
}
