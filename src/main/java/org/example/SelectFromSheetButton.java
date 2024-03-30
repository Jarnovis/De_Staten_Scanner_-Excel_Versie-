package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectFromSheetButton extends UploadButton implements IButton{
    private JButton button;
    private String name;
    private String sheet;
    private final boolean[] through = {true};

    public SelectFromSheetButton(String name){
        button = new JButton(name);
        this.name = name;
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
                rightData.checkData((String) selectFromSheetFail.getBox().getSelectedItem());
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

}
