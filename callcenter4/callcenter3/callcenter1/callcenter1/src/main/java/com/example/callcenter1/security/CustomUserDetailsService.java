package com.example.callcenter1.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.callcenter1.model.operator.Operator;
import com.example.callcenter1.model.operator.Role;
import com.example.callcenter1.repository.operator.OperatorRepository;
import com.example.callcenter1.repository.operator.RoleRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {//UserDetailsService, operator bilgilerini almak için kullanılır.
    @Autowired
    private OperatorRepository operatorRepository;//OperatorRepository, operator bilgilerini almak için kullanılır.
    @Autowired
    private RoleRepository roleRepository;//RoleRepository, rol bilgilerini almak için kullanılır.

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Önce operator tablosunda ara
        Optional<Operator> operatorOpt = operatorRepository.findByOperatorEmail(email);
        if (operatorOpt.isPresent()) {
            Operator operator = operatorOpt.get();
            Role role = roleRepository.findByRoleId(operator.getRoleId())//findByRoleId, rol bilgilerini almak için kullanılır.
                    .orElseThrow(() -> new UsernameNotFoundException("Role not found"));//Role not found, rol bulunamadığında hata mesajıdır.
            return org.springframework.security.core.userdetails.User//Spring Security User nesnesi
                    .withUsername(operator.getOperatorEmail())//getOperatorEmail, operator bilgilerini almak için kullanılır.
                    .password(operator.getOperatorPassword())//getOperatorPassword, operator bilgilerini almak için kullanılır.
                    .roles(role.getRoleType())//getRoleType, rol bilgilerini almak için kullanılır.
                    .build();//build, operator bilgilerini almak için kullanılır.
        }

        // Sonra admin tablosunda ara
        // Assuming Admin model and repository are available
        // import com.example.callcenter1.model.admin.Admin;
        // import com.example.callcenter1.repository.admin.AdminRepository;
        // import org.springframework.beans.factory.annotation.Autowired;
        // import org.springframework.security.core.userdetails.UserDetails;
        // import org.springframework.security.core.userdetails.UserDetailsService;
        // import org.springframework.security.core.userdetails.UsernameNotFoundException;
        // import org.springframework.stereotype.Service;

        // import java.util.Optional;

        // @Service
        // public class CustomUserDetailsService implements UserDetailsService {//UserDetailsService, operator bilgilerini almak için kullanılır.
        //     @Autowired
        //     private OperatorRepository operatorRepository;//OperatorRepository, operator bilgilerini almak için kullanılır.
        //     @Autowired
        //     private RoleRepository roleRepository;//RoleRepository, rol bilgilerini almak için kullanılır.
        //     @Autowired
        //     private AdminRepository adminRepository; // Added AdminRepository

        //     @Override
        //     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //         // Önce operator tablosunda ara
        //         Optional<Operator> operatorOpt = operatorRepository.findByOperatorEmail(email);
        //         if (operatorOpt.isPresent()) {
        //             Operator operator = operatorOpt.get();
        //             Role role = roleRepository.findByRoleId(operator.getRoleId())//findByRoleId, rol bilgilerini almak için kullanılır.
        //                     .orElseThrow(() -> new UsernameNotFoundException("Role not found"));//Role not found, rol bulunamadığında hata mesajıdır.
        //             return org.springframework.security.core.userdetails.User//Spring Security User nesnesi
        //                     .withUsername(operator.getOperatorName())//getOperatorName, operator bilgilerini almak için kullanılır.
        //                     .password(operator.getOperatorPassword())//getOperatorPassword, operator bilgilerini almak için kullanılır.
        //                     .roles(role.getRoleType())//getRoleType, rol bilgilerini almak için kullanılır.
        //                     .build();//build, operator bilgilerini almak için kullanılır.
        //         }

        //         // Sonra admin tablosunda ara
        //         Optional<Admin> adminOpt = adminRepository.findByAdminEmail(email);
        //         if (adminOpt.isPresent()) {
        //             Admin admin = adminOpt.get();
        //             // ... admin için UserDetails dön ...
        //         }

        //         throw new UsernameNotFoundException("Kullanıcı bulunamadı: " + email);
        //     }
        // }

        throw new UsernameNotFoundException("Kullanıcı bulunamadı: " + email);
    }
} 