package com.ajedrez.motor.jugador;

public enum EstadoDeMovida {
    HECHO {
        @Override
        boolean isHecho() {
            return true;
        }
    },
    MOVIDA_ILEGAL {
        @Override
        boolean isHecho() {
            return false;
        }
    },
    PONE_JUGADOR_EN_JAQUE {
        @Override
        boolean isHecho() {
            return false;
        }
    };

    abstract boolean isHecho();
}
