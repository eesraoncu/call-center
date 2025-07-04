package com.example.callcenter1.security;

import com.example.callcenter1.model.Operator;
import com.example.callcenter1.model.Role;
import com.example.callcenter1.repository.OperatorRepository;
import com.example.callcenter1.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {//UserDetailsService, operator bilgilerini almak için kullanılır.
    @Autowired
    private OperatorRepository operatorRepository;//OperatorRepository, operator bilgilerini almak için kullanılır.
    @Autowired
    private RoleRepository roleRepository;//RoleRepository, rol bilgilerini almak için kullanılır.

    @Override
    public UserDetails loadUserByUsername(String operatorName) throws UsernameNotFoundException {//loadUserByUsername, operator bilgilerini almak için kullanılır.
        Operator operator = operatorRepository.findByOperatorName(operatorName)//findByOperatorName, operator bilgilerini almak için kullanılır.
                .orElseThrow(() -> new UsernameNotFoundException("Operator not found"));//Operator not found, operator bulunamadığında hata mesajıdır.
        Role role = roleRepository.findByRoleId(operator.getRoleId())//findByRoleId, rol bilgilerini almak için kullanılır.
                .orElseThrow(() -> new UsernameNotFoundException("Role not found"));//Role not found, rol bulunamadığında hata mesajıdır.
        return org.springframework.security.core.userdetails.User//Spring Security User nesnesi
                .withUsername(operator.getOperatorName())//getOperatorName, operator bilgilerini almak için kullanılır.
                .password(operator.getOperatorPassword())//getOperatorPassword, operator bilgilerini almak için kullanılır.
                .roles(role.getRoleType())//getRoleType, rol bilgilerini almak için kullanılır.
                .build();//build, operator bilgilerini almak için kullanılır.
    }
} 