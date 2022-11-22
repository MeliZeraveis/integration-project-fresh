package br.dh.meli.integratorprojectfresh.service;

//import br.dh.meli.integratorprojectfresh.dto.request.UserDTO;
import br.dh.meli.integratorprojectfresh.dto.request.UserDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.User;
import br.dh.meli.integratorprojectfresh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    @Autowired
    UserRepository repo;

    void validUserNameAndEmail(User user) {
        List<User> userList = repo.findAll();
        for(User u : userList) {
            if(u.getUsername().equalsIgnoreCase(user.getUsername()) || u.getEmail().equalsIgnoreCase(user.getEmail()))
                throw new NotFoundException(Msg.USER_NAME_OR_EMAIL_ALREADY_REGISTERED);
        }
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = new User(userDTO);
        validUserNameAndEmail(user);
        repo.save(user);
        return userDTO;
    }

    @Override
    public UserDTO update(UserDTO user, Long id) {
        Optional<User> optionalUser = repo.findById(id);
        if (optionalUser.isEmpty()){
            throw new NotFoundException(Msg.USER_NOT_FOUND);}
        User updateUser = optionalUser.get();

       // validUserNameAndEmail(updateUser);
        updateUser.setUsername(user.getUsername());
        updateUser.setEmail(user.getEmail());
        updateUser.setPassword(user.getPassword());
        updateUser.setRole(user.getRole());

        repo.save(updateUser);
        return user;
    }

    @Override
    public List<UserDTO> findByRole(String role) {
        if(role.equalsIgnoreCase("manager") || role.equalsIgnoreCase("buyer") || role.equalsIgnoreCase("seller")) {
            List<User> user = repo.findUserByRole(role);
            List<UserDTO> userDTO = user.stream().map(UserDTO::new).collect(Collectors.toList());
            return userDTO;
        }
        throw new NotFoundException(Msg.ROLE_IS_NOT_EXIST);
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> userList = repo.findAll();
        List<UserDTO> userDTO = userList.stream().map(UserDTO::new).collect(Collectors.toList());
        return userDTO;
    }

//    @Override
//    public void delete(Long id) {repo.deleteById(id);}

}
