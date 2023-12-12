package com.example.filehandling.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CricketerResponseDto {

    @JsonProperty("images")
    private List<CricketerDto> cricketerDtoList;

}
