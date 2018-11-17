package cc.project.busapp.bootstrap;

import cc.project.busapp.domain.User;
import cc.project.busapp.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap  implements CommandLineRunner {

    private UserRepository userRepository;

    public Bootstrap(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
        public void run(String... args) throws Exception {
            loadCustomer();
        }

    public void loadCustomer (){

        User user1 = new User(1l, "Jhon Doe", "jhonDoe", "jhonDoe@mail.com");
        User user2 = new User(2l, "Pamela J. Travis", "PamelaJT", "PamelaJTravis@gustr.com");
        User user3 = new User(3l, "Willie D. Morrison", "WillieD", "WillieDMorrison@superrito.com");
        User user4 = new User(4l, "Homer F. Martin", "HomerF", "HomerFMartin@superrito.com");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        System.out.println("**********************Users Loaded****************************"+ userRepository.count());


    }
}

