package com.example.JuniorWebite.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@Entity
@Table(name="Article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "title")
    private String title;

    @Column(name = "paragraph")
    private String paragraph;

    @Column(name = "ContentType")
    private String ContentType;

    @Column(name = "date-Of-Creation")
    @CreationTimestamp
    private Date date;


    @Column(name = "image")
    @Lob
    private String image;



}