package com.example.entity;

import javax.persistence.Entity;

@Entity
public interface Member1Projection {

    //get + 변수명()
    String getId();

    String getName();

    int getAge();
    
}
