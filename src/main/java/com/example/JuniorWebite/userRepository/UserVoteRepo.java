package com.example.JuniorWebite.userRepository;

import com.example.JuniorWebite.Entity.User;
import com.example.JuniorWebite.Entity.UserVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserVoteRepo extends JpaRepository<UserVote,Integer> {

    @Query("SELECT u.userID FROM UserVote u WHERE u.responceIdVoted = :responceIdVoted")
    List<Integer> findUserIdsByResponceIdVoted(@Param("responceIdVoted") Integer responceIdVoted);
}
