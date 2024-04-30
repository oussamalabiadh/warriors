package com.example.JuniorWebite.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@Entity
@Table(name="user-info")
public class User {

    @Id
    @Column(name = "user-id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userID;

    @Column(name = "date-Of-Register")
    @CreationTimestamp
    private Date dateOfRegister;

    @Column(name = "role")
    private String role;

    @Column(name = "user-name")
    private String userName;

    @Column(name = "email")
    @NaturalId(mutable = true)
    private String email;


    @Column(name = "password")
    private String password;

    @Lob
    @Column(name = "User-Picture")
    private String UserPicture;


}
