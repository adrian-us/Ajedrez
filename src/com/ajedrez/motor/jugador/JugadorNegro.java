package com.ajedrez.motor.jugador;

import com.ajedrez.motor.Alianza;
import com.ajedrez.motor.piezas.Pieza;
import com.ajedrez.motor.tablero.Movida;
import com.ajedrez.motor.tablero.Tablero;
import java.util.Collection;

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
}
