package repository.user;
import model.User;
import model.validator.Notification;


import java.util.List;

public interface UserRepository {

    List<User> findAllEmployee();

    Notification<User> findByUsernameAndPassword(String username, String password);

    Notification<User> findByUsername(String username);

    boolean save(User user);

    boolean delete(User user);

    void removeAll();

    boolean existsByUsername(String username);

}
