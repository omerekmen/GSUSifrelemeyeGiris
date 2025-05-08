public String decrypt(String input, String key) {
    int[] order = getSaccoKeyOrder(key);
    int col = key.length();

    // Calculate the positions where each column appears in the ordering
    int[] columnPositions = new int[col + 1]; // +1 because we're using 1-based indexing
    for (int i = 0; i < col; i++) {
        columnPositions[order[i]] = i + 1;
    }

    // Initialize the column letter count map
    Map<Integer, Integer> colLetterCount = new HashMap<>();

    // Keep track of the current position in the input string
    int currentPos = 0;

    // Process each column in order
    for (int colOrder = 1; colOrder <= col; colOrder++) {
        // Find the actual column index for this order
        int colIndex = -1;
        for (int i = 0; i < col; i++) {
            if (order[i] == colOrder) {
                colIndex = i;
                break;
            }
        }

        // Calculate how many characters are in this column
        int lettersInThisColumn = 0;

        // For each row of the matrix
        int remainingChars = input.length() - currentPos;
        int remainingCols = col - colOrder + 1;

        if (remainingCols > 0) {
            // Calculate full rows that will be filled
            int fullRows = remainingChars / remainingCols;
            // Calculate if this column gets an extra character in the partial row
            int extra = (remainingChars % remainingCols > 0) ? 1 : 0;

            lettersInThisColumn = fullRows + extra;
        }

        colLetterCount.put(colOrder, lettersInThisColumn);
        currentPos += lettersInThisColumn;
    }

    System.out.println("Column letter count: " + colLetterCount);

    // Continue with the rest of your decryption logic
    // ...
}