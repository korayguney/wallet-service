package com.roofstacks.walletservice.service;

import com.roofstacks.walletservice.dto.LoginDTO;
import com.roofstacks.walletservice.model.Login;
import com.roofstacks.walletservice.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public void save(LoginDTO loginDTO) {
        mapToDTO(this.loginRepository.save(
                new Login(
                    loginDTO.getUsername(),
                    loginDTO.getPassword()
                )
        ));
    }

    public static LoginDTO mapToDTO(Login login) {
        if (login != null) {
            return new LoginDTO(
                    login.getUsername(),
                    login.getPassword()
            );
        }
        return null;
    }
}
