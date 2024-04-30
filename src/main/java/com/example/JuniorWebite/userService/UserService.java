package com.example.JuniorWebite.userService;
import com.example.JuniorWebite.Dto.ArticleDTO;
import com.example.JuniorWebite.Dto.loginDTO;
import com.example.JuniorWebite.Dto.restPasswordDTO;
import com.example.JuniorWebite.Dto.userDTO;
import com.example.JuniorWebite.Entity.Article;
import com.example.JuniorWebite.response.VoteResponse;
import com.example.JuniorWebite.response.loginResponse;
import com.example.JuniorWebite.response.resetPasswordResponse;

import java.util.List;


public interface UserService {

    String addUser(userDTO userDTO);

    loginResponse loginUser(loginDTO loginDTO);
    resetPasswordResponse resetPassword(restPasswordDTO restPasswordDTO);

     VoteResponse HasVoted(int responseID , int userID);

     String aadArticle(ArticleDTO articleDTO);

    List<Article> getAllArticles();




}
