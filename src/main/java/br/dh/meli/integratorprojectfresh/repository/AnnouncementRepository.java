package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
  List<Announcement> findBySectionType(String category);

  List<Announcement> findByNameContainingIgnoreCase(String queryString);
  List<Announcement> findByPriceBetweenOrderByPrice(BigDecimal min, BigDecimal max);
}
