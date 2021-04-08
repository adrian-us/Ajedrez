package com.ajedrez.motor.piezas;

import com.ajedrez.motor.Alianza;
import com.ajedrez.motor.tablero.Movida;
import com.ajedrez.motor.tablero.Tablero;
import com.ajedrez.motor.tablero.UtilidadesTablero;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

public class Peon extends Pieza {

    /* Si el peon es una pieza Blanca entonces avanza restando 8, si es una pieza Negra avanza sumando 8 */

    private final static int[] COORDENADAS_MOVIDAS_POSIBLES = {8, 16, 7, 9};

    public Peon(final int posicionPieza, final Alianza alianzaPieza) {
        super(TipoPieza.PEON, posicionPieza, alianzaPieza);
    }

    @Override
    public Collection<Movida> calcularMovidasLegales(Tablero tablero) {

        final List<Movida> movidasLegales = new ArrayList<>();

        for (final int posibleDesplazamiento : COORDENADAS_MOVIDAS_POSIBLES) {

            final int coordenadaPosibleMovida =
                    /* Si la pieza es Blanca se convierte en negativo ( * -1 )
                       Si la pieza es Negra no cambia el signo, sigue siendo positivo ( * 1 )
                     */
                    this.posicionPieza + (this.getAlianzaPieza().getDireccion() * posibleDesplazamiento);
            if (!UtilidadesTablero.esCasillaValida(coordenadaPosibleMovida)) {
                continue;
            }

            if (posibleDesplazamiento == 8 && !tablero.getCasilla(coordenadaPosibleMovida).estaOcupada()) {
                // F A L T A (promover peones)!
                movidasLegales.add(new Movida.MovidaDesplazamiento(tablero, this, coordenadaPosibleMovida));
            } else if (posibleDesplazamiento == 16 && this.isPrimeraMovida()
                    && (UtilidadesTablero.SEGUNDA_FILA[this.posicionPieza] && this.alianzaPieza.isNegra())
                    || (UtilidadesTablero.SEPTIMA_FILA[this.posicionPieza] && this.alianzaPieza.isBlanca())) {
                /* Cada peon tiene un posible desplazamiento de 16 que serian dos espacios adelante pero se tiene que
                 verificar :
                    1. Sea la primera movida de ese peon 'this.isPrimeraMovida()'
                    2. Se encuentre en la Segunda o Septima fila respectivas a su color
                   Esto para validar tambien partidas cargadas por FEN/PGN
                 */
                final int coordenadaPosibleMovidaAnterior = this.posicionPieza + (this.alianzaPieza.getDireccion() * 8);
                if (!tablero.getCasilla(coordenadaPosibleMovidaAnterior).estaOcupada()
                        && !tablero.getCasilla(coordenadaPosibleMovida).estaOcupada()) {
                    /* Si la siguiente casilla y la siguiente despues de esa no estan ocupadas entonces agrego la
                    movida a la lista de movidas legales */
                    movidasLegales.add(new Movida.MovidaDesplazamiento(tablero, this, coordenadaPosibleMovida));
                }
            } else if (coordenadaPosibleMovida == 7 &&
                    !((UtilidadesTablero.OCTAVA_COLUMNA[this.posicionPieza] && this.alianzaPieza.isBlanca() ||
                     (UtilidadesTablero.PRIMERA_COLUMNA[this.posicionPieza] && this.alianzaPieza.isNegra()))))
            /* El 7 es una excepcion para un peon blanco en la columna 8 y para un peon negro en la columna 1 */
            {
                if (tablero.getCasilla(coordenadaPosibleMovida).estaOcupada()) {
                    final Pieza piezaEnCasilla = tablero.getCasilla(coordenadaPosibleMovida).getPieza();
                    if (this.alianzaPieza != piezaEnCasilla.getAlianzaPieza()) {
                        // FALTA capturar pieza y promover
                        movidasLegales.add(new Movida.MovidaDesplazamiento(tablero, this, coordenadaPosibleMovida));
                    }
                }
            } else if (coordenadaPosibleMovida == 9 &&
                    !((UtilidadesTablero.PRIMERA_COLUMNA[this.posicionPieza] && this.alianzaPieza.isBlanca() ||
                    (UtilidadesTablero.OCTAVA_COLUMNA[this.posicionPieza] && this.alianzaPieza.isNegra()))))
            {
                if (tablero.getCasilla(coordenadaPosibleMovida).estaOcupada()) {
                    final Pieza piezaEnCasilla = tablero.getCasilla(coordenadaPosibleMovida).getPieza();
                    if (this.alianzaPieza != piezaEnCasilla.getAlianzaPieza()) {
                        // FALTA capturar pieza y promover
                        movidasLegales.add(new Movida.MovidaDesplazamiento(tablero, this, coordenadaPosibleMovida));
                    }
                }
            }
        }
        return ImmutableList.copyOf(movidasLegales);
    }

    @Override
    public String toString() {
        return TipoPieza.PEON.toString();
    }

    @Override
    public Peon moverPieza(final Movida movida) {
        return new Peon(movida.getCoordenadasDestino(), movida.getPiezaMovida().alianzaPieza);
    }

}
