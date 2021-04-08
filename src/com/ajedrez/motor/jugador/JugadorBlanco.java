package com.ajedrez.motor.jugador;

import com.ajedrez.motor.Alianza;
import com.ajedrez.motor.piezas.Pieza;
import com.ajedrez.motor.tablero.Movida;
import com.ajedrez.motor.tablero.Tablero;
import java.util.Collection;

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
}
