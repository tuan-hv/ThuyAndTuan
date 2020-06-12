package com.payment.modals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbstractDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date createdAt;
    private Date updatedAt;
    private int status;
}
