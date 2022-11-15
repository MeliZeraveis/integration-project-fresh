package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.model.BatchStock;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BatchStockRepository extends JpaRepository<BatchStock, Long> {
   // List<BatchStock> findAllByDueDateAndSectionType(LocalDate plusDays, String section);
    List<BatchStock> findAllByDueDateThanG
}
