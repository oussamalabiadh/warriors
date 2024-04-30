package com.example.JuniorWebite.userControle;

import com.example.JuniorWebite.Entity.Article;
import org.springframework.web.servlet.view.RedirectView;

import com.example.JuniorWebite.Entity.User;
import com.example.JuniorWebite.response.*;
import com.example.JuniorWebite.userRepository.UserRepo;
import com.example.JuniorWebite.userService.*;
import com.example.JuniorWebite.userService.userIMPL.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.JuniorWebite.Dto.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")

@AllArgsConstructor


public class userController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserIMPL userIMPLL;


    @Autowired
    private UserRepo userRepository;

    @Autowired
    private EmailSenderService emailSenderService;

/*****************************************/

    @PostMapping(path="/save")
    public responseGenerale saveUser(@RequestBody userDTO userDTO )  {

        if(userDTO.getConfirmPassword().equals(userDTO.getUserPassword()))
        {
            return  new responseGenerale(userIMPLL.addUser(userDTO));
        }
        else
        {
            return new responseGenerale("The password and the confirm password do not match. Please re-enter.");
        }



    }

/****************************************/

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody loginDTO loginDTO)
    {
        loginResponse loginResponse= userService.loginUser(loginDTO);
        return ResponseEntity.ok(loginResponse);

    }



    /**
     * Update password
     **/

    /**
     * Send email from maleekbejaoui@gmail.com
     **/
    @PostMapping("/reset-password")
    public responseGenerale resetPassword(@RequestBody getMailDTO getMailDTO)  {
        String email = getMailDTO.getEmail();
                User user = userRepository.findByEmail(email);

        if (user != null) {
            //String encodedEmail = URLEncoder.encode(user.getEmail(), StandardCharsets.UTF_8.toString());
            String link = "http://localhost:8080/api/v1/user/click-link?email=" + email;

            // String link = "http://localhost:8080/api/v1/user/click-link?email="+user.getEmail();
            String subject = "Reset Junior account";
            String Body = "Hello " + user.getUserName() + " click this link to reset your password  : \n" + link;
            emailSenderService.sendEmail(email, subject, Body);
            return new responseGenerale("Password send sent to : " + email);
        } else {
            return new responseGenerale("User with email " + email + " not found.");
        }
    }

    @PostMapping("/update-pasword")
    public ResponseEntity<?> updatePassword(@RequestBody restPasswordDTO restPasswordDTO)//
    {
        resetPasswordResponse resetPasswordResponse=userService.resetPassword(restPasswordDTO);
        return ResponseEntity.ok(resetPasswordResponse);

    }


    /**
     * when link in mail clicked
     **/
    @GetMapping("/click-link")
    public RedirectView linkClicked(@RequestParam String email) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return new RedirectView("http://localhost:63342/JuniorWebite/com/example/JuniorWebite/TryHTML/updatePassWord.html?email=" + email);
            // return "User mail :" + email;
        } else {

            return new RedirectView("https://www.google.com");

            // return "No User with under this email:" + email;
        }
    }




    @PostMapping("/HasVoted")
    public VoteResponse Vote(@RequestParam int userID,
                             @RequestParam int responseID)
    {
        return  userService.HasVoted(userID,responseID);
    }



    // Send email to admin  SendMailAdminDTO
    @PostMapping("/sentEmailToadmin")
    public responseGenerale submitContactForm(@RequestBody SendMailAdminDTO sendMailAdminDTO)   //email firstName lastName phoneNumber Message
    {
        String emailAdmin="malekbejaoui3000@gmail.com";

        String FromEmail=sendMailAdminDTO.getEmail();
        String Phone = sendMailAdminDTO.getPhoneNumber();
        String User= sendMailAdminDTO.getFirstName()+" "+sendMailAdminDTO.getLastName();
        String subject = "Urgent: Assistance Required for User Issue";
        String Body = sendMailAdminDTO.getMessage()+"\n"+"From : "+FromEmail+"\n"+"User : "+User+"\n"+"phone Number : "+Phone;

        emailSenderService.sendEmail(emailAdmin, subject, Body);
        return new responseGenerale("Contact Form submitted successfully");
    }


    @PostMapping("/postArticle")
    responseGenerale postAricle(@RequestBody ArticleDTO articleDTO)
    {
        return new responseGenerale(userService.aadArticle(articleDTO));
    }

    @PostMapping("/getAllArticle")
    List<Article> getAllArticles()
    {
        return userService.getAllArticles();
    }


}
