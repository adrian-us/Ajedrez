package com.ajedrez.motor.tablero;

import com.ajedrez.motor.piezas.Peon;
import com.ajedrez.motor.piezas.Pieza;
import com.ajedrez.motor.piezas.Torre;

import static com.ajedrez.motor.tablero.Tablero.*;

public abstract class Movida {

    final Tablero tablero;
    final Pieza piezaMovida;
    final int coordenadasDestino;

    public static final Movida MOVIDA_NULA = new MovidaNula();

    private Movida(final Tablero tablero,
           final Pieza piezaMovida,
           final int coordenadasDestino) {
        this.tablero = tablero;
        this.piezaMovida = piezaMovida;
        this.coordenadasDestino = coordenadasDestino;
    }

    @Override
    public int hashCode() {
        final int primo = 31;
        int resultado = 1;
        resultado = primo * resultado + this.coordenadasDestino;
        resultado = primo * resultado + this.piezaMovida.hashCode();
        return resultado;
    }

    @Override
    public boolean equals(final Object ajeno) {
        if (this == ajeno) {
            return true;
        }
        if (!(ajeno instanceof Movida)) {
            return false;
        }
        final Movida movidaAjena = (Movida) ajeno;
        return getCoordenadasDestino() == movidaAjena.getCoordenadasDestino() &&
               getPiezaMovida().equals(movidaAjena.getPiezaMovida());
    }

    public int getCoordenadasActuales() {
        return this.piezaMovida.getPosicionPieza();
    }

    public int getCoordenadasDestino() {
        return this.coordenadasDestino;
    }

    public Pieza getPiezaMovida() {
        return this.piezaMovida;
    }

    public boolean isAtaque() {
        return false;
    }

    public boolean isMovidaEnroque() {
        return false;
    }

    public Pieza getPiezaAtacada() {
        return null;
    }

    public Tablero ejecutar() {

        final Builder builder = new Builder();
        /* Todas las piezas excepto que la se va mover, se quedan igual */
        for (final Pieza pieza : this.tablero.getJugadorActual().getPiezasActivas()) {
            // todo hashcode y equals para las piezas
            if (!this.piezaMovida.equals(pieza)) {
                builder.setPieza(pieza);
            }
        }

        for (final Pieza pieza : this.tablero.getJugadorActual().getOponente().getPiezasActivas()) {
            builder.setPieza(pieza);
        }
        /* La pieza que va a ser movida se va a setear aqui. Se crea una nueva pieza del mismo tipo
        solo que cambia unicamente la posicion en el tablero */
        builder.setPieza(this.piezaMovida.moverPieza(this));
        builder.setSiguienteTurno(this.tablero.getJugadorActual().getOponente().getAlianza());
        return builder.build();
    }

    // Clase : Movida sin captura, simplemente movida / desplazamiento de pieza
    public static final class MovidaDesplazamiento extends Movida {

        public MovidaDesplazamiento(final Tablero tablero,
                                    final Pieza piezaMovida,
                                    final int coordenadasDestino) {
            super(tablero, piezaMovida, coordenadasDestino);
        }

    }

    // Clase : Movida de captura, movida de ataque
    public static class MovidaAtaque extends Movida {

        final Pieza piezaAtacada;

        public MovidaAtaque(final Tablero tablero,
                     final Pieza piezaMovida,
                     final int coordenadasDestino,
                     final Pieza piezaAtacada) {
            super(tablero, piezaMovida, coordenadasDestino);
            this.piezaAtacada = piezaAtacada;
        }

        @Override
        public int hashCode() {
            return this.piezaAtacada.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(final Object ajeno) {
            if (this == ajeno) {
                return true;
            }
            if (!(ajeno instanceof MovidaAtaque)) {
                return false;
            }
            final MovidaAtaque movidaAtaqueAjena = (MovidaAtaque) ajeno;
            return super.equals(movidaAtaqueAjena) && getPiezaAtacada().equals(movidaAtaqueAjena.getPiezaAtacada());
        }

        @Override
        public Tablero ejecutar() {
            return null;
        }

        @Override
        public boolean isAtaque() {
            return true;
        }

        @Override
        public Pieza getPiezaAtacada() {
            return this.piezaAtacada;
        }

    }

    public static final class MovidaPeon extends Movida {

        public MovidaPeon(final Tablero tablero,
                          final Pieza piezaMovida,
                          final int coordenadasDestino) {
            super(tablero, piezaMovida, coordenadasDestino);
        }
    }

    public static class MovidaPeonAtaque extends MovidaAtaque {

        public MovidaPeonAtaque(final Tablero tablero,
                                final Pieza piezaMovida,
                                final int coordenadasDestino,
                                final Pieza piezaAtacada) {
            super(tablero, piezaMovida, coordenadasDestino, piezaAtacada);
        }
    }

    public static final class MovidaPeonEnPassant extends MovidaPeonAtaque {

