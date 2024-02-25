package com.mango.customer.restapi.persistence.entities;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "KATA_SLOGAN")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KataSlogan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @Column(name = "ID")
    private long id;

    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Column(name = "SLOGAN_CONTENT", nullable = false)
    private String sloganContent;

}
