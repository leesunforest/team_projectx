package com.projectx.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "user")
@ToString
public class User {

    @Id
    @Column(name = "user_no")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userNo;

    @Column(name = "user_id")
    private String userId;
}
