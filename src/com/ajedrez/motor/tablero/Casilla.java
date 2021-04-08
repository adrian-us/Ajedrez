package com.ajedrez.motor.tablero;

import com.ajedrez.motor.piezas.Pieza;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

public abstract class Casilla {
    // protected : accesible solamente para sub-clases
    // final : puede ser establecida solamente una vez, en el momento de construccion
    protected final int coordenada;

    private static final Map<Integer, CasillaVacia> CACHE_CASILLAS_VACIAS = crearCasillasVacias();

    private static Map<Integer, CasillaVacia> crearCasillasVacias() {
        final Map<Integer, CasillaVacia> casillaVaciaMap = new HashMap<>();
        for (int i = 0; i < UtilidadesTablero.CANT_CASILLAS; i++) {
            casillaVaciaMap.put(i, new CasillaVacia(i));
        }
        return ImmutableMap.copyOf(casillaVaciaMap);
    }

    /*  ImmutableMap es parte de la biblioteca 'Guava'(Google). Esto prohibe que una vez construida
        la casilla sea editada o modificada de alguna forma */

    public static Casilla crearCasilla(final int coordenada, final Pieza pieza) {
        return pieza != null ? new CasillaOcupada(coordenada,pieza) : CACHE_CASILLAS_VACIAS.get(coordenada);
    }

    private Casilla(final int coordenada) {
        this.coordenada = coordenada;
    }

    public abstract boolean estaOcupada();

    public abstract Pieza getPieza();

    //  CASILLA VACIA
    public static final class CasillaVacia extends Casilla {

        private CasillaVacia(final int coordenada) {
            super(coordenada);
        }

        @Override
        public String toString() {
            return "-";
        }

        @Override
        public boolean estaOcupada() {
            return false;
        }

        @Override
        public Pieza getPieza() {
            return null;
        }
    }

    //  CASILLA OCUPADA
    public static final class CasillaOcupada extends Casilla {
        // private : no hay forma de referenciar el atributo fuera de la clase
        private final Pieza piezaEnCasilla;

        private CasillaOcupada(int coordenada, final Pieza piezaEnCasilla) {
            super(coordenada);
            this.piezaEnCasilla = piezaEnCasilla;
        }

        @Override
        public String toString() {
            return getPieza().getAlianzaPieza().isNegra() ? getPieza().toString().toLowerCase() :
                    getPieza().toString();
        }

        @Override
        public boolean estaOcupada() {
            return true;
        }

        @Override
        public Pieza getPieza() {
            return piezaEnCasilla;
        }
    }
}
