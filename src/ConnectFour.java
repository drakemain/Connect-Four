public class ConnectFour {
    private char[][] board = new char[ROWS][COLS];
    private char currentPlayer;
    private boolean isRunning = true;
    private char winner = '\0';

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

    private boolean checkExitRequest(int input) {
        return input == -1;
    }

    public void processInput(int input) {
        if (checkExitRequest(input)) {
            this.isRunning = false;
        } else {
            if (!this.isValidInputRange(input)) {
                throw new Error("Input must be between 1 and " + COLS + ". (-1 to exit)");
            }

            dropADisk(input);
            this.swapActivePlayer();
            this.checkWin(input);
        }
    }

    private void dropADisk(int col) {
        final int COLINDEX = col - 1;

        if (this.isColFull(COLINDEX)) {
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

    public boolean isWon() {
        return this.winner != '\0';
    }

    public void reset() {
        // set all cells to empty state
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                this.board[row][col] = 'e';
            }
        }

        // red goes first by default, else whoever lost goes first
        if (this.winner == '\0' || this.winner == 'y') {
            this.currentPlayer = 'r';
        } else {
            this.currentPlayer = 'y';
        }

        this.winner = '\0';
    }

    public boolean isDraw() {
        boolean draw = true;

        for (char cell : this.board[0]) {
            if (cell == 'e') {
                draw = false;
                break;
            }
        }

        return draw;
    }

    public char[][] getState() { return this.board; }

    public char getWinner() { return this.winner; }
    
    private void checkWin(int col) {
        int colIndex = col - 1;
        int row = ROWS - 1;
        char player;

        // find the top occupied (and therefore the last to
        // be occupied) row in the given column
        while (row > 0 && this.board[row - 1][colIndex] != 'e') {
            --row;
        }

        player = this.board[row][colIndex];

        if (this.board[row][colIndex] == 'e') {
            return;
        }

        if (
                this.checkHorizontal(row, colIndex) >= 4
                || this.checkVertical(row, colIndex) >= 4
                || this.checkDiagLTR(row, colIndex) >= 4
        ) {
            this.winner = player;
        }

        // TODO:
        // check diag r->l
    }

    private int checkHorizontal(int row, int col) {
        char player = this.board[row][col];
        
        // start at 1 to count the starting space
        int counter = 1;
        int checkCol = col - 1;
        
        // count the cells the player occupies to the left of the
        // starting position within the bounds of the board
        while (checkCol >= 0 && this.board[row][checkCol] == player) {
            --checkCol;
            ++counter;
        }
        
        checkCol = col + 1;

        // count cells to the right
        while (checkCol < COLS && this.board[row][checkCol] == player) {
            ++checkCol;
            ++counter;
        }

        return counter;
    }

    // Expects input to be the top occupied row
    // and therefore only needs to check lower cells
    private int checkVertical(int row, int col) {
        int result = 0;
        char player = this.board[row][col];

        if (player != 'e') {
            while (row < ROWS && this.board[row][col] == player) {
                ++result;
                ++row;
            }
        }

        return result;
    }

    private int checkDiagLTR(int row, int col) {
        int result = 1;
        char player = this.board[row][col];
        int checkCol = col - 1;
        int checkRow = row - 1;

        while(
                checkRow >= 0
                && checkCol >= 0
                && this.board[checkRow][checkCol] == player
        ) {
            ++result;
            --checkCol;
            --checkRow;
        }

        checkCol = col + 1;
        checkRow = row + 1;

        while(
                checkRow < ROWS
                && checkCol < COLS
                && this.board[checkRow][checkCol] == player
        ) {
            ++result;
            ++checkCol;
            ++checkRow;
        }

        return result;
    }

    private boolean isValidInputRange(int val) {
        return 1 <= val && val <= COLS;
    }

    private boolean isColFull(int col) {
        return this.board[0][col] != 'e';
    }
}

