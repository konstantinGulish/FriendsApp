package app.friends.friendsap.security;


import app.friends.friendsap.models.AppUserRepository;
import app.friends.friendsap.models.Role;
import app.friends.friendsap.models.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    AppUserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... strings) throws Exception {

        Role aRole = new Role();
        aRole.setRole("USER");
        roleRepository.save(aRole);

//        AppUser user = new AppUser();
//        user.setUsername("user");
//        user.setPassword("password");
//        user.addRole(roleRepository.findByRole("USER"));
//        userRepository.save(user);
    }

}