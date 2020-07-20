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
 * @created_at 07/07/2020 - 2:42 PM
 * @created_by thuynt
 * @since 07/07/2020
 */
@Entity
@Table(name = "account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;
    @Column(name = "accountnumber", length = 45)
    private String accountNumber;
    @Column(name = "accountbalance", length = 45)
    private Long accountBalance;
    @CreationTimestamp
    @Column(name = "createdat", updatable = false)
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updatedat")
    private Date updatedAt;
    @Column(name = "status")
    private int status;

    @ManyToOne()
    @JoinColumn(name = "type_id")
    private AccountType accountType;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
