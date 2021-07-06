package com.roofstacks.walletservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String secondName;
    private long ssid;
    private String email;

    @JsonManagedReference
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "customer")
    private List<Wallet> wallets = new ArrayList<>();

    /*
        All other necessary customer fields are omitted to simplify API
     */

}
