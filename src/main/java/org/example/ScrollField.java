package org.example;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class ScrollField {
    private JTextArea field;
    private JScrollPane scroll;

    public ScrollField(int[] size){ //(
        field = new JTextArea(size[0], size[1]);
        field.setEditable(false);
        field.setVisible(true);
        scroll = new JScrollPane(field);
        scroll.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
    }

    public void setTextScrollField(ArrayList<ArrayList> text, ArrayList<Integer> positions){
        String info = "";
        for (int i = 1; i < text.getFirst().size(); i++){
            info += text.getFirst().get(i) + " | " + text.get(1).get(i) + " | " + positions.get(i * 2) + " | " + (positions.get(i * 2 + 1)+1) + "\n";
        }

        field.setText(info);
    }

    public JTextArea getField(){
        return field;
    }
    public JScrollPane getScroll() {
        return scroll;
    }

}
