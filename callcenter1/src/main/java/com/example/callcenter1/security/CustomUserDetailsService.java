package com.example.callcenter1.security;

import com.example.callcenter1.model.User;
import com.example.callcenter1.model.Role;
import com.example.callcenter1.repository.UserRepository;
import com.example.callcenter1.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {//UserDetailsService, kullanıcı bilgilerini almak için kullanılır.
    @Autowired
    private UserRepository userRepository;//UserRepository, kullanıcı bilgilerini almak için kullanılır.
    @Autowired
    private RoleRepository roleRepository;//RoleRepository, rol bilgilerini almak için kullanılır.

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {//loadUserByUsername, kullanıcı bilgilerini almak için kullanılır.
        User user = userRepository.findByUserName(userName)//findByUserName, kullanıcı bilgilerini almak için kullanılır.
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));//User not found, kullanıcı bulunamadığında hata mesajıdır.
        Role role = roleRepository.findByRoleId(user.getRoleId())//findByRoleId, rol bilgilerini almak için kullanılır.
                .orElseThrow(() -> new UsernameNotFoundException("Role not found"));//Role not found, rol bulunamadığında hata mesajıdır.
        return org.springframework.security.core.userdetails.User//User, kullanıcı bilgilerini almak için kullanılır.
                .withUsername(user.getUserName())//getUserName, kullanıcı bilgilerini almak için kullanılır.
                .password(user.getUserPassword())//getUserPassword, kullanıcı bilgilerini almak için kullanılır.
                .roles(role.getRoleType())//getRoleType, rol bilgilerini almak için kullanılır.
                .build();//build, kullanıcı bilgilerini almak için kullanılır.
    }
} 