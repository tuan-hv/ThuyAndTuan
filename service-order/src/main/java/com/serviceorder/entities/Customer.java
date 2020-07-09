package com.serviceorder.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 07/07/2020 - 2:36 PM
 * @created_by thuynt
 * @since 07/07/2020
 */
@Entity
@Table(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    @Column(name = "name", length = 45)
    private String customerName;
    @Column(name = "phone", length = 45)
    private String customerPhone;
    @CreationTimestamp
    @Column(name = "createdat", updatable = false)
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updatedat")
    private Date updatedAt;
    @Column(name = "status")
    private int status;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "customer")
    private Account account;


}
