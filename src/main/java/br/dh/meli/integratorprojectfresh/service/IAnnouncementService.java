package br.dh.meli.integratorprojectfresh.service;

//import br.dh.meli.integratorprojectfresh.dto.request.AnnouncementDTO;
import br.dh.meli.integratorprojectfresh.model.Announcement;

import java.util.List;
import java.util.Optional;

public interface IAnnouncementService {
    Optional<Announcement> getAnnouncementByAnnouncementId(Long id);
}
