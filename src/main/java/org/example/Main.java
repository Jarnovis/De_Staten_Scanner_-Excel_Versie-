package org.example;

public class Main{
    public static void main(String[] args) throws Exception {
        Connector connector = new Connector();
        connector.open();
        RightData rightData = new RightData();
        GUI_Upload gui_upload = new GUI_Upload(rightData);
        GUI_Commit gui_commit = new GUI_Commit();
        GUI_Search gui_search = new GUI_Search(gui_upload, gui_commit, connector, rightData, false);
    }
}