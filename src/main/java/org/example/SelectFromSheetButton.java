package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectFromSheetButton extends UploadButton implements IButton{
    private JButton button;
    private String sheet;
    private final boolean[] through = {true};
    private final boolean[] pressed = {false};

    public SelectFromSheetButton(String name){
        button = new JButton(name);
    }

    @Override
    public void create(boolean visable){
        this.button.setMaximumSize(new Dimension(150, 25));
        this.button.setBackground(Color.lightGray);
        this.button.setVisible(visable);
    }


    public void action(SelectFromSheet selectFromSheet, RightData rightData, GetKeySource getKeySource, SelectFromSheetButton selectKeySourceButton){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rightData.createWorkbook();
                    sheet = (String) selectFromSheet.getBox().getSelectedItem();
                    rightData.readFile(sheet, null);
                    getKeySource.getKey(rightData, selectFromSheet);
                    selectKeySourceButton.setSheet(sheet);

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            };
        });

    }

    public void action(RightData rightData, GetKeySource getKeySource){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!rightData.gatherKeySource((String) getKeySource.getBox().getSelectedItem(), sheet)){
                    through[0] = false;
                }

                else{
                    through[0] = true;
                }
            };
        });
    }

    public void action(RightData rightData, SelectFromSheet selectFromSheetFail){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightData.checkData((String) selectFromSheetFail.getBox().getSelectedItem(), null);
            }
        });

    }

    public void action(RightData rightData, SelectFromSheet selectFromSheetFail, GetKeySource selectFromWebsiteFail, ScrollField matchesField, ScrollField noMatchesField){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightData.checkData((String) selectFromSheetFail.getBox().getSelectedItem(), (String) selectFromWebsiteFail.getBox().getSelectedItem());
                matchesField.setTextScrollField(rightData.getMatches(), rightData.getPositions(), null);
                noMatchesField.setTextScrollField(null,  null, rightData.getNoMatches());
                pressed[0] = true;
            }
        });
    }

    @Override
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
