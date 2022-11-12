package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AnnouncementService implements IAnnouncementService{

    private final AnnouncementRepository repo;
    @Override
    public Optional<Announcement> getAnnouncementByAnnouncementId(Long id) {
        return repo.findById(id);
    }
}
