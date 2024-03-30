package org.example;

import javax.swing.*;
import java.util.Scanner;


public class Main{
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Connector connector = new Connector();
        connector.open();
        RightData rightData = new RightData(connector);
        GUI_Reworked gui = new GUI_Reworked(connector, rightData);

    }
}