package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.SectionRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SectionDetailResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SectionResponseDTO;
import br.dh.meli.integratorprojectfresh.model.Section;

public interface ISectionService {

    SectionResponseDTO saveSection(SectionRequestDTO section);
    SectionResponseDTO updateSection(Long id, SectionRequestDTO section);
    void deleteSection(Long id);
    SectionDetailResponseDTO findById(Long id);

}
