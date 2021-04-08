package com.ajedrez.motor;

import com.ajedrez.motor.jugador.Jugador;
import com.ajedrez.motor.jugador.JugadorBlanco;
import com.ajedrez.motor.jugador.JugadorNegro;

public enum Alianza {
    BLANCO {
        @Override
        public int getDireccion() {
            return -1;
        }

        @Override
        public boolean isNegra() {
            return false;
        }

        @Override
        public boolean isBlanca() {
            return true;
        }

        @Override
        public Jugador escogerJugador(final JugadorBlanco jugadorBlanco,
                                      final JugadorNegro jugadorNegro) {
            return jugadorBlanco;
        }
    },
    NEGRO {
        @Override
        public int getDireccion() {
            return 1;
        }

        @Override
        public boolean isNegra() {
            return true;
        }

        @Override
        public boolean isBlanca() {
            return false;
        }

        @Override
        public Jugador escogerJugador(final JugadorBlanco jugadorBlanco,
                                      final JugadorNegro jugadorNegro) {
            return jugadorNegro;
        }
    };

    public abstract int getDireccion();

    public abstract boolean isNegra();
    public abstract boolean isBlanca();

    public abstract Jugador escogerJugador(JugadorBlanco jugadorBlanco, JugadorNegro jugadorNegro);
}
