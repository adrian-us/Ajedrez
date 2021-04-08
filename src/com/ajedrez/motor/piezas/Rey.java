package com.ajedrez.motor.piezas;

import com.ajedrez.motor.Alianza;
import com.ajedrez.motor.tablero.Casilla;
import com.ajedrez.motor.tablero.Movida;
import com.ajedrez.motor.tablero.Tablero;
import com.ajedrez.motor.tablero.UtilidadesTablero;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.ajedrez.motor.tablero.Movida.*;

public class Rey extends Pieza {

    private final static int[] COORDENADAS_MOVIDAS_POSIBLES = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Rey(final int posicionPieza, final Alianza alianzaPieza) {
        super(TipoPieza.REY, posicionPieza, alianzaPieza);
    }

    @Override
    public Collection<Movida> calcularMovidasLegales(Tablero tablero) {

        final List<Movida> movidasLegales = new ArrayList<>();

        for (final int posibleDesplazamiento : COORDENADAS_MOVIDAS_POSIBLES) {
            final int coordenadaPosibleMovida = this.posicionPieza + posibleDesplazamiento;

            if (esExcepcionPrimeraColumna(this.posicionPieza, posibleDesplazamiento) ||
                esExcepcionOctavaColumna(this.posicionPieza, posibleDesplazamiento)) {
                continue;
            }

            if (UtilidadesTablero.esCasillaValida(coordenadaPosibleMovida)) {
                final Casilla casillaPosibleMovida = tablero.getCasilla(coordenadaPosibleMovida);
                if (!casillaPosibleMovida.estaOcupada()) {
                    movidasLegales.add(new MovidaDesplazamiento(tablero, this, coordenadaPosibleMovida));
                } else {
                    final Pieza piezaEnCasilla = casillaPosibleMovida.getPieza();
                    final Alianza alianzaPieza = piezaEnCasilla.getAlianzaPieza();
                    if (this.alianzaPieza != alianzaPieza) {
                        movidasLegales.add(new MovidaAtaque(tablero, this, coordenadaPosibleMovida, piezaEnCasilla));
                    }
                }
            }
        }

        return ImmutableList.copyOf(movidasLegales);
    }

    private static boolean esExcepcionPrimeraColumna(final int posicionActual, final int posibleDesplazamiento) {
        return UtilidadesTablero.PRIMERA_COLUMNA[posicionActual] && (posibleDesplazamiento == -9 ||
                posibleDesplazamiento == -1 || posibleDesplazamiento == 7);
    }

    private static boolean esExcepcionOctavaColumna(final int posicionActual, final int posibleDesplazamiento) {
        return UtilidadesTablero.OCTAVA_COLUMNA[posicionActual] && (posibleDesplazamiento == -7 ||
                posibleDesplazamiento == 1 || posibleDesplazamiento == 9);
    }

    @Override
    public String toString() {
        return TipoPieza.REY.toString();
    }

    @Override
    public Rey moverPieza(final Movida movida) {
        return new Rey(movida.getCoordenadasDestino(), movida.getPiezaMovida().alianzaPieza);
    }

}
