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

public class Torre extends Pieza {

    private final static int[] COORDENADAS_VECTORES_MOVIDAS_POSIBLES = {-8, -1, 1, 8};

    public Torre(final int posicionPieza, final Alianza alianzaPieza) {
        super(TipoPieza.TORRE, posicionPieza, alianzaPieza);
    }

    @Override
    public Collection<Movida> calcularMovidasLegales(final Tablero tablero) {
        final List<Movida> movidasLegales = new ArrayList<>();
        for (final int posibleDesplazamiento : COORDENADAS_VECTORES_MOVIDAS_POSIBLES) {
            int coordenadaPosibleMovida = this.posicionPieza;
            while (UtilidadesTablero.esCasillaValida(coordenadaPosibleMovida)) {
                if (esExcepcionPrimeraColumna(coordenadaPosibleMovida,posibleDesplazamiento) ||
                        (esExcepcionOctabaColumna(coordenadaPosibleMovida, posibleDesplazamiento))) {
                    break;
                }
                coordenadaPosibleMovida += posibleDesplazamiento;
                if (UtilidadesTablero.esCasillaValida(coordenadaPosibleMovida)) {
                    final Casilla casillaPosibleMovida = tablero.getCasilla(coordenadaPosibleMovida);
                    if (!casillaPosibleMovida.estaOcupada()) {
                        movidasLegales.add(new Movida.MovidaDesplazamiento(tablero, this, coordenadaPosibleMovida));
                    } else {
                        final Pieza piezaEnCasilla = casillaPosibleMovida.getPieza();
                        final Alianza alianzaPieza = piezaEnCasilla.getAlianzaPieza();
                        if (this.alianzaPieza != alianzaPieza) { // Esto significa entonces que son piezas contrarias
                            movidasLegales.add(new Movida.MovidaAtaque(tablero, this, coordenadaPosibleMovida, piezaEnCasilla));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(movidasLegales);
    }

    private static boolean esExcepcionPrimeraColumna(final int posicionActual, final int posibleDesplazamiento) {
        return UtilidadesTablero.PRIMERA_COLUMNA[posicionActual] && (posibleDesplazamiento == -1);
    }
    private static boolean esExcepcionOctabaColumna(final int posicionActual, final int posibleDesplazamiento) {
        return UtilidadesTablero.OCTAVA_COLUMNA[posicionActual] && (posibleDesplazamiento == 1);
    }


    @Override
    public String toString() {
        return TipoPieza.TORRE.toString();
    }

    @Override
    public Torre moverPieza(final Movida movida) {
        return new Torre(movida.getCoordenadasDestino(), movida.getPiezaMovida().alianzaPieza);
    }

}
