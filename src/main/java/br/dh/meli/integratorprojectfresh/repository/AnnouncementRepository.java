package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Announcement repository.
 */
@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    /**
     * Find by section type list.
     *
     * @param category the category
     * @return the list
     */
    List<Announcement> findBySectionType(String category);
}
