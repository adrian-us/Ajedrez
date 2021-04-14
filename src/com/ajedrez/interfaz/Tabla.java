package com.ajedrez.interfaz;

import javax.swing.*;
import java.awt.*;

public class Tabla {

    private final JFrame frame;
    private static Dimension DIMENSIONES_FRAME = new Dimension(600,600);

    public Tabla(){
        this.frame = new JFrame("Ajedrez");
        this.frame.setSize(DIMENSIONES_FRAME);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
    }
}
