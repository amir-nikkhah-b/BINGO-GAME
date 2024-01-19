import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class BingoGame {
    private static final int SIZE = 5;
    private static final int MAX_NUMBER = 25;
    private static final int NUMBERS_PER_ROW = 5;

    private int[][] bingoCard;
    private boolean[][] markedNumbers;
    private Random random;
    private Scanner scanner;

    public BingoGame() {
        bingoCard = new int[SIZE][SIZE];
        markedNumbers = new boolean[SIZE][SIZE];
        random = new Random();
        scanner = new Scanner(System.in);
    }

    public void play() {
        initializeBingoCard();
        displayBingoCard();

        while (!isBingo()) {
            int drawnNumber = drawNumber();
            System.out.println("Drawn number: " + drawnNumber);

            markNumberOnCard(drawnNumber);
            displayBingoCard();
        }

        System.out.println("Bingo! You've won!");
    }

    private void initializeBingoCard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                bingoCard[i][j] = random.nextInt(MAX_NUMBER) + 1;
            }
        }
    }

    private void displayBingoCard() {
        System.out.println("Bingo Card:");
        for (int[] row : bingoCard) {
            for (int number : row) {
                System.out.print((markedNumbers[getRowNumber(number)][getColumnNumber(number)]) ? "[X] " : "[" + number + "] ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private int drawNumber() {
        int drawnNumber;
        do {
            drawnNumber = random.nextInt(MAX_NUMBER) + 1;
        } while (isNumberAlreadyDrawn(drawnNumber));

        return drawnNumber;
    }

    private boolean isNumberAlreadyDrawn(int number) {
        for (int[] row : bingoCard) {
            if (Arrays.stream(row).anyMatch(n -> n == number)) {
                return false; // Number is still on the card
            }
        }
        return true; // Number has been drawn
    }

    private void markNumberOnCard(int number) {
        int row = getRowNumber(number);
        int col = getColumnNumber(number);
        markedNumbers[row][col] = true;
    }

    private int getRowNumber(int number) {
        return (number - 1) / NUMBERS_PER_ROW;
    }

    private int getColumnNumber(int number) {
        return (number - 1) % NUMBERS_PER_ROW;
    }

    private boolean isBingo() {
        // Check rows and columns
        for (int i = 0; i < SIZE; i++) {
            if (Arrays.stream(markedNumbers[i]).allMatch(Boolean::booleanValue) ||
                Arrays.stream(markedNumbers).allMatch(row -> row[i])) {
                return true;
            }
        }

        // Check diagonals
        return (markedNumbers[0][0] && markedNumbers[1][1] && markedNumbers[2][2] && markedNumbers[3][3] && markedNumbers[4][4]) ||
               (markedNumbers[0][4] && markedNumbers[1][3] && markedNumbers[2][2] && markedNumbers[3][1] && markedNumbers[4][0]);
    }

    public static void main(String[] args) {
        BingoGame bingoGame = new BingoGame();
        bingoGame.play();
    }
}
