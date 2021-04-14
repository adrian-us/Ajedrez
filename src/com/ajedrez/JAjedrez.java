package com.ajedrez;

import com.ajedrez.interfaz.Tabla;
import com.ajedrez.motor.tablero.Tablero;

public class JAjedrez {

    public static void main(String[] args) {

        Tablero tablero = Tablero.crearTableroEstandar();
        System.out.println(tablero);

        Tabla tabla = new Tabla();
    }
}
