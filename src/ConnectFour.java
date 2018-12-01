public class ConnectFour {
    private char[][] board = new char[ROWS][COLS];
    private char currentPlayer;
    private boolean isRunning = true;

    private static final int ROWS = 6;
    private static final int COLS = 7;

    public ConnectFour() {
        // set all cells in board to empty state
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col] = 'e';
            }
        }

        currentPlayer = 'r';
    }

    public boolean checkExitRequest(int input) {
        if (input == -1) {
            this.isRunning = false;
        }

        return !this.isRunning;
    }

    public void dropADisk(int col) {
        final int COLINDEX = col - 1;

        if (!this.isValidInputRange(COLINDEX)) {
            throw new Error("Input must be between 1 and " + COLS + ".");
        } else if (this.isColFull(COLINDEX)) {
            throw new Error("Column is full.");
        }

        this.placeADisk(COLINDEX);
    }

    private void placeADisk(int col) {
        int row = ROWS - 1;

        while (this.board[row][col] != 'e') {
            --row;
        }

        this.board[row][col] = this.currentPlayer;
    }

    public void swapActivePlayer() {
        this.currentPlayer = currentPlayer == 'r' ? 'y' : 'r';
    }

    public char getActivePlayer() {
        return this.currentPlayer;
    }

    public boolean isRunning() { return this.isRunning; }

    public char[][] getState() { return this.board; }

    private boolean isValidInputRange(int val) {
        return 0 <= val && val <= COLS - 1;
    }

    private boolean isColFull(int col) {
        return this.board[0][col] != 'e';
    }
}

