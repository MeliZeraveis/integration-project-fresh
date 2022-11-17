package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;


public interface SectionRepository extends JpaRepository<Section, Long> {
}
