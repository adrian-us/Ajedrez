package com.ajedrez.motor.piezas;

import com.ajedrez.motor.Alianza;
import com.ajedrez.motor.tablero.Casilla;
import com.ajedrez.motor.tablero.Movida;
import com.ajedrez.motor.tablero.Movida.MovidaDesplazamiento;
import com.ajedrez.motor.tablero.Movida.MovidaAtaque;
import com.ajedrez.motor.tablero.Tablero;
import com.ajedrez.motor.tablero.UtilidadesTablero;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Caballo extends Pieza {

    private final static int[] COORDENADAS_MOVIDAS_POSIBLES = {-17,-15,-10,-6,6,10,15,17};

    public Caballo(final int posicionPieza, final Alianza alianzaPieza) {
        super(TipoPieza.CABALLO, posicionPieza, alianzaPieza);
    }


    @Override
    public Collection<Movida> calcularMovidasLegales(final Tablero tablero) {

        List<Movida> movidasLegales = new ArrayList<>();

        for (final int posibleDesplazamiento : COORDENADAS_MOVIDAS_POSIBLES) {
            final int coordenadaPosibleMovida = this.posicionPieza + posibleDesplazamiento;
            if (UtilidadesTablero.esCasillaValida(coordenadaPosibleMovida)) {

                if (esExcepcionPrimeraColumna(this.posicionPieza, posibleDesplazamiento) ||
                        esExcepcionSegundaColumna(this.posicionPieza, posibleDesplazamiento) ||
                        esExcepcionSeptimaColumna(this.posicionPieza, posibleDesplazamiento) ||
                        esExcepcionOctavaColumna(this.posicionPieza, posibleDesplazamiento)) {
                    continue;
                }

                final Casilla casillaPosibleMovida = tablero.getCasilla(coordenadaPosibleMovida);
                if (!casillaPosibleMovida.estaOcupada()) {
                    movidasLegales.add(new MovidaDesplazamiento(tablero, this, coordenadaPosibleMovida));
                } else {
                    final Pieza piezaEnCasilla = casillaPosibleMovida.getPieza();
                    final Alianza alianzaPieza = piezaEnCasilla.getAlianzaPieza();
                    if (this.alianzaPieza != alianzaPieza) { // Esto significa entonces que son piezas contrarias
                        movidasLegales.add(new MovidaAtaque(tablero, this, coordenadaPosibleMovida, piezaEnCasilla));
                    }
                }
            }
        }
        return ImmutableList.copyOf(movidasLegales);
    }
    /* Excepciones : Columnas A,B,G,H
        Patron de error de primera columna 'A' : {-17, -10, 6, 15}
        Patron de error de segunda columna 'B' : {-10, 6}
        Patron de error de septima columna 'G' : {-6, 10}
        Patron de error de octava columna  'H' : {-15, -6, 10, 17}
    */

    // Primera columna 'A'
    private static boolean esExcepcionPrimeraColumna(final int posicionActual, final int posibleDesplazamiento) {
        /* Si estoy en la primera columna y mi posible desplazamiento es {-17,-10,6,15} entonces es una excepcion
           y la movida no es legal */
        return UtilidadesTablero.PRIMERA_COLUMNA[posicionActual] && (posibleDesplazamiento == -17 ||
                posibleDesplazamiento == -10 || posibleDesplazamiento == 6 || posibleDesplazamiento == 15);
    }
    // Segunda columna 'B'
    private static boolean esExcepcionSegundaColumna(final int posicionActual, final int posibleDesplazamiento) {
        return UtilidadesTablero.SEGUNDA_COLUMNA[posicionActual] && (posibleDesplazamiento == -10 ||
                posibleDesplazamiento == 6);
    }
    // Septima Columna 'G'
    private static boolean esExcepcionSeptimaColumna(final int posicionActual, final int posibleDesplazamiento) {
        return UtilidadesTablero.SEPTIMA_COLUMNA[posicionActual] && (posibleDesplazamiento == -6 ||
                posibleDesplazamiento == 10);
    }
    // Octava Columna 'H'
    private static boolean esExcepcionOctavaColumna(final int posicionActual, final int posibleDesplazamiento) {
        return UtilidadesTablero.OCTAVA_COLUMNA[posicionActual] && (posibleDesplazamiento == -15 ||
                posibleDesplazamiento == -6 || posibleDesplazamiento == 10 || posibleDesplazamiento == 17);
    }

    @Override
    public String toString() {
        return TipoPieza.CABALLO.toString();
    }

    @Override
    public Caballo moverPieza(final Movida movida) {
        return new Caballo(movida.getCoordenadasDestino(), movida.getPiezaMovida().alianzaPieza);
    }

}
