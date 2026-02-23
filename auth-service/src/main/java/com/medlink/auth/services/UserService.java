package com.medlink.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medlink.auth.models.JwtRequest;
import com.medlink.auth.models.LoginRequest;
import com.medlink.auth.models.UserModel;
import com.medlink.auth.repository.UserRepository;
import com.medlink.auth.utils.JwtUtils;
import com.medlink.auth.utils.EmailUtil;
import com.medlink.auth.utils.OtpUtil;

import jakarta.mail.MessagingException;
import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private OtpUtil otpUtil;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    JwtUtils jwt;

    public boolean authenticate(String token, JwtRequest user) {
        return this.jwt.validateToken(token, user);
    }

    public String createToken(JwtRequest user) {
        return this.jwt.generateToken(user);
    }

    public String login(LoginRequest log) throws Exception {
        try {
            UserModel user = userRepository.findByEmail(log.getEmail());
            if (user == null) {
                throw new Exception("User not found");
            }
            if (!user.getPassword().equals(log.getPassword())) {
                throw new Exception("Wrong password");
            }
            JwtRequest jwtRequest = new JwtRequest(log.getEmail(), user.getId());
            return createToken(jwtRequest);
        } catch (Exception e) {
            throw new Exception("Login failed: " + e.getMessage());
        }
    }

    public String register(UserModel user) throws Exception {
        UserModel check = userRepository.findByEmail(user.getEmail());
        if (check != null) {
            throw new Exception("User Already Exists");
        }
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(user.getEmail(), otp);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to send otp please try again: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Email sending failed: " + e.getMessage());
        }
        user.setOtp(otp);
        user.setActive(false);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "User registration successful";
    }

    public String verifyAccount(String email, String otp) throws Exception {
        UserModel user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found with this email: " + email);
        }
        try {
            if (user.getOtp().equals(otp) && user.getOtpGeneratedTime().plusMinutes(5).isAfter(LocalDateTime.now())) {
                user.setActive(true);
                UserModel s = userRepository.save(user);
                JwtRequest jwtRequest = new JwtRequest(s.getEmail(), s.getId());
                String JWTTOKEN = jwt.generateToken(jwtRequest);
                return JWTTOKEN;
            }
        } catch (Exception e) {
            throw new Exception("Error SigningUp: " + e.getMessage());
        }
        return null;
    }

    public String regenerateOtp(String email) {
        UserModel user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found with this email: " + email);
        }
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "Email sent... please verify account within 1 minute";
    }
}
