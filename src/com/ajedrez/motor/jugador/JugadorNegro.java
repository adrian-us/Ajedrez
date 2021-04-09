package com.ajedrez.motor.jugador;

import com.ajedrez.motor.Alianza;
import com.ajedrez.motor.piezas.Pieza;
import com.ajedrez.motor.piezas.Torre;
import com.ajedrez.motor.tablero.Casilla;
import com.ajedrez.motor.tablero.Movida;
import com.ajedrez.motor.tablero.Movida.EnroqueCorto;
import com.ajedrez.motor.tablero.Tablero;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JugadorNegro extends Jugador {
    public JugadorNegro(final Tablero tablero,
                        final Collection<Movida> movidasLegalesBlanco,
                        final Collection<Movida> movidasLegalesNegro) {
        super(tablero, movidasLegalesNegro, movidasLegalesBlanco);
    }

    @Override
    public Collection<Pieza> getPiezasActivas() {
        return this.tablero.getPiezasNegras();
    }

    @Override
    public Alianza getAlianza() {
        return Alianza.NEGRO;
    }

    @Override
    public Jugador getOponente() {
        return this.tablero.getJugadorBlanco();
    }

    @Override
    protected Collection<Movida> calcularEnroques(final Collection<Movida> movidasLegalesJugador,
                                                  final Collection<Movida> movidasLegalesOponente) {
        final List<Movida> enroques = new ArrayList<>();

        if (this.reyJugador.isPrimeraMovida() && !this.isEnJaque()) {
            /* Enroque del Rey Negro */
            if (!this.tablero.getCasilla(5).estaOcupada() &&
                !this.tablero.getCasilla(6).estaOcupada()) {
                final Casilla casillaTorre = this.tablero.getCasilla(7);
                if (casillaTorre.estaOcupada() && casillaTorre.getPieza().isPrimeraMovida()) {
                    if (    Jugador.calcularAtaquesEnCasilla(5,movidasLegalesOponente).isEmpty() &&
                            Jugador.calcularAtaquesEnCasilla(6,movidasLegalesOponente).isEmpty() &&
                            casillaTorre.getPieza().getTipoPieza().isTorre()) {
                        enroques.add(new EnroqueCorto(this.tablero,this.reyJugador,6,(Torre)casillaTorre.getPieza(),casillaTorre.getCoordenadas(),5));
                    }
                }
            }

            if (!this.tablero.getCasilla(1).estaOcupada() &&
                !this.tablero.getCasilla(2).estaOcupada() &&
                !this.tablero.getCasilla(3).estaOcupada()) {
                final Casilla casillaTorre = this.tablero.getCasilla(0);

                if (casillaTorre.estaOcupada() && casillaTorre.getPieza().isPrimeraMovida()) {
                    if (    Jugador.calcularAtaquesEnCasilla(1,movidasLegalesOponente).isEmpty() &&
                            Jugador.calcularAtaquesEnCasilla(2,movidasLegalesOponente).isEmpty() &&
                            Jugador.calcularAtaquesEnCasilla(3,movidasLegalesOponente).isEmpty() &&
                            casillaTorre.getPieza().getTipoPieza().isTorre()) {
                        enroques.add(new EnroqueCorto(this.tablero,this.reyJugador,2,(Torre)casillaTorre.getPieza(),casillaTorre.getCoordenadas(),3));
                    }
                }
            }
        }
        return ImmutableList.copyOf(enroques);
    }
}
