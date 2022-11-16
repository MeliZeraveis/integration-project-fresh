package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.model.BatchStock;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BatchStockRepository extends JpaRepository<BatchStock, Long> {
    List<BatchStock> findAllByDueDateBetweenAndSectionType(LocalDate today, LocalDate dueDate, String section );

    List<BatchStock> findAllByDueDateBetween(LocalDate today, LocalDate dueDatE);

}
