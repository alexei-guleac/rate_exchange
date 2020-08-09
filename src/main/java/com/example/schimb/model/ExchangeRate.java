package com.example.schimb.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;


/**
 * Exchange rate model class
 */
@Entity(name = "exchange_rate")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@ApiModel(description = "Exchange rate model")
public class ExchangeRate {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "currency")
    @ApiModelProperty(notes = "Exchange currency")
    private Currency currency;

    @Column(name = "factor")
    @ApiModelProperty(notes = "Exchange factor")
    private Integer factor;

    @Column(name = "rate")
    @ApiModelProperty(notes = "Currency exchange rate")
    private Double rate;

    @Column(name = "updated_at")
    @ApiModelProperty(notes = "Currency exchange rate updated at date")
    private Date updatedAt;

}
