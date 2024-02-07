package customer.demoapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cds.gen.restaurantservice.Users;
import customer.demoapp.models.UserDetailsImpl;
import customer.demoapp.repo.UserRepo;
import jakarta.annotation.PostConstruct;

@Service
public class UserInfoService implements UserDetailsService {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private PasswordEncoder encoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users user = userRepo.getUserByName(username);
    if (user != null) {
      return new UserDetailsImpl(user);
    }
    throw new UsernameNotFoundException("user name not found");
  }

  public void addUser(String name, String password, String email, String roles) {
    userRepo.addUser(name, encoder.encode(password), email, roles);
  }

  @PostConstruct
  void intUsers() {
    addUser("user1", "password1", "user1@test.com", "USER");
    addUser("admin", "passadmin", "admin@test.com", "ADMIN");
  }

}
