package app.security.daos;

import app.security.entities.User;
import dk.bugelhartmann.UserDTO;
import io.javalin.validation.ValidationException;

public interface ISecurityDAO {
    UserDTO getVerifiedUser(String username, String password) throws ValidationException, app.security.exceptions.ValidationException;
    User createUser(String username, String password);
    User addRole(UserDTO user, String newRole);
}
