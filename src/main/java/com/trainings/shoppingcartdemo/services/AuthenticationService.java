package com.trainings.shoppingcartdemo.services;


import com.trainings.shoppingcartdemo.dto.LoginDto;
import com.trainings.shoppingcartdemo.dto.RegisterDto;
import com.trainings.shoppingcartdemo.models.Account;
import com.trainings.shoppingcartdemo.repositories.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class AuthenticationService {
    private final AccountRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            AccountRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            AccountService accountService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
    }

    public Account signup(RegisterDto input) {
        // Kiểm tra xem người dùng đã tồn tại hay chưa
        if (userRepository.findByUsername(input.getUsername()) != null) {
            return null;
        }
        return accountService.createAccount(input.getUsername(), input.getPassword());
    }

    public Account authenticate(LoginDto input) {
        try {
            log.debug("Authenticating user: " + input.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword())
            );
            // Kiểm tra nếu xác thực thành công
            if (authentication.isAuthenticated()) {
                return userRepository.findByUsername(input.getUsername());
            } else {
                throw new RuntimeException("Unknown authentication error");
            }
        } catch (BadCredentialsException e) {
            // Ném ra lỗi thông tin đăng nhập sai
            throw new RuntimeException("Invalid username or password", e);
        } catch (DisabledException e) {
            // Ném ra lỗi tài khoản bị vô hiệu hóa
            throw new RuntimeException("Account is disabled", e);
        } catch (LockedException e) {
            // Ném ra lỗi tài khoản bị khóa
            throw new RuntimeException("Account is locked", e);
        } catch (AuthenticationException e) {
            // Bắt tất cả các loại lỗi xác thực khác
            throw new RuntimeException("Authentication failed", e);
        } catch (Exception e) {
            // Bắt tất cả các loại lỗi khác
            throw new RuntimeException("Unknown error", e);
        }
    }
}