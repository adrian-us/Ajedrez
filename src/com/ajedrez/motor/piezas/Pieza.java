package com.ajedrez.motor.piezas;

import com.ajedrez.motor.Alianza;
import com.ajedrez.motor.tablero.Movida;
import com.ajedrez.motor.tablero.Tablero;
import java.util.Collection;

public abstract class Pieza {
    protected final TipoPieza tipoPieza;
    protected final int posicionPieza;
    protected final Alianza alianzaPieza;
    protected final boolean isPrimeraMovida;
    private final int cacheHashCode;

    Pieza(final TipoPieza tipoPieza, final int posicionPieza, final Alianza alianzaPieza) {
        this.tipoPieza = tipoPieza;
        this.posicionPieza = posicionPieza;
        this.alianzaPieza = alianzaPieza;
        // todo
        this.isPrimeraMovida = false;
        this.cacheHashCode = computarHashCode();
    }

    private int computarHashCode() {
        int resultado = tipoPieza.hashCode();
        resultado = 31 * resultado + alianzaPieza.hashCode();
        resultado = 31 * resultado + posicionPieza;
        resultado = 31 * resultado + (isPrimeraMovida ? 1 : 0);
        return resultado;
    }

    @Override
    public boolean equals(final Object ajeno) {
        /*  Se hace un Override ya que cuando se usa este metodo lo que se desea verificar es la igualdad
            conceptual entre los objetos, es decir si se es el mismo tipo de objeto y con las mismas
            propiedades. Ya que cada vez que se usa este metodo es para verificar si es la misma pieza
            en terminos dentro del tablero de juego y no precisamente si apuntan al mismo objeto en
            memoria. Si son iguales referentemente, es decir se refieren al mismo objeto, entonces por
           definicion la equidad entre los objetos es verdadera (Diferencia entre Igualdad e Identidad) */
        if (this == ajeno) {
            return true;
        }
        if (!(ajeno instanceof Pieza)) {
            return false;
        }
        final Pieza piezaAjena = (Pieza) ajeno;
        return posicionPieza == piezaAjena.getPosicionPieza() && tipoPieza == piezaAjena.getTipoPieza() &&
               alianzaPieza == piezaAjena.getAlianzaPieza() && isPrimeraMovida == piezaAjena.isPrimeraMovida();
    }

    @Override
    public int hashCode() {
        return this.cacheHashCode;
    }

    public int getPosicionPieza() { return this.posicionPieza; }

    public Alianza getAlianzaPieza() {
        return this.alianzaPieza;
    }

    public boolean isPrimeraMovida() { return this.isPrimeraMovida; }

    public TipoPieza getTipoPieza() {
        return this.tipoPieza;
    }

    /* Metodo para calcular las movidas legales de una pieza */
    public abstract Collection<Movida> calcularMovidasLegales(final Tablero tablero);

    /* Recibir una movida y aplicarla a la pieza existente en la que se esta, y retornar
       una nueva pieza igual a la pieza anterior existente solo que con una posicion actualizada */
    public abstract Pieza moverPieza(Movida movida);

    public enum TipoPieza {
        PEON("P") {
            @Override
            public boolean isRey() {
                return false;
            }
            @Override
            public boolean isTorre() {
                return false;
            }
        },
        CABALLO("N") {
            @Override
            public boolean isRey() {
                return false;
            }
            @Override
            public boolean isTorre() {
                return false;
            }
        },
        ALFIL("B") {
            @Override
            public boolean isRey() {
                return false;
            }
            @Override
            public boolean isTorre() {
                return false;
            }
        },
        TORRE("R") {
            @Override
            public boolean isRey() {
                return false;
            }
            @Override
            public boolean isTorre() {
                return true;
            }
        },
        REINA("Q") {
            @Override
            public boolean isRey() {
                return false;
            }
            @Override
            public boolean isTorre() {
                return false;
            }
        },
        REY("K") {
            @Override
            public boolean isRey() {
                return true;
            }
            @Override
            public boolean isTorre() {
                return false;
            }
        };

        private String nombrePieza;

        TipoPieza(final String nombrePieza) {
            this.nombrePieza = nombrePieza;
        }

        @Override
        public String toString() {
            return this.nombrePieza;
        }

        public abstract boolean isRey();

        public abstract boolean isTorre();
    }
}
