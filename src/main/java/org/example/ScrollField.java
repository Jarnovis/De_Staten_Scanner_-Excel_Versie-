package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class ScrollField {
    private JTextArea field;
    private JScrollPane scroll;
    private String info;

    public ScrollField(int[] size){ //(
        field = new JTextArea(size[0], size[1]);
        field.setEditable(false);
        field.setVisible(true);
        scroll = new JScrollPane(field);
        scroll.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);

    }

    public void setTextScrollField(ArrayList<ArrayList> text, JComboBox getKeySource, JComboBox selectFromWebsiteFail){
        // Zet de text in het scollfield
        info = "";

        if (text.getFirst().isEmpty()){
            info += "No data avalible\nTry another keySource";
        }
        else {
            // Als er in text de zoektermen zitten, dan wordt er pas vanaf de tweede positie in de lijst (1) gebruikt om gegevens in de scrollfield te zetten
            if (text.getFirst().get(0).equals(getKeySource.getSelectedItem()) && text.get(1).get(0).equals(selectFromWebsiteFail.getSelectedItem())) {
                for (int i = 1; i < text.getFirst().size(); i++) {
                    info += text.getFirst().get(i) + " | " + text.get(1).get(i) + "\n";
                }
                info += String.format("%d matches out of %s",
                        text.getFirst().size() - 1,
                        getKeySource.getSelectedItem());

            }
            else {
                // Als er in text geen zoektermen zitten dan wordt er vanaf de eerste positie (0) gebruikt om gegevens in de scrollfield te zetten
                for (int i = 0; i < text.getFirst().size(); i++) {
                    info += text.getFirst().get(i) + " | " + text.get(1).get(i) + "\n";
                    System.out.println(text.getFirst().get(i) + " | " + text.get(1).get(i));
                }
                info += String.format("%d matches out of %s",
                        text.getFirst().size(),
                        getKeySource.getSelectedItem());
            }
        }
        field.setText(info);
    }

    public void setTextScrollField(ArrayList<Object> names){
        // Zet de text in het scollfield
        info = "";
        // De niet gevonden matches
        for (int i = 0; i < names.size(); i++){
            info += names.get(i) +"\n";
        }
        info += "missing matches: " + names.size();
        field.setText(info);
    }



    public JScrollPane getScroll() {
        return scroll;
    }

    public String getInfo(){
        return info;
    }

}
