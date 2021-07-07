package com.roofstacks.walletservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer extends AbstractEntity {

    @NotBlank(message = "First Name is mandatory")
    private String firstName;

    @NotBlank(message = "Second Name is mandatory")
    private String secondName;

    @NotNull(message = "SSID is mandatory")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private long ssid;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email format is wrong!")
    private String email;

    @JsonManagedReference
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "customer")
    private List<Wallet> wallets = new ArrayList<>();

    /*
        All other necessary customer fields are omitted to simplify API
     */

}
