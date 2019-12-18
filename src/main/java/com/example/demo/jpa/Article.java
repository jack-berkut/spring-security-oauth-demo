package com.example.demo.jpa;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "article")
@Data
public class Article {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String entry;
    private LocalDate date;
}
