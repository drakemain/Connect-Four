import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String DEFAULT = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";

    private static final Map<Character, String> cell = Map.ofEntries(
            Map.entry('e', DEFAULT + " "),
            Map.entry('r', RED + "\u25CF"),
            Map.entry('y', YELLOW + "\u25CF")
    );

    public static void main(String[] args) {
	    ConnectFour game = new ConnectFour();

	    while (game.isRunning()) {
            displayBoard(game.getState());
            try {
                int input = prompt(game.getActivePlayer());
                if (!game.checkExitRequest(input)) {
                    game.dropADisk(input);
                    game.swapActivePlayer();
                }
            } catch(Error e) {
                System.out.println(e.getMessage());
            } catch(InputMismatchException e) {
                System.out.println("Input must be numeric.");
            }
        }
    }

    private static void displayBoard(char[][] state) {
        // print column numbers
        System.out.print(" ");
        for (int i = 0; i < state[0].length; ++i) {
            System.out.print((i + 1) + " ");
        }

        // print each cell in the board
        System.out.print("\n|");
        for (char[] row : state) {
            for (char cellState : row) {
                System.out.print(cell.get(cellState) + DEFAULT + "|");
            }

            if (row != state[state.length - 1]) {
                System.out.print("\n|");
            } else {
                System.out.println();
            }
        }
    }

    private static int prompt(char player) {
        Scanner scanner = new Scanner(System.in);

        System.out.print(cell.get(player) + ": " + DEFAULT);
        return scanner.nextInt();
    }
}
