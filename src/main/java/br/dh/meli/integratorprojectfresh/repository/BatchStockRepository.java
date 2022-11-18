package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.model.BatchStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface Batch stock repository.
 */
@Repository
public interface BatchStockRepository extends JpaRepository<BatchStock, Long> {
    /**
     * Find all by announcement id list.
     * @param announcementId the announcement id
     * @return the list
     */
    List<BatchStock> findAllByAnnouncementId(Long announcementId);

    /**
     * Find all by due date between list.
     * @param today   today's date
     * @param dueDate the due date
     * @return the list
     */
    List<BatchStock> findAllByDueDateBetween(LocalDate today, LocalDate dueDate);
}
