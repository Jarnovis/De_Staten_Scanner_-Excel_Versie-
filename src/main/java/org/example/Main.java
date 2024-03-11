package org.example;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connector connector = new Connector();
        RightData rightData = new RightData();
        GUI gui = new GUI(connector, rightData);

        boolean running = true;
        connector.open();

        while (running){
            System.out.println("Geef een URL-link op: ");
            String URL = scanner.nextLine();
            //String URL = "https://www.tutorialspoint.com/java-program-to-determine-when-a-frame-or-window-is-closing-in-java";

            if (URL.equalsIgnoreCase("S")){
                connector.close();
            }

            else{
                connector.connect(URL);

            }
        }
    }
}