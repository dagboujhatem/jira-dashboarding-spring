package com.vermeg;

import com.vermeg.entities.ERole;
import com.vermeg.entities.User;
import com.vermeg.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Application implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // this bean used to crypt the password
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder passwordEncoderBean = applicationContext.getBean(BCryptPasswordEncoder.class);
        return passwordEncoderBean;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = new User();
        user.setFirstName("hammami");
        user.setLastName("nadia");
        user.setRole(ERole.ROLE_HELP_DESK);
        user.setEmail("nadia@gmail.com");
        user.setPassword(this.passwordEncoder().encode("123456789"));
        this.userRepository.save(user);
    }
}
