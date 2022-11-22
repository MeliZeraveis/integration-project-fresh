package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.SectionRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SectionResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.enums.Roles;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.Section;
import br.dh.meli.integratorprojectfresh.model.User;
import br.dh.meli.integratorprojectfresh.model.Warehouse;
import br.dh.meli.integratorprojectfresh.repository.SectionRepository;
import br.dh.meli.integratorprojectfresh.repository.WarehouseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class SectionServiceTest {

    @InjectMocks
    private SectionService service;

    @Mock
    private SectionRepository sectionRepo;

    @Mock
    private WarehouseRepository warehouseRepo;

    SectionResponseDTO sectionResponseTest;
    SectionRequestDTO sectionRequestTest;
    Section sectionTest;
    Warehouse warehouseTest;
    User userTest;

    @BeforeEach
    void setup() {
        userTest = new User(1L,"test","123456","test@email.com", Roles.MANAGER);
        warehouseTest = new Warehouse(1L,"Teste","AddressTest","TR",new ArrayList<>(),new ArrayList<>(),userTest);
        sectionTest = new Section(1L, "Fresh", 50.0f, 20.0f, 1L, new ArrayList<>(), new ArrayList<>(), warehouseTest);
        sectionRequestTest = new SectionRequestDTO(sectionTest);
        sectionResponseTest = new SectionResponseDTO(sectionTest);
    }

    @Test
    void saveSectionMethod_ReturnNewSection_WhenParamsAreValid(){
        BDDMockito.when(warehouseRepo.findById(warehouseTest.getWarehouseCode()))
                .thenReturn(java.util.Optional.ofNullable(warehouseTest));
        BDDMockito.when(sectionRepo.save(ArgumentMatchers.any()))
                .thenReturn(sectionTest);

        sectionResponseTest = service.saveSection(sectionRequestTest);

        assertThat(sectionResponseTest).isNotNull();

    }

    @Test
    void saveSectionMethod_ReturnNotFoundException_WhenWarehouseNotExist() throws NotFoundException {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.saveSection(sectionRequestTest));
        assertAll(
                () -> Assertions.assertEquals(Msg.WAREHOUSE_NOT_FOUND, actualException.getMessage())
        );

    }

    @Test
    void updateSectionMethod_ReturnUpdatedSection_WhenParamsAreValid() {

        BDDMockito.when(warehouseRepo.findById(warehouseTest.getWarehouseCode()))
                .thenReturn(java.util.Optional.ofNullable(warehouseTest));
        BDDMockito.when(sectionRepo.findById(sectionTest.getSectionCode()))
                .thenReturn(java.util.Optional.ofNullable(sectionTest));
        BDDMockito.when(sectionRepo.save(ArgumentMatchers.any()))
                .thenReturn(sectionTest);

        sectionResponseTest = service.updateSection(sectionTest.getSectionCode(),sectionRequestTest);

        assertThat(sectionResponseTest).isNotNull();

    }

    @Test
    void updateSectionMethod_ReturnNotFoundException_WhenWarehouseNotExist() throws NotFoundException {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.updateSection(sectionTest.getSectionCode(),sectionRequestTest));
        assertAll(
                () -> Assertions.assertEquals(Msg.WAREHOUSE_NOT_FOUND, actualException.getMessage())
        );

    }

    @Test
    void updateSectionMethod_ReturnNotFoundException_SectionNotExist() throws NotFoundException {

        BDDMockito.when(warehouseRepo.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(warehouseTest));

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.updateSection(sectionTest.getSectionCode(), sectionRequestTest));
        assertAll(
                () -> Assertions.assertEquals(Msg.SECTION_NOT_FOUND, actualException.getMessage())
        );

    }

    @Test
    void deleteSectionMethod_ReturnNewSection_WhenParamsAreValid(){
        BDDMockito.when(warehouseRepo.findById(warehouseTest.getWarehouseCode()))
                .thenReturn(java.util.Optional.ofNullable(warehouseTest));
        BDDMockito.when(sectionRepo.save(ArgumentMatchers.any()))
                .thenReturn(sectionTest);

        sectionResponseTest = service.saveSection(sectionRequestTest);

        assertThat(sectionResponseTest).isNotNull();

    }

    @Test
    void deleteSectionMethod_ReturnNewSection_WhenSectionNotExist(){
        BDDMockito.when(warehouseRepo.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(warehouseTest));

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.deleteSection(sectionTest.getSectionCode()));
        assertAll(
                () -> Assertions.assertEquals(Msg.SECTION_NOT_FOUND, actualException.getMessage())
        );

    }



}
