package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.SectionRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SectionResponseDTO;

public interface ISectionService {

    SectionResponseDTO saveSection(SectionRequestDTO section);
    SectionResponseDTO updateSection(Long id, SectionRequestDTO section);



}
