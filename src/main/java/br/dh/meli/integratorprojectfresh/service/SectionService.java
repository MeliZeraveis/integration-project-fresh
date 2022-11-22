package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.SectionRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SectionResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.Section;
import br.dh.meli.integratorprojectfresh.model.Warehouse;
import br.dh.meli.integratorprojectfresh.repository.SectionRepository;
import br.dh.meli.integratorprojectfresh.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SectionService implements ISectionService {

    private final SectionRepository sectionRepo;
    private final WarehouseRepository warehouseRepo;

    @Override
    public SectionResponseDTO saveSection(SectionRequestDTO section) {
        Warehouse warehouse = warehouseRepo.findById(section.getWarehouseCode())
                .orElseThrow(() -> new NotFoundException(Msg.WAREHOUSE_NOT_FOUND));

        Section newSection = new Section(section.getMaxCapacity(), section.getUsedCapacity(), warehouse.getWarehouseCode(), section.getType());
        Section savedSection = sectionRepo.save(newSection);

        return new SectionResponseDTO(savedSection);
    }

    @Override
    public SectionResponseDTO updateSection(Long id, SectionRequestDTO section) {
        Warehouse warehouse = warehouseRepo.findById(section.getWarehouseCode())
                .orElseThrow(() -> new NotFoundException(Msg.WAREHOUSE_NOT_FOUND));

        sectionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.SECTION_NOT_FOUND));

        Section editSection = new Section(section.getMaxCapacity(), section.getUsedCapacity(), warehouse.getWarehouseCode(), section.getType());
        editSection.setSectionCode(id);
        Section updatedSection = sectionRepo.save(editSection);

        return new SectionResponseDTO(updatedSection);
    }

}
