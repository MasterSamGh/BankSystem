package com.mysite.banking.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "ATM")
@Getter
@Setter
@ToString
public class ATM {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ATM_sequence")
    @SequenceGenerator(name = "ATM_sequence", sequenceName = "hibernate_ATM_seq", allocationSize = 1)
    private Long id;

    private int denomination;
    private int quantity;
}
