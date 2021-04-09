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

public class JugadorBlanco extends Jugador {
    public JugadorBlanco(final Tablero tablero,
                         final Collection<Movida> movidasLegalesBlanco,
                         final Collection<Movida> movidasLegalesNegro) {
        super(tablero, movidasLegalesBlanco, movidasLegalesNegro);
    }

    @Override
    public Collection<Pieza> getPiezasActivas() {
        return this.tablero.getPiezasBlancas();
    }

    @Override
    public Alianza getAlianza() {
        return Alianza.BLANCO;
    }

    @Override
    public Jugador getOponente() {
        return this.tablero.getJugadorNegro();
    }

    @Override
    protected Collection<Movida> calcularEnroques(final Collection<Movida> movidasLegalesJugador,
                                                  final Collection<Movida> movidasLegalesOponente) {
        final List<Movida> enroques = new ArrayList<>();

        /* El enroque es permisible si y solo si se cumplen todas las siguiente condiciones (Schiller 2001:19)
        *   1. El rey nunca se movio
        *   2. La torra a usar en el enroque nunca fue movida
        *   3. El rey no esta en jaque
        *   4. Ninguna de las casillas por las que el rey pasara o quedara esta bajo ataque
        *   5. Las casillas entre el rey y la torre esten desocupadas
        *   6. El rey no termina en jaque (valido para cualquier movimiento legal) */
        if (this.reyJugador.isPrimeraMovida() && !this.isEnJaque()) {
            /* Enroque del Rey Blanco */
            if (!this.tablero.getCasilla(61).estaOcupada() &&
                !this.tablero.getCasilla(62).estaOcupada()) {
                final Casilla casillaTorre = this.tablero.getCasilla(63);

                if (casillaTorre.estaOcupada() && casillaTorre.getPieza().isPrimeraMovida()) {

                    /* Si las casillas por las que va a pasar el rey no estan bajo ataque */
                    if (Jugador.calcularAtaquesEnCasilla(61, movidasLegalesOponente).isEmpty() &&
                        Jugador.calcularAtaquesEnCasilla(62, movidasLegalesOponente).isEmpty() &&
                        /* Si la pieza en la casilla de la Torre (63/56) sea en realidad una Torre */
                        casillaTorre.getPieza().getTipoPieza().isTorre()) {
                        enroques.add(new EnroqueCorto(this.tablero,
                                                             this.reyJugador,
                                             62,
                                                             (Torre)casillaTorre.getPieza(),
                                                             casillaTorre.getCoordenadas(),
                                          61));
                    }
                }
            }

            if (    !this.tablero.getCasilla(59).estaOcupada() &&
                    !this.tablero.getCasilla(58).estaOcupada() &&
                    !this.tablero.getCasilla(57).estaOcupada()) {
                final Casilla casillaTorre = this.tablero.getCasilla(56);

                if (casillaTorre.estaOcupada() && casillaTorre.getPieza().isPrimeraMovida()) {
                    if (    Jugador.calcularAtaquesEnCasilla(59, movidasLegalesOponente).isEmpty() &&
                            Jugador.calcularAtaquesEnCasilla(58, movidasLegalesOponente).isEmpty() &&
                            Jugador.calcularAtaquesEnCasilla(57, movidasLegalesOponente).isEmpty() &&
                            casillaTorre.getPieza().getTipoPieza().isTorre()) {
                        // todo agregar una movidaEnroque
                        enroques.add(new EnroqueCorto(  this.tablero,
                                                        this.reyJugador,
                                                        58,
                                                        (Torre)casillaTorre.getPieza(),
                                                        casillaTorre.getCoordenadas(),
                                                        59));
                    }
                }
            }
        }
        return ImmutableList.copyOf(enroques);
    }


}
