package com.arczi.marksapp.rest.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Student {

    private String index;
    private Double mark;
    private String group;
    private Integer lecturePoints;
    private Integer homeworkPoints;
    private Integer presenceCounter;
    private Integer absenceCounter;
    private Integer allPoints;

}
