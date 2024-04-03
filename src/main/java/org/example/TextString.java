package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TextString {
    private JLabel label;
    public void create(String text, boolean visible, int[] size){
        label = new JLabel(text);

        if (size == null){
            label.setMaximumSize(new Dimension(100, 15));
            label.setPreferredSize(new Dimension(100, 15));
        }

        else{
            label.setMaximumSize(new Dimension(size[0], size[1]));
            label.setPreferredSize(new Dimension(size[0], size[1]));
        }
        label.setVisible(visible);

    }

    public void visible(boolean visibility){
        label.setVisible(visibility);
    }

    public void setText(ArrayList<ArrayList> info){
        String text = info.getFirst().getFirst() + " | " + info.getFirst().get(1) + " | " + "Excel X | Excel Y";
        label.setText(text);
    }

    public JLabel getText(){
        return label;
    }
}
