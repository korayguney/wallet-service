package com.roofstacks.walletservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO implements Serializable {
    @ApiModelProperty(hidden = true)
    private long id;
    private String firstName;
    private String secondName;
    private long ssid;
    private String email;

}
