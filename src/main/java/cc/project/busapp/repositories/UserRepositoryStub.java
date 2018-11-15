package cc.project.busapp.repositories;

import cc.project.busapp.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserRepositoryStub {

    private static Map<Long, User> users = new HashMap<Long, User>();
    private static Long userIndex = 4l;

    static {
        User user1 = new User(1l, "Jhon Doe", "jhonDoe", "jhonDoe@mail.com");
        User user2 = new User(2l, "Pamela J. Travis", "PamelaJT", "PamelaJTravis@gustr.com");
        User user3 = new User(3l, "Willie D. Morrison", "WillieD", "WillieDMorrison@superrito.com");
        User user4 = new User(4l, "Homer F. Martin", "HomerF", "HomerFMartin@superrito.com");

        users.put(1l,user1);
        users.put(2l,user2);
        users.put(3l,user3);
        users.put(4l,user4);

    }

    public List<User> getAll() {
        return new ArrayList<User>(users.values());
    }


    public User getById(long id) {
        return users.get(id);
    }
}
