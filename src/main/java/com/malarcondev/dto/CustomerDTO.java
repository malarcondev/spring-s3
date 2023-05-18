package com.malarcondev.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String profileImageId;
}
