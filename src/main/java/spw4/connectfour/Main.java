package spw4.connectfour;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        ConnectFour connectFour = new ConnectFourImpl(Player.red);
        System.out.println(connectFour);

        while (!connectFour.isGameOver()) {
            System.out.print("command [1 .. 7, (r)estart, (q)uit, (h)elp] > ");
            input = scanner.nextLine();

            switch (input) {
                case "1":
                    connectFour.drop(1);
                    break;
                case "2":
                    connectFour.drop(2);
                    break;
                case "3":
                    connectFour.drop(3);
                    break;
                case "4":
                    connectFour.drop(4);
                    break;
                case "5":
                    connectFour.drop(5);
                    break;
                case "6":
                    connectFour.drop(6);
                    break;
                case "7":
                    connectFour.drop(7);
                    break;
                case "r":
                    connectFour.reset(Player.red);
                    break;
                case "q":
                    System.out.println("Ok, bye.");
                    return;
                case "h":
                    printHelp();
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
            System.out.println(connectFour);
        }
        System.out.println("GAME OVER - Winner: " + connectFour.getWinner().toString().toUpperCase());
    }

    private static void printHelp() {
        System.out.println();
        System.out.println("Available commands:");
        System.out.println("-------------------");
        System.out.println("1 .. 7 --> drop disc in column");
        System.out.println("r      --> restart game");
        System.out.println("q      --> quit game");
        System.out.println("h      --> show help");
    }
}
