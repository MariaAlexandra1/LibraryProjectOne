package repository.user;
import model.User;


import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findByUsernameAndPassword(String username, String password);

    //Notification<User> findByUsernameAndPassword(String username, String password) throws AuthenticationException;

    boolean save(User user);

    void removeAll();

    boolean existsByUsername(String username);

}
