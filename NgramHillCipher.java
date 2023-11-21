import java.util.Scanner;

public class NgramHillCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("N-gram Hill Cipher");
        while (true) {
            System.out.print("Choose an option (E)ncrypt, (D)ecrypt, (Q)uit: ");
            String choice = scanner.nextLine().trim().toUpperCase();

            if (choice.equals("Q")) {
                break;
            } else if (choice.equals("E") || choice.equals("D")) {
                System.out.print("Enter the text (uppercase letters only): ");
                String text = scanner.nextLine().trim().toUpperCase();

                System.out.print("Enter the key as alphabetic characters: ");
                String keyString = scanner.nextLine().trim().toUpperCase();

                int n = (int) Math.sqrt(keyString.length());

                if (isValidInput(text, n)) {
                    int[][] keyMatrix = getKeyMatrix(keyString, n);
                    if (choice.equals("E")) {
                        String encryptedText = encrypt(text, keyMatrix, n);
                        System.out.println("Encrypted text: " + encryptedText);
                    } else {
                        String decryptedText = decrypt(text, keyMatrix, n);
                        System.out.println("Decrypted text: " + decryptedText);
                    }
                } else {
                    System.out.println("Invalid input. Text should be uppercase letters, and n should be a positive integer.");
                }
            } else {
                System.out.println("Invalid option. Please choose 'E', 'D', or 'Q'.");
            }
        }

        scanner.close();
    }

    public static boolean isValidInput(String text, int n) {
        return text.matches("^[A-Z]+$") && n > 0;
    }

    public static int[][] getKeyMatrix(String keyString, int n) {
        int[][] keyMatrix = new int[n][n];
        int k = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                keyMatrix[i][j] = keyString.charAt(k) - 'A';
                k++;
            }
        }

        return keyMatrix;
    }

    public static String encrypt(String text, int[][] keyMatrix, int n) {
        StringBuilder ciphertext = new StringBuilder();
        int alphabetSize = 26;

        // Pad the text if its length is not a multiple of n
        while (text.length() % n != 0) {
            text += "X";
        }

        for (int i = 0; i < text.length(); i += n) {
            String block = text.substring(i, i + n);
            int[] blockVector = new int[n];

            for (int j = 0; j < n; j++) {
                blockVector[j] = block.charAt(j) - 'A';
            }

            int[] resultVector = new int[n];

            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    resultVector[j] += keyMatrix[j][k] * blockVector[k];
                }
                resultVector[j] %= alphabetSize;
            }

            for (int j = 0; j < n; j++) {
                ciphertext.append((char) (resultVector[j] + 'A'));
            }
        }

        return ciphertext.toString();
    }

    public static String decrypt(String text, int[][] keyMatrix, int n) {
        StringBuilder plaintext = new StringBuilder();
        int alphabetSize = 26;
    
        for (int i = 0; i < text.length(); i += n) {
            String block = text.substring(i, i + n);
            int[] blockVector = new int[n];
    
            for (int j = 0; j < n; j++) {
                blockVector[j] = block.charAt(j) - 'A';
            }
    
            int[] resultVector = new int[n];
    
            int[][] inverseKeyMatrix = inverseMatrix(keyMatrix, alphabetSize);
    
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    resultVector[j] += inverseKeyMatrix[j][k] * blockVector[k];
                }
                resultVector[j] = (resultVector[j] % alphabetSize + alphabetSize) % alphabetSize;
            }
    
            for (int j = 0; j < n; j++) {
                plaintext.append((char) (resultVector[j] + 'A'));
            }
        }
    
        return plaintext.toString();
    }
    
    public static int[][] inverseMatrix(int[][] matrix, int modulo) {
        int[][] inverse = new int[matrix.length][matrix[0].length];
        int determinant = determinant(matrix, modulo);

        if (determinant == 0) {
            throw new IllegalArgumentException("The key matrix is not invertible.");
        }

        int detInverse = modInverse(determinant, modulo);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int minorDet = determinant(minor(matrix, i, j), modulo);
                int cofactor = (int) Math.pow(-1, i + j) * minorDet;
                inverse[i][j] = (cofactor * detInverse) % modulo;
                if (inverse[i][j] < 0) {
                    inverse[i][j] += modulo;
                }
            }
        }

        return inverse;
    }

    public static int[][] minor(int[][] matrix, int row, int col) {
        int[][] minorMatrix = new int[matrix.length - 1][matrix[0].length - 1];
        int r = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (i == row) {
                continue;
            }
            int c = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                if (j == col) {
                    continue;
                }
                minorMatrix[r][c] = matrix[i][j];
                c++;
            }
            r++;
        }
        return minorMatrix;
    }

    public static int determinant(int[][] matrix, int modulo) {
        int n = matrix.length;
        if (n != matrix[0].length) {
            throw new IllegalArgumentException("Matrix is not square.");
        }
        if (n == 1) {
            return matrix[0][0];
        }
        if (n == 2) {
            return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % modulo;
        }
        int det = 0;
        for (int j = 0; j < n; j++) {
            int[][] minorMatrix = minor(matrix, 0, j);
            int minorDet = determinant(minorMatrix, modulo);
            int cofactor = (int) Math.pow(-1, 0 + j) * minorDet;
            det = (det + matrix[0][j] * cofactor) % modulo;
        }
        return (det + modulo) % modulo;
    }

    public static int modInverse(int a, int modulo) {
        for (int x = 1; x < modulo; x++) {
            if ((a * x) % modulo == 1) {
                return x;
            }
        }
        throw new IllegalArgumentException("Modular inverse does not exist.");
    }
}
