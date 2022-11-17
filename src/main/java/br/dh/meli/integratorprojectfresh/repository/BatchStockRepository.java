package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.model.BatchStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

public interface BatchStockRepository extends JpaRepository<BatchStock, Long> {
    List<BatchStock> findAllByDueDateBetween(LocalDate today, LocalDate dueDate);

}
