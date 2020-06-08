package com.serviceorder.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ApiLog {
	@Id
    @GeneratedValue
    private Long id;
    private Date calledTime;
    private String data;
    private String errorMessage;
    private int retryNum;
}
