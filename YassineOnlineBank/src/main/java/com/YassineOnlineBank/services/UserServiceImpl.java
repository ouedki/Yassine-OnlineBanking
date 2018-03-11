package com.YassineOnlineBank.services;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.YassineOnlineBank.dao.RoleDao;
import com.YassineOnlineBank.dao.UserDao;
import com.YassineOnlineBank.models.User;
import com.YassineOnlineBank.models.UserRole;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
    private AccountService accountService;
	
	@Override
	public User createUser(User user, Set<UserRole> userRoles) {
        User localUser = userDao.findByUsername(user.getUsername());

        if (localUser != null) {
            LOG.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
        } else {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);

            for (UserRole ur : userRoles) {
                roleDao.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);

            user.setPrimaryAccount(accountService.createPrimaryAccount());
            user.setSavingsAccount(accountService.createSavingsAccount());

            localUser = userDao.save(user);
        }

        return localUser;
    }

	@Override
	public User findByUsername(String username) {
	return userDao.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public boolean checkByUsername(String username) {
		if(findByUsername(username)!=null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkByEmail(String email) {
		if(findByEmail(email)!=null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkUserExists(String username, String email) {
		if(checkByUsername(username) || checkByEmail(email)) {
			return true;
		} else {
			return false;
		}
	}
}
