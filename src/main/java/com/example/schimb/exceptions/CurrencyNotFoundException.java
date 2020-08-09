package com.example.schimb.exceptions;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CurrencyNotFoundException extends Exception {
    public CurrencyNotFoundException(String s) {
        super(s);
    }
}
