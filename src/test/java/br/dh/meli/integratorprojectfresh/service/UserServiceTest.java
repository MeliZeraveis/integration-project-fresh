package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.UserDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPutResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.ActionNotAllowedException;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.User;
import br.dh.meli.integratorprojectfresh.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository repo;

    UserDTO userDTO;

    User user;

    List<User> userList = new ArrayList<>();;

    @BeforeEach
    void setup() {
        List<User> userList = new ArrayList<>();
        userDTO = new UserDTO("Fulano1234", "123456", "emaildofulano@email.com", "manager");

        user = new User(userDTO);
        userList.add(user);
    }

    @Test
    @DisplayName("Exceção quando o nome ou o email esta em formato incorreto")
    void SaveMethod_ThrowsExeption_WhenNameOrEmailIncorretFormat() throws ActionNotAllowedException {

        User user1 = new User(1l, "teste", "teste", "email.com", "buyer");
       userList.add(user1);

        BDDMockito.when(repo.findAll())
                .thenReturn(userList);

        assertThrows(NotFoundException.class, () -> {
            service.validUserNameAndEmail(user1);
        });
    }
    @Test
    @DisplayName("Sucesso ao atualizar User")
    void UpdateMethod_ReturnUpdatedUser_WhenParamsAreValid() {
        User user1 = new User(1l, "teste", "teste", "email@emai.com", "buyer");
        userList.add(user1);
        userList.add(user1);
        userList.get(0).setUsername("testeuser");


        BDDMockito.when(repo.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(userList.get(0)));

        UserDTO userDTO1 = service.update(userDTO, 1l);

        assertThat(userDTO1).isNotNull();
        assertThat(userDTO1.getUsername().equals("testeuser"));
    }

    @Test
    @DisplayName("Sucesso ao procurar o User pela role")
    void FindRoleMethod_ReturnUserDTO_WhenRoleIsValid() {
        User user1 = new User(1l, "teste", "teste", "email@email.com", "buyer");
        userList.add(user1);

        List<UserDTO> userDTO1= service.findByRole("manager");

        assertThat(userDTO1).isNotNull();
    }
    @Test
    @DisplayName("Erro quando a ROLE não existe")
    void FindUserByRole_ThrowsException_WhenRoleNotFound() {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.findByRole("batatinha"));
        assertAll(
                () -> Assertions.assertEquals(Msg.ROLE_IS_NOT_EXIST, actualException.getMessage())
        );
    }

    @Test
    @DisplayName("Sucesso ao procurar uma lista de users")
    void FindAllUsersMethod_ReturnUserDTO_WhenSucess() {
        User user1 = new User(1l, "teste", "teste", "email@email.com", "buyer");
        userList.add(user1);
        User user2 = new User(1l, "teste2", "teste2", "email2@email.com", "manager");
        userList.add(user2);

        List<UserDTO> userDTO1= service.getAll();

        assertThat(userDTO1).isNotNull();
    }

}
