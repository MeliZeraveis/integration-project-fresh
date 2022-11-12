package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.AnnoucementGetResponseDTO;
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
        if(announcement.isPresent()) {
            AnnoucementGetResponseDTO responseDTO = new AnnoucementGetResponseDTO(announcement.get());
            return responseDTO;
        }
        return  null;
    }
}
