package com.ajedrez.motor.tablero;

import java.util.*;
import com.ajedrez.motor.Alianza;
import com.ajedrez.motor.jugador.Jugador;
import com.ajedrez.motor.jugador.JugadorBlanco;
import com.ajedrez.motor.jugador.JugadorNegro;
import com.ajedrez.motor.piezas.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class Tablero {

    private final List<Casilla> tableroJuego;
    private final Collection<Pieza> piezasBlancas;
    private final Collection<Pieza> piezasNegras;

    private final JugadorBlanco jugadorBlanco;
    private final JugadorNegro jugadorNegro;
    private final Jugador jugadorActual;

    private Tablero(final Builder builder) {
        this.tableroJuego = crearTableroJuego(builder);
        this.piezasBlancas = calcularPiezasActivas(this.tableroJuego, Alianza.BLANCO);
        this.piezasNegras = calcularPiezasActivas(this.tableroJuego, Alianza.NEGRO);

        final Collection<Movida> movidasLegalesBlanco = calcularMovidasLegales(this.piezasBlancas);
        final Collection<Movida> movidasLegalesNegro = calcularMovidasLegales(this.piezasNegras);

        this.jugadorBlanco = new JugadorBlanco(this, movidasLegalesBlanco, movidasLegalesNegro);
        this.jugadorNegro = new JugadorNegro(this, movidasLegalesBlanco, movidasLegalesNegro);
        this.jugadorActual = builder.siguienteTurno.escogerJugador(this.jugadorBlanco, this.jugadorNegro);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < UtilidadesTablero.CANT_CASILLAS; i++) {
            final String casillaEnString = this.tableroJuego.get(i).toString();
            builder.append(String.format("%3s", casillaEnString));
            if ( (i + 1) % UtilidadesTablero.CANT_CASILLAS_POR_FILA == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public Jugador getJugadorBlanco() { return this.jugadorBlanco; }
    public Jugador getJugadorNegro() { return this.jugadorNegro; }
    public Jugador getJugadorActual() { return this.jugadorActual; }
    public Collection<Pieza> getPiezasBlancas() { return this.piezasBlancas; }
    public Collection<Pieza> getPiezasNegras() { return this.piezasNegras; }
    public Casilla getCasilla(final int coordenadaCasilla) {
        return tableroJuego.get(coordenadaCasilla);
    }

    private Collection<Movida> calcularMovidasLegales(Collection<Pieza> piezas) {
        final List<Movida> movidasLegales = new ArrayList<>();
        for (final Pieza pieza : piezas) {
            movidasLegales.addAll(pieza.calcularMovidasLegales(this));
        }
        return ImmutableList.copyOf(movidasLegales);
    }

    private static Collection<Pieza> calcularPiezasActivas(final List<Casilla> tableroJuego, final Alianza alianza) {
        final List<Pieza> piezasActivas = new ArrayList<>();

        for (final Casilla casilla : tableroJuego) {
            if (casilla.estaOcupada()) {
                final Pieza pieza = casilla.getPieza();
                if (pieza.getAlianzaPieza() == alianza) {
                    piezasActivas.add(pieza);
                }
            }
        }
        return ImmutableList.copyOf(piezasActivas);
    }

    private static List<Casilla> crearTableroJuego(final Builder builder) {
        final Casilla[] casillas = new Casilla[UtilidadesTablero.CANT_CASILLAS];
        for (int i = 0; i < UtilidadesTablero.CANT_CASILLAS; i++) {
            casillas[i] = Casilla.crearCasilla(i, builder.configuracionTablero.get(i));
        }
        return ImmutableList.copyOf(casillas);
    }

    public static Tablero crearTableroEstandar() {
        final Builder builder = new Builder();
        /* Configuracion de las Negras */
        builder.setPieza(new Torre(0, Alianza.NEGRO));
        builder.setPieza(new Caballo(1, Alianza.NEGRO));
        builder.setPieza(new Alfil(2, Alianza.NEGRO));
        builder.setPieza(new Reina(3, Alianza.NEGRO));
        builder.setPieza(new Rey(4, Alianza.NEGRO));
        builder.setPieza(new Alfil(5, Alianza.NEGRO));
        builder.setPieza(new Caballo(6, Alianza.NEGRO));
        builder.setPieza(new Torre(7, Alianza.NEGRO));
        builder.setPieza(new Peon(8, Alianza.NEGRO));
        builder.setPieza(new Peon(9, Alianza.NEGRO));
        builder.setPieza(new Peon(10, Alianza.NEGRO));
        builder.setPieza(new Peon(11, Alianza.NEGRO));
        builder.setPieza(new Peon(12, Alianza.NEGRO));
        builder.setPieza(new Peon(13, Alianza.NEGRO));
        builder.setPieza(new Peon(14, Alianza.NEGRO));
        builder.setPieza(new Peon(15, Alianza.NEGRO));
        /* Configuracion de las Blancas */
        builder.setPieza(new Peon(48, Alianza.BLANCO));
        builder.setPieza(new Peon(49, Alianza.BLANCO));
        builder.setPieza(new Peon(50, Alianza.BLANCO));
        builder.setPieza(new Peon(51, Alianza.BLANCO));
        builder.setPieza(new Peon(52, Alianza.BLANCO));
        builder.setPieza(new Peon(53, Alianza.BLANCO));
        builder.setPieza(new Peon(54, Alianza.BLANCO));
        builder.setPieza(new Peon(55, Alianza.BLANCO));
        builder.setPieza(new Torre(56, Alianza.BLANCO));
        builder.setPieza(new Caballo(57, Alianza.BLANCO));
        builder.setPieza(new Alfil(58, Alianza.BLANCO));
        builder.setPieza(new Reina(59, Alianza.BLANCO));
        builder.setPieza(new Rey(60, Alianza.BLANCO));
        builder.setPieza(new Alfil(61, Alianza.BLANCO));
        builder.setPieza(new Caballo(62, Alianza.BLANCO));
        builder.setPieza(new Torre(63, Alianza.BLANCO));
        /* Turno siguiente de las blancas */
        builder.setSiguienteTurno(Alianza.BLANCO);

        return builder.build();
    }

    public Iterable<Movida> getTodasMovidasLegales() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.jugadorBlanco.getMovidasLegales(),
                this.jugadorNegro.getMovidasLegales()));
    }

    public static class Builder {

        Map<Integer, Pieza> configuracionTablero;
        Alianza siguienteTurno;
        Peon peonEnPassant;

        public Builder() {
            this.configuracionTablero = new HashMap<>();
        }

        public Builder setPieza(final Pieza pieza) {
            this.configuracionTablero.put(pieza.getPosicionPieza(), pieza);
            return this;
        }

        public Builder setSiguienteTurno(final Alianza siguienteTurno) {
            this.siguienteTurno = siguienteTurno;
            return this;
        }

        public Tablero build() {
            return new Tablero(this);
        }

        public void setPeonEnPassant(Peon peonEnPassant) {
            this.peonEnPassant = peonEnPassant;
        }
    }
}
