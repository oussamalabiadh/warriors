package com.example.JuniorWebite.userService.userIMPL;

import com.example.JuniorWebite.Dto.ArticleDTO;
import com.example.JuniorWebite.Dto.loginDTO;
import com.example.JuniorWebite.Dto.restPasswordDTO;
import com.example.JuniorWebite.Dto.userDTO;
import com.example.JuniorWebite.Entity.Article;
import com.example.JuniorWebite.Entity.LoginStat;
import com.example.JuniorWebite.Entity.User;
import com.example.JuniorWebite.Entity.UserVote;
import com.example.JuniorWebite.response.VoteResponse;
import com.example.JuniorWebite.response.loginResponse;
import com.example.JuniorWebite.response.resetPasswordResponse;
import com.example.JuniorWebite.userRepository.ArticleRepo;
import com.example.JuniorWebite.userRepository.UserRepo;
import com.example.JuniorWebite.userRepository.UserVoteRepo;
import com.example.JuniorWebite.userRepository.loginStatRepo;
import com.example.JuniorWebite.userService.UserService;

import com.example.JuniorWebite.userService.UserToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;


import io.jsonwebtoken.security.Keys;


@Service
public class UserIMPL implements UserService, UserToken {

    /**
     * GENERATE TOKEN
     **/
    private final long expirationTime = 86400000; // 1 day in milliseconds


    @Override
    public Claims buildClaims(String userId, String userName, String email, String role) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("userName", userName);
        claims.put("email", email);
        claims.put("role", role);
        return claims;
    }

    @Override
    public String generateToken(String userId, String userName, String email, String role) {
        Claims claims = this.buildClaims(userId, userName, email, role);

        // Set the expiration time for the token
        long now = System.currentTimeMillis();
        Date expiration = new Date(now + this.expirationTime);

        // Build the JWT token
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(expiration)
                .signWith(Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }


    /**********************************************************/

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private loginStatRepo repository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public String addUser(userDTO userDTO) {

        if( userRepo.findByEmail(userDTO.getUserEmail()) == null)
        {
            User user1= new User(
                    userDTO.getUserID(),
                    userDTO.getDateOfRegister(),
                    "USER",
                    userDTO.getUserName(),
                    userDTO.getUserEmail(),
                    this.passwordEncoder.encode(userDTO.getUserPassword()),
                    userDTO.getUserPicture()
            );

            userRepo.save(user1);

            return user1.getUserName();
        }

        else return "Failed, this email is already used";

    }



    public void updateUserLoginStat(String username) {
        LoginStat loginStat = new LoginStat();
        loginStat.setUserName(username);
        repository.save(loginStat);
    }

    @Override
    public loginResponse loginUser(loginDTO loginDTO) {

            User user1 = userRepo.findByEmail(loginDTO.getUserEmail());

        if (user1 != null) {
                String password = loginDTO.getUserPassword();
                String encodedPassword = user1.getPassword();

                if (passwordEncoder.matches(password, encodedPassword)) {
                    Optional<User> user = userRepo.findOneByEmailAndPassword(loginDTO.getUserEmail(), encodedPassword);
                    if (user.isPresent()) {
                        String token = this.generateToken(String.valueOf(user1.getUserID()),user1.getUserName(), user1.getEmail(),user1.getRole());
                        updateUserLoginStat(user1.getUserName());
                        return new loginResponse("Login Success", true,token);
                    } else {
                        return new loginResponse("Login Failed", false,"USER NOT FOUND");
                    }
                } else {
                    return new loginResponse("password Not Match", false,"USER NOT FOUND");
                }
                }
            else {
                return new loginResponse("Email not exits", false,"USER NOT FOUND");
            }
        }


    @Override
    public resetPasswordResponse resetPassword(restPasswordDTO restPasswordDTO) {

        String email=restPasswordDTO.getUserEmail();
        User user = userRepo.findByEmail(restPasswordDTO.getUserEmail());

        if(user!=null)
        {
            user.setPassword(this.passwordEncoder.encode(restPasswordDTO.getUserPassword()));
            userRepo.save(user);
            return new resetPasswordResponse(email,"password updated",true);
        }
        else {
            return new resetPasswordResponse("No user found with this email","failed to update",false);

        }

    }


    @Autowired UserVoteRepo userVoteRepo;

    public VoteResponse HasVoted(int userID,int responseID)
    {

        List<Integer> userVotedID = userVoteRepo.findUserIdsByResponceIdVoted(responseID);
        String userName= userRepo.findUserBy(userID);
    if(userName==null)
    {
        return new VoteResponse("No user Found with this Id","No User so no vote",false,userID);
    }
    else
    {
        if(userVotedID.contains(userID))
        {

            return new VoteResponse(userName,"voted in this post",true,userID);
        }
        else
        {
            UserVote userVote= new UserVote();
            userVote.setUserName(userName);
            userVote.setUserID(userID);
            userVote.setResponceIdVoted(responseID);
            userVoteRepo.save(userVote);
            return new VoteResponse(userName,"no vote found",false,userID);
        }
    }

    }



    @Autowired
    private ArticleRepo articleRepository;


    @Override
    public String aadArticle(ArticleDTO articleDTO) {


        Article article=new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContentType(articleDTO.getContentType());
        article.setParagraph(articleDTO.getParagraph());
        article.setImage(articleDTO.getImage());

        articleRepository.save(article);

        return "Article submitted successfully";
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }


}



