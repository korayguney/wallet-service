package com.roofstacks.walletservice.repository;

import com.roofstacks.walletservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT CASE WHEN COUNT(c)>0 THEN TRUE ELSE FALSE END FROM Customer c WHERE c.ssid = ?1")
    boolean selectExistsSsid(Long ssid);

}
