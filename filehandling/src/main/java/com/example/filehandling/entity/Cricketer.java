package com.example.filehandling.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cricketer",schema = "myschema")
@Data
public class Cricketer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "image")
    private byte[] imageData;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;




}
