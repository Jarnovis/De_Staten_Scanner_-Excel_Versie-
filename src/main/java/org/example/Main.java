package org.example;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Connector connector = new Connector();
        connector.open();
        RightData rightData = new RightData();
        GUI gui = new GUI(connector, rightData);

        while (true) {
            // Zorgt ervoor dat de loop niet eindigt
        }
    }
}