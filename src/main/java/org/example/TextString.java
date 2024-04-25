package org.example;

import javax.swing.*;
import java.awt.*;

public class TextString {
    private JLabel label;
    public TextString(String text, boolean visible, int[] size){
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

    public void setText(JComboBox getKeySource, JComboBox selectFromWebsiteFail){
        String text = getKeySource.getSelectedItem() + " | " + selectFromWebsiteFail.getSelectedItem();
        label.setText(text);
    }

    public JLabel getText(){
        return label;
    }
}
