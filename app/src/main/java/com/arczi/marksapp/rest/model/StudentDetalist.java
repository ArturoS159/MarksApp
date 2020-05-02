package com.arczi.marksapp.rest.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentDetalist {

    private String index;
    private String group;
    private List<Lab> labs;
}
