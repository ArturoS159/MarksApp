package com.arczi.marksapp.rest.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Lab {
    private String dateOfLab;//TODO redactor to date
    private boolean presence;
    private Integer points;
}
