package com.example.filehandling.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CricketerDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("imageName")
    private String name;
}