        public MovidaPeonEnPassant(final Tablero tablero,
                                   final Pieza piezaMovida,
                                   final int coordenadasDestino,
                                   final Pieza piezaAtacada) {
            super(tablero, piezaMovida, coordenadasDestino, piezaAtacada);
        }
    }

    public static final class MovidaSaltoPeon extends Movida {

        public MovidaSaltoPeon(final Tablero tablero,
                                final Pieza piezaMovida,
                                final int coordenadasDestino) {
            super(tablero, piezaMovida, coordenadasDestino);
        }

        @Override
        public Tablero ejecutar() {
            final Builder builder = new Builder();
            for (final Pieza pieza : this.tablero.getJugadorActual().getPiezasActivas()) {
                if (!this.piezaMovida.equals(pieza)) {
                    builder.setPieza(pieza);
                }
            }
            for (final Pieza pieza : this.tablero.getJugadorActual().getOponente().getPiezasActivas()) {
                builder.setPieza(pieza);
            }
            final Peon peonMovido = (Peon) this.piezaMovida.moverPieza(this);
            builder.setPieza(peonMovido);
            builder.setPeonEnPassant(peonMovido);
            builder.setSiguienteTurno(this.tablero.getJugadorActual().getOponente().getAlianza());
            return builder.build();
        }
    }

    static abstract class MovidaEnroque extends Movida {
        protected final Torre torreDeEnroque;
        protected final int torreDeEnroqueInicio;
        protected final int torreDeEnroqueDestino;

        public MovidaEnroque(final Tablero tablero,
                             final Pieza piezaMovida,
                             final int coordenadasDestino,
                             final Torre torreDeEnroque,
                             final int torreDeEnroqueInicio,
                             final int torreDeEnroqueDestino) {
            super(tablero, piezaMovida, coordenadasDestino);
            this.torreDeEnroque = torreDeEnroque;
            this.torreDeEnroqueInicio = torreDeEnroqueInicio;
            this.torreDeEnroqueDestino = torreDeEnroqueDestino;
        }

        public Torre getTorreDeEnroque() {
            return this.torreDeEnroque;
        }

        @Override
        public boolean isMovidaEnroque() {
            return true;
        }

        @Override
        public Tablero ejecutar() {

            final Builder builder = new Builder();
            for (final Pieza pieza : this.tablero.getJugadorActual().getPiezasActivas()) {
                if (!this.piezaMovida.equals(pieza) && !this.torreDeEnroque.equals(pieza)) {
                    builder.setPieza(pieza);
                }
            }
            for (final Pieza pieza : this.tablero.getJugadorActual().getOponente().getPiezasActivas()) {
                builder.setPieza(pieza);
            }
            builder.setPieza(this.piezaMovida.moverPieza(this));
            // todo  : pasar true/false a cada pieza si es primeraMovida o no
            builder.setPieza(new Torre( this.torreDeEnroqueDestino, this.torreDeEnroque.getAlianzaPieza()));
            builder.setSiguienteTurno(this.tablero.getJugadorActual().getOponente().getAlianza());
            return builder.build();
        }
    }

    public static final class EnroqueCorto extends MovidaEnroque {

        public EnroqueCorto(final Tablero tablero,
                            final Pieza piezaMovida,
                            final int coordenadasDestino,
                            final Torre torreDeEnroque,
                            final int torreDeEnroqueInicio,
                            final int torreDeEnroqueDestino) {
            super(tablero,piezaMovida,coordenadasDestino,torreDeEnroque,torreDeEnroqueInicio,torreDeEnroqueDestino);
        }

        @Override
        public String toString() {
            return "O-O";
        }
    }

    public static final class EnroqueLargo extends MovidaEnroque {

        public EnroqueLargo(final Tablero tablero,
                            final Pieza piezaMovida,
                            final int coordenadasDestino,
                            final Torre torreDeEnroque,
                            final int torreDeEnroqueInicio,
                            final int torreDeEnroqueDestino) {
            super(tablero,piezaMovida,coordenadasDestino,torreDeEnroque,torreDeEnroqueInicio,torreDeEnroqueDestino);
        }

        @Override
        public String toString() {
            return "O-O-O";
        }
    }

    public static final class MovidaNula extends Movida {
        public MovidaNula() {
            super(null, null, -1);
        }

        @Override
        public Tablero ejecutar() {
            throw new RuntimeException("No se puede ejecutar la movida nula");
        }
    }

    public static class CreadorMovidas {
        private CreadorMovidas() {
            throw new RuntimeException("No se inicializa");
        }

        public static Movida crearMovida(final Tablero tablero,
                                         final int coordenadasActuales,
                                         final int coordenadasDestino) {
            for (final Movida movida : tablero.getTodasMovidasLegales()) {
                if (movida.getCoordenadasActuales() == coordenadasActuales &&
                    movida.getCoordenadasDestino() == coordenadasDestino) {
                    return movida;
                }
            }
            return MOVIDA_NULA;
        }
    }
}
