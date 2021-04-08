package com.ajedrez.motor;

import com.ajedrez.motor.tablero.Tablero;

public class JAjedrez {

    public static void main(String[] args) {

        Tablero tablero = Tablero.crearTableroEstandar();
        System.out.println(tablero);
    }
}
