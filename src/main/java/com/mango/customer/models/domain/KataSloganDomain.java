package com.mango.customer.models.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author javiruizidneo
 */
@Data
@Builder

@AllArgsConstructor
@NoArgsConstructor
public class KataSloganDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private Long customerId;

    private String sloganContent;

}
