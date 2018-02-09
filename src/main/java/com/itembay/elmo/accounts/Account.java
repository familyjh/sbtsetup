package com.itembay.elmo.accounts;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String userName;

    private String password;

    private String email;

    private String fullName;

    @CreatedDate
    //private LocalDateTime joined;
    private Date joined;

    @LastModifiedDate
    //private LocalDateTime updated;
    private Date updated;
}
