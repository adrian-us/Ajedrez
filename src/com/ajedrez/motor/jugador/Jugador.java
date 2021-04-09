package com.ajedrez.motor.jugador;

import com.ajedrez.motor.Alianza;
import com.ajedrez.motor.piezas.Pieza;
import com.ajedrez.motor.piezas.Rey;
import com.ajedrez.motor.tablero.Movida;
import com.ajedrez.motor.tablero.Tablero;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Jugador {

    protected final Tablero tablero;
    protected final Rey reyJugador;
    protected final Collection<Movida> movidasLegales;
    private final boolean isEnJaque;

    Jugador(final Tablero tablero,
            final Collection<Movida> movidasLegales,
            final Collection<Movida> movidasOponente) {
        this.tablero = tablero;
        this.reyJugador = establecerRey();
        this.movidasLegales = ImmutableList.copyOf(Iterables.concat(movidasLegales, calcularEnroques(movidasLegales, movidasOponente)));
        this.isEnJaque = !Jugador.calcularAtaquesEnCasilla(this.reyJugador.getPosicionPieza(),movidasOponente).isEmpty();
    }

    public Rey getReyJugador() {
        return this.reyJugador;
    }

    public Collection<Movida> getMovidasLegales() {
        return this.movidasLegales;
    }

    protected static Collection<Movida> calcularAtaquesEnCasilla(int posicionPieza, Collection<Movida> movidas) {
        final List<Movida> movidasAtaque = new ArrayList<>();
        for ( final Movida movida : movidas ) {
            if (posicionPieza == movida.getCoordenadasDestino()) {
                movidasAtaque.add(movida);
            }
        }
        return ImmutableList.copyOf(movidasAtaque);
    }

    private Rey establecerRey() {
        for (final Pieza pieza : getPiezasActivas() ) {
            if (pieza.getTipoPieza().isRey()) {
                return (Rey) pieza;
            }
        }
        throw new RuntimeException("No hay Rey, no es un tablero valido !");
    }

    public boolean isMovidaLegal(final Movida movida) {
        return this.movidasLegales.contains(movida);
    }

    public boolean isEnJaque() {
        return this.isEnJaque;
    }

    public boolean isEnJaqueMate() {
        return this.isEnJaque && !tieneMovidasDeEscape();
    }

    public boolean isEnStalemate() {
        return !this.isEnJaque && !tieneMovidasDeEscape();
    }

    protected boolean tieneMovidasDeEscape() {
        for (final Movida movida : this.movidasLegales) {
            final TransicionMovida transicion = crearMovida(movida);
            if (transicion.getEstadoDeMovida().isHecho()) {
                return true;
            }
        }
        return false;
    }

    // todo

    public boolean isEnrocado() {
        return false;
    }

    public TransicionMovida crearMovida(final Movida movida) {
        if(!isMovidaLegal(movida)) {
            return new TransicionMovida(this.tablero, movida, EstadoDeMovida.MOVIDA_ILEGAL);
        }

        final Tablero tableroEnTransicion = movida.ejecutar();

        final Collection<Movida> ataquesRey =
                Jugador.calcularAtaquesEnCasilla(
                        tableroEnTransicion.getJugadorActual().getOponente().getReyJugador().getPosicionPieza(),
                        tableroEnTransicion.getJugadorActual().getMovidasLegales());

        if (!ataquesRey.isEmpty()) {
            return new TransicionMovida(this.tablero, movida, EstadoDeMovida.PONE_JUGADOR_EN_JAQUE);
        }

        return new TransicionMovida(tableroEnTransicion, movida, EstadoDeMovida.HECHO);
    }

    public abstract Collection<Pieza> getPiezasActivas();
    public abstract Alianza getAlianza();
    public abstract Jugador getOponente();
    protected abstract Collection<Movida> calcularEnroques(Collection<Movida> movidasLegalesJugador,
                                                               Collection<Movida> movidasLegalesOponente);
}
