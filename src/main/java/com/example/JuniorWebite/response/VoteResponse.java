package com.example.JuniorWebite.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoteResponse {
    private String userName;
    private  String description;
    private Boolean hasVoted;
    private  int userId;


}
