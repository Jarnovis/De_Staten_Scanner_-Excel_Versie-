package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectFromSheetButton extends SearchAndUpload implements IButton{
    private JButton button;
    private String sheet;
    private final boolean[] through = {true};
    private final boolean[] pressed = {false};
    private boolean visable;

    public SelectFromSheetButton(String name, boolean visable){
        super(name, visable);
        button = new JButton(name);
        this.visable = visable;
    }

    @Override
    public void create(){
        this.button.setMaximumSize(new Dimension(150, 25));
        this.button.setBackground(Color.lightGray);
        this.button.setVisible(visable);
    }


    public void action(SelectFromSheet selectFromSheet, RightData rightData, GetKeySource getKeySource, SelectFromSheetButton selectKeySourceButton){
        // Verzameld de hoofdzoekterm
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sheet = (String) selectFromSheet.getBox().getSelectedItem();

                try {
                    getKeySource.getKey(rightData, selectFromSheet, null);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                selectKeySourceButton.setSheet(sheet);
            };
        });

    }

    public void action(RightData rightData, GetKeySource getKeySource){
        // Verzameld alle gegevens van de hoofdzoekterm
        pressed[0] = false;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!rightData.gatherKeySource((String) getKeySource.getBox().getSelectedItem(), sheet)){
                        through[0] = false;
                    }

                    else{
                        through[0] = true;
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                pressed[0] = true;
            };
        });
    }

    @Override
    public void action(RightData rightData, SelectFromSheet selectFromSheetFail){
        // Zorgt ervoor dat de niet-automatische gevonden matches gemaakt kunnen worden
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightData.checkData((String) selectFromSheetFail.getBox().getSelectedItem(), null);
            }
        });

    }

    public void action(RightData rightData, SelectFromSheet selectFromSheetFail, GetKeySource selectFromWebsiteFail, ScrollField matchesField, ScrollField noMatchesField, GetKeySource getKeySource){
        // Zorgt ervoor dat de matches en niet-matches in hun scrollfield gezet worden
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightData.checkData((String) selectFromSheetFail.getBox().getSelectedItem(), (String) selectFromWebsiteFail.getBox().getSelectedItem());
                matchesField.setTextScrollField(rightData.getMatches(), true, rightData.getNoMatches(), getKeySource, selectFromWebsiteFail);
                noMatchesField.setTextScrollField(rightData.getMatches(),  false, rightData.getNoMatches(), getKeySource, selectFromWebsiteFail);
                pressed[0] = true;
            }
        });
    }


    public void visible(boolean visabilty){
        button.setVisible(visabilty);
    }

    public JButton getButton(){
        return button;
    }

    public void setSheet(String sheet){
        this.sheet = sheet;
    }

    public boolean getThrough(){
        return through[0];
    }
    public void setThrough(boolean set){
        through[0] = set;
    }

    public boolean[] getPressed(){
        // pressed clonen, zodat pressed_old niet ook meteen naar false gezet wordt
        boolean[] pressed_old = pressed.clone();
        pressed[0] = false;
        return pressed_old;
    }

}
