package com.example.JuniorWebite.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@AllArgsConstructor
@Data
@ToString
public class AllUserDTO {

    public int userID;
    public Date dateOfRegister;
    public String role;
    public String userName;
    public String userEmail;
   public String userPicture;


}
