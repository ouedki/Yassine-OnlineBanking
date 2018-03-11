package com.YassineOnlineBank.services;

import java.util.Set;

import com.YassineOnlineBank.models.User;
import com.YassineOnlineBank.models.UserRole;

public interface UserService {
	User createUser(User user, Set<UserRole> userRoles);
	User findByUsername(String username);
	User findByEmail(String username);
	boolean checkByUsername(String username);
	boolean checkByEmail(String email);
	boolean checkUserExists(String username, String email);
}
