package com.roofstacks.walletservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO implements Serializable {
    @ApiModelProperty(hidden = true)
    private long id;

    @NotBlank(message = "First Name is mandatory")
    @ApiModelProperty(example = "Koray")
    private String firstName;

    @NotBlank(message = "Second Name is mandatory")
    @ApiModelProperty(example = "Guney")
    private String secondName;

    @NotNull(message = "SSID is mandatory")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @ApiModelProperty(example = "1111111111")
    private long ssid;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email format is wrong!")
    @ApiModelProperty(example = "roof@stacks.com")
    private String email;

}
