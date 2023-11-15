package com.ecommerce;

import java.math.BigDecimal;

import com.ecommerce.utils.Utils;

public class Prueba {
    
    public static void main(String[] args) {
        System.out.println(Utils.formatTitle("Texto de        prueba."));

        System.out.println(redondear(new BigDecimal("1224.5")));
    }


    public static BigDecimal redondear(BigDecimal precio) {
        BigDecimal ultimoDigito = precio.remainder(new BigDecimal(10));

        if(ultimoDigito.compareTo(new BigDecimal("4")) >= 0)
            precio = precio.add(new BigDecimal(10).subtract(ultimoDigito));
        else
            precio = precio.subtract(ultimoDigito);
        return precio;
    }
}
