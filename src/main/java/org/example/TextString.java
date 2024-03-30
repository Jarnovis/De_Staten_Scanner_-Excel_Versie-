package org.example;

import javax.swing.*;
import java.awt.*;

public class TextString {
    private JLabel label;
    public void create(String text, boolean visible){
        label = new JLabel(text);

        label.setMaximumSize(new Dimension(220, 15));
        label.setPreferredSize(new Dimension(220, 15));
        label.setVisible(visible);

    }

    public void visible(boolean visibility){
        label.setVisible(visibility);
    }

    public JLabel getText(){
        return label;
    }
}
