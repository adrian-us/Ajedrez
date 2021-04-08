package com.ajedrez.motor.jugador;

import com.ajedrez.motor.tablero.Movida;
import com.ajedrez.motor.tablero.Tablero;

public class TransicionMovida {
    /* Todo de lo que se tiene que tener un control cada vez que se efectua una movida */
    private final Tablero tableroEnTransicion;
    private final Movida movida;
    private final EstadoDeMovida estadoDeMovida;

    public TransicionMovida(final Tablero tableroEnTransicion,
                            final Movida movida,
                            final EstadoDeMovida estadoDeMovida) {
        this.tableroEnTransicion = tableroEnTransicion;
        this.movida = movida;
        this.estadoDeMovida = estadoDeMovida;
    }

    public EstadoDeMovida getEstadoDeMovida() {
        return this.estadoDeMovida;
    }
}
