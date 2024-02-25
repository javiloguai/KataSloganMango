package com.mango.customer.restapi.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KATA_CUSTOMER", uniqueConstraints = { @UniqueConstraint(columnNames = { "EMAIL" }) })
public class KataCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "CITY")
    private String city;

    @Column(name = "EMAIL")
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customerId")
    private List<KataSlogan> slogans;

}
