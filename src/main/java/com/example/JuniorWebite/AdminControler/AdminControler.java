package com.example.JuniorWebite.AdminControler;


import com.example.JuniorWebite.AdminService.AdminService;
import com.example.JuniorWebite.Dto.AllUserDTO;
import com.example.JuniorWebite.Dto.userDTO;
import com.example.JuniorWebite.response.responseGenerale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/Admin")

public class AdminControler {


    @Autowired
    private AdminService service;

    @PostMapping("/count-by-month")
    public List<Object[]> getCountByMonth() {

        return service.getCountByMonth();
    }

    @PostMapping("/allUser")
    public ResponseEntity<List<AllUserDTO>> getAllUsers() {
        try {

            List<AllUserDTO> allUsers = service.getAllUsers();

            return ResponseEntity.ok(allUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/deleteUser")
    public responseGenerale deleteUser(@RequestBody userDTO userDTO) {

        return service.removeUser(userDTO.getUserID());
    }


}
