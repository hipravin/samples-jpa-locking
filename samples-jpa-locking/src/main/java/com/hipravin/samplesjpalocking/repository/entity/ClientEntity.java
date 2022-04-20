package com.hipravin.samplesjpalocking.repository.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CLIENT")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientIdSeq")
    @SequenceGenerator(sequenceName = "CLIENT_CLIENT_ID_SEQ", allocationSize = 100, name = "clientIdSeq")
    @Column(name = "CLIENT_ID")
    private Long clientId;

    @Basic
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Basic
    @Column(name = "LAST_NAME")
    private String lastName;

    @Basic
    @Column(name = "EMAIL")
    private String email;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
