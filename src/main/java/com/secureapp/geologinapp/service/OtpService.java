package com.secureapp.geologinapp.service;

import com.secureapp.geologinapp.model.OTP;
import com.secureapp.geologinapp.repository.OTPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {
    @Autowired private OTPRepository otpRepo;

    public String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    public void saveOtp(String email, String otp) {
        OTP entity = new OTP();
        entity.setEmail(email);
        entity.setOtp(otp);
        entity.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpRepo.deleteByEmail(email);
        otpRepo.save(entity);
    }

    public boolean verifyOtp(String email, String inputOtp) {
        Optional<OTP> record = otpRepo.findByEmail(email);
        return record.filter(o -> o.getOtp().equals(inputOtp) && o.getExpiryTime().isAfter(LocalDateTime.now()))
                .map(o -> { otpRepo.deleteByEmail(email); return true; }).orElse(false);
    }
}
