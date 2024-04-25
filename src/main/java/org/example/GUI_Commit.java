package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GUI_Commit extends GUI_Search implements IGUI {
    protected JFrame frame = new JFrame();
    private ScrollField matches = new ScrollField(new int[] {10, 20});
    private ScrollField noMatches = new ScrollField(new int[] {10, 20});
    private TextString matchText = new TextString("Founded Matches", true, new int[] {200, 15});
    private TextString noMatchText = new TextString("Matches Not Found", true, new int[] {200, 15});
    public GUI_Commit(){
        super();
        placement();
        window();
    }

    @Override
    public void window(){
        frame.setName("Commit");
        frame.setSize(new Dimension(500, 400));
        frame.setVisible(false);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        WindowListener Listener = new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                setVisible(false);
            }
        };

        frame.addWindowListener(Listener);
    }

    @Override
    public void placement(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(5, 5, 5, 5);
        panel.add(matchText.getText(), constraints);

        constraints.gridx ++;
        panel.add(noMatchText.getText(), constraints);

        constraints.gridy ++;
        constraints.gridx --;
        panel.add(matches.getScroll(), constraints);

        constraints.gridx ++;
        panel.add(noMatches.getScroll(), constraints);

        frame.add(panel, BorderLayout.CENTER);

    }
    protected void updateMatchesBox(RightData rightData, JComboBox keySource, JComboBox website){
        matchText.setText(keySource, website);
        matches.setTextScrollField(rightData.getMatches(), keySource, website);
        website.removeAllItems();
        website.addItem("Website Fail");
        website.setVisible(false);
    }

    protected void updateNoMatchesBox(RightData rightData){
        noMatches.setTextScrollField(rightData.getNoMatches());
    }

    @Override
    public void setVisible(boolean visible){
        frame.setVisible(visible);
    }

    // TestOnly functionaliteiten
    public ScrollField getMatches() {
        return matches;
    }

    public ScrollField getNoMatches() {
        return noMatches;
    }
}
