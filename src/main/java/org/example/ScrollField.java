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

    public void setTextScrollField(ArrayList<ArrayList> text, boolean match, ArrayList<Object> names){
        String info = "";

        if (match){
            for (int i = 1; i < text.getFirst().size(); i++){
                info += text.getFirst().get(i) + " | " + text.get(1).get(i) + "\n";
            }

            info += text.getFirst().size()-1 + " matches out of " + (Integer) (text.getFirst().size()-1 + names.size()) + " " + text.getFirst().getFirst() + " " + " (" + (Double.valueOf(((double) text.getFirst().size() /(text.getFirst().size() + names.size())*100)))  + "%";
        }

        else{
            for (int i = 1; i < names.size(); i++){
                if (i == names.size()-1){
                    info += names.get(i);
                }

                else{
                    info += names.get(i) +"\n";
                }
            }
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
