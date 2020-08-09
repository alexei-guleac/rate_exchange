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
 * Cash model class
 */
@Entity(name = "cash")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@ApiModel(description = "Cash model model")
public class Cash {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    @ApiModelProperty(notes = "Username")
    private Currency username;

    @Column(name = "currency")
    @ApiModelProperty(notes = "Exchange currency")
    private Currency currency;

    @Column(name = "amount")
    @ApiModelProperty(notes = "Amount")
    private Double amount;

    @Column(name = "updated_at")
    @ApiModelProperty(notes = "Last operation date")
    private Date updatedAt;

}
