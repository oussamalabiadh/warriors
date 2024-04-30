package com.example.JuniorWebite.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NonNull

@Entity
@Table(name="user-voted")
public class UserVote {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    @Column(name = "user-Name")
    private String userName;

    @Column(name = "user-id")
    private int  userID;

    @Column(name = "responce-id-voted")
    private int responceIdVoted;





}
