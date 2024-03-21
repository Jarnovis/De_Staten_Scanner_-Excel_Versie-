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
        //System.out.println("Voor testen excek: uitschakelen URL LINK en zorgen dat de loop niet eindigt!!!!!!!!!!!!!!!!!!!!!!!!!");

        while (running){
            //System.out.println("Geef een URL-link op: ");
            //String URL = scanner.nextLine();
            String URL = "https://zonsopgang.info/delft/2024-06-06";

            if (URL.equalsIgnoreCase("S")){
                connector.close();
            }

            else{
                connector.connect(URL);

            }
        }
    }
}