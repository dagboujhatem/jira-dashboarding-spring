package com.vermeg.service.impl;

import com.vermeg.exceptions.EmailAlreadyUsedException;
import com.vermeg.exceptions.ResourceNotFoundException;
import com.vermeg.repositories.UserRepository;
import com.vermeg.entities.User;
import com.vermeg.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserServiceImpl implements UserDetailsService ,UserService{

	@Autowired
	MessageSource messageSource;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	private FilesStorageServiceImpl filesStorage;


	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if(user == null){
			String message = messageSource.getMessage("common.usernameNotFound",
					null, LocaleContextHolder.getLocale());
			throw new UsernameNotFoundException(message);
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	public void delete(int id) {
		Optional<User> userData = this.userRepository.findById(id);
		if (userData.isPresent()) {
			this.userRepository.deleteById(id);
		} else {
			String modelName = messageSource.getMessage("models.user",null , LocaleContextHolder.getLocale());
			String message = messageSource.getMessage("common.notFound",
					new Object[] {modelName}, LocaleContextHolder.getLocale());
			throw new ResourceNotFoundException(message);
		}
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findById(int id) {
		Optional<User> optionalUser = userRepository.findById(id);
		String modelName = messageSource.getMessage("models.user",null , LocaleContextHolder.getLocale());
		String message = messageSource.getMessage("common.notFound",
				new Object[] {modelName}, LocaleContextHolder.getLocale());
		return optionalUser.orElseThrow(() -> new ResourceNotFoundException(message));
	}

    public User update(User updatedUser) throws EmailAlreadyUsedException {
        User user = findById(updatedUser.getId());
        if(user != null) {
        	if (userRepository.existsByEmailAndIdNot(updatedUser.getEmail(), updatedUser.getId())){
				String message = messageSource.getMessage("common.emailAlreadyUsed",
						null, LocaleContextHolder.getLocale());
				throw new EmailAlreadyUsedException(message);
			}
            BeanUtils.copyProperties(updatedUser, user, "password");
			if(!updatedUser.getPassword().isEmpty()){
				user.setPassword(bcryptEncoder.encode(user.getPassword()));
			}
            userRepository.save(user);
        }
        return updatedUser;
    }

    public User save(User user) throws EmailAlreadyUsedException {
		// test if email already used
		if (this.userRepository.existsByEmail(user.getEmail())) {
			String message = messageSource.getMessage("common.emailAlreadyUsed",
					null, LocaleContextHolder.getLocale());
			throw new EmailAlreadyUsedException(message);
		}
		// Create new user account
	    User newUser = new User();
	    newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setAvatar(user.getAvatar());
        return userRepository.save(newUser);
    }

	// Profile section
	public User getProfile(Principal principal){
		if(!userRepository.existsByEmail(principal.getName())){
			String modelName = messageSource.getMessage("models.user",null , LocaleContextHolder.getLocale());
			String message = messageSource.getMessage("common.notFound",
					new Object[] {modelName}, LocaleContextHolder.getLocale());
			throw new ResourceNotFoundException(message);
		}
		return userRepository.findByEmail(principal.getName());
	}

	public void updateProfile(Principal principal, User updatedUser, MultipartFile file) throws EmailAlreadyUsedException {
		if(!userRepository.existsByEmail(principal.getName())){
			String modelName = messageSource.getMessage("models.user",null , LocaleContextHolder.getLocale());
			String message = messageSource.getMessage("common.notFound",
					new Object[] {modelName}, LocaleContextHolder.getLocale());
			throw new ResourceNotFoundException(message);
		}
		if (userRepository.existsByEmailAndIdNot(updatedUser.getEmail(), updatedUser.getId())){
			String message = messageSource.getMessage("common.emailAlreadyUsed",
					null, LocaleContextHolder.getLocale());
			throw new EmailAlreadyUsedException(message);
		}
		filesStorage.save(file);
		User user = userRepository.findByEmail(principal.getName());
		user.setFirstName(updatedUser.getFirstName());
		user.setLastName(updatedUser.getLastName());
		user.setEmail(updatedUser.getEmail());
		if(!updatedUser.getPassword().isEmpty()){
			user.setPassword(bcryptEncoder.encode(updatedUser.getPassword()));
		}
		userRepository.save(user);
	}
}
