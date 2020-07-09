package com.serviceorder.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 07/07/2020 - 2:44 PM
 * @created_by thuynt
 * @since 07/07/2020
 */
@Entity
@Table(name = "type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int typeId;
    @Column(name = "name", length = 45)
    private String typeName;

    @CreationTimestamp
    @Column(name = "createdat", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updatedat")
    private Date updatedAt;

    @Column(name = "status")
    private int status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountType")
    private List<Account> accountList;


}
