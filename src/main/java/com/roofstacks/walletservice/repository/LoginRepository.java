package com.roofstacks.walletservice.repository;

import com.roofstacks.walletservice.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findByUsername(String username);
}
