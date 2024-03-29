package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectFromSheetButton extends UploadButton implements IButton{
    private JButton button;
    private String name;
    private String sheet;

    public SelectFromSheetButton(String name){
        button = new JButton(name);
        this.name = name;
    }

    @Override
    public void create(){
        this.button.setMaximumSize(new Dimension(150, 25));
        this.button.setBackground(Color.lightGray);
        this.button.setVisible(true);
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

    public boolean[] action(RightData rightData, GetKeySource getKeySource){
        final boolean[] through = {true};
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(getKeySource.getBox().getSelectedItem());
                if (!rightData.gatherKeySource((String) getKeySource.getBox().getSelectedItem(), sheet)){
                    through[0] = false;
                }
            };
        });
        return through;

    }

    public void action(RightData rightData, SelectFromSheet selectFromSheetFail){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightData.checkData((String) selectFromSheetFail.getBox().getSelectedItem());
            }
        });

    }

    public JButton getButton(){
        return button;
    }

    public void setSheet(String sheet){
        this.sheet = sheet;
    }

}
