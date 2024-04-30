package com.example.JuniorWebite.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import java.sql.Date;


@AllArgsConstructor
@Data
@ToString
public class ArticleDTO {

    private Long id;

    private String title;

    private String paragraph;

    private String ContentType;

    private Date date;

    private String image;
}
