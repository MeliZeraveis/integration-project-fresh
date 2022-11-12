package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

//    @Query(value = " SELECT * from meli_fresh.announcement" +
//            " where announcement.id = ?1", nativeQuery = true)
//    List<Announcement> getTeste(Long code);
}
