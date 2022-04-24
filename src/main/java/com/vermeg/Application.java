package com.vermeg;

import com.vermeg.entities.ERole;
import com.vermeg.entities.User;
import com.vermeg.repositories.UserRepository;
import com.vermeg.service.impl.FilesStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

@SpringBootApplication
@EnableSwagger2
public class Application implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Resource
    FilesStorageServiceImpl storageService;

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
        // Insert default user (with ADMIN role as default)
        this.userRepository.deleteAll();
        User user1 = new User();
        user1.setFirstName("Hammami");
        user1.setLastName("Nadia");
        user1.setRole(ERole.ROLE_ADMIN);
        user1.setEmail("hammaminadia293@gmail.com");
        user1.setPassword(this.passwordEncoder().encode("123456789"));
        this.userRepository.save(user1);

        this.userRepository.deleteAll();
        User user2 = new User();
        user2.setFirstName("Dagbouj");
        user2.setLastName("Hatem");
        user2.setRole(ERole.ROLE_ADMIN);
        user2.setEmail("dagboujhatem@gmail.com");
        user2.setPassword(this.passwordEncoder().encode("123456789"));
        this.userRepository.save(user2);
        // Files Storage section
        storageService.deleteAll();
        storageService.init();
    }
}
