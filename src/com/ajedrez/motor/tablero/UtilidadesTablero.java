package com.ajedrez.motor.tablero;

public class UtilidadesTablero {

    public static final boolean[] PRIMERA_COLUMNA = initColumna(0);
    public static final boolean[] SEGUNDA_COLUMNA = initColumna(1);
    public static final boolean[] SEPTIMA_COLUMNA = initColumna(6);
    public static final boolean[] OCTAVA_COLUMNA = initColumna(7);

    public static final boolean[] SEGUNDA_FILA = initFila(8);
    public static final boolean[] SEPTIMA_FILA = initFila(48);


    public static final int CANT_CASILLAS = 64;
    public static final int CANT_CASILLAS_POR_FILA = 8;

    private UtilidadesTablero() {
        throw new RuntimeException("Esta clase no se puede inicializar.");
    }

    /* Metodo : Dada una coordenada se inicializa en 'true' cada casilla restante de esa columna */
    private static boolean[] initColumna(int numeroColumna) {
        final boolean[] columna = new boolean[CANT_CASILLAS];
        do {
            columna[numeroColumna] = true;
            numeroColumna += CANT_CASILLAS_POR_FILA;
        } while (numeroColumna < CANT_CASILLAS);
        return columna;
    }

    private static boolean[] initFila(int numeroFila) {
        final boolean[] fila = new boolean[CANT_CASILLAS];
        do {
            fila[numeroFila] = true;
            numeroFila++;
        } while (numeroFila % CANT_CASILLAS_POR_FILA != 0);
        return fila;
    }

    public static boolean esCasillaValida(final int coordenada) {
        return coordenada >= 0 && coordenada < 64;
    }


}
