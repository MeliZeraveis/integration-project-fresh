package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.annotations.OneOf;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.enums.Roles;
import br.dh.meli.integratorprojectfresh.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    @NotNull(message = Msg.USER_NAME_REQUIRED)
    @Size(max=200)
    private String username;

    @NotNull(message = Msg.PASSWORD_REQUIRED)
    private String password;

    @NotNull(message = Msg.EMAIL_REQUIRED)
    @Email(message = Msg.EMAIL_INCORRET)
    private String email;

    @NotNull(message = Msg.ROLE_REQUIRED)
    @OneOf(value = {Roles.SELLER, Roles.MANAGER, Roles.BUYER}, message = Msg.ROLE_IS_NOT_EXIST)
    private String role;

    public UserDTO(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
