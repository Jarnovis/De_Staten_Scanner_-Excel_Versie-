package org.example;

import javax.swing.*;
import java.util.ArrayList;
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
    }

    public void setTextScrollField(ArrayList<ArrayList> text, boolean match, ArrayList<Object> names, GetKeySource getKeySource, GetKeySource selectFromWebsiteFail){
        // Zet de text in het scollfield
        info = "";

        if (text.getFirst().isEmpty()){
            info += "No data avalible\nTry another keySource";
        }

        // Kijken of de matches of niet gevonden matches aangeroepen wordt
        else if (match){
            // Gevonden matches
            // Als er in text de zoektermen zitten, dan wordt er pas vanaf de tweede positie in de lijst (1) gebruikt om gegevens in de scrollfield te zetten
            if (text.getFirst().get(0).equals(getKeySource.getBox().getSelectedItem()) && text.get(1).get(0).equals(selectFromWebsiteFail.getBox().getSelectedItem())){
                for (int i = 1; i < text.getFirst().size(); i++){
                    info += text.getFirst().get(i) + " | " + text.get(1).get(i) + "\n";
                }
                info += String.format("%d matches out of %d %s (%.2f%%)",
                        text.getFirst().size() - 1,
                        text.getFirst().size() - 1 + names.size(),
                        getKeySource.getBox().getSelectedItem(),
                        ((double) text.getFirst().size() / (text.getFirst().size() + names.size()) * 100));
            }

            else{
                // Als er in text geen zoektermen zitten dan wordt er vanaf de eerste positie (0) gebruikt om gegevens in de scrollfield te zetten
                for (int i = 0; i < text.getFirst().size(); i++){
                    info += text.getFirst().get(i) + " | " + text.get(1).get(i) + "\n";
                }
                info += String.format("%d matches out of %d %s (%.2f%%)",
                        text.getFirst().size() - 1,
                        text.getFirst().size() - 1 + names.size(),
                        getKeySource.getBox().getSelectedItem(),
                        ((double) text.getFirst().size() / (text.getFirst().size() + names.size()) * 100));
            }

        }
        else{
            // De niet gevonden matches
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

    public JScrollPane getScroll() {
        return scroll;
    }

    public String getInfo(){
        return info;
    }

}
