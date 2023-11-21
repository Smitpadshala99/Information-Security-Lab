import java.util.Scanner;

public class hill_cipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hill Cipher");
        while (true) {
            System.out.print("Choose an option (E)ncrypt, (D)ecrypt, (Q)uit: ");
            String choice = scanner.nextLine().trim().toUpperCase();

            if (choice.equals("Q")) {
                break;
            } else if (choice.equals("E") || choice.equals("D")) {
                System.out.print("Enter the text (uppercase letters only): ");
                String text = scanner.nextLine().trim().toUpperCase();

                System.out.print("Enter the key (9 characters - 3x3 matrix, no spaces): ");
                String key = scanner.nextLine().trim().toUpperCase();

                if (isValidInput(text, key)) {
                    if (choice.equals("E")) {
                        String encryptedText = encrypt(text, key);
                        System.out.println("Encrypted text: " + encryptedText);
                    } else {
                        String decryptedText = decrypt(text, key);
                        System.out.println("Decrypted text: " + decryptedText);
                    }
                } else {
                    System.out.println("Invalid input. Text and key should be uppercase letters.");
                }
            } else {
                System.out.println("Invalid option. Please choose 'E', 'D', or 'Q'.");
            }
        }

        scanner.close();
    }

    public static boolean isValidInput(String text, String key) {
        return text.matches("^[A-Z]+$") && key.matches("^[A-Z]{9}$");
    }

    public static String encrypt(String text, String key) {
        int[][] keyMatrix = new int[3][3];
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                keyMatrix[i][j] = (key.charAt(k++) % 65);
            }
        }

        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < text.length(); i += 3) {
            String block = text.substring(i, i + 3);
            int[][] blockVector = new int[3][1];
            for (int j = 0; j < 3; j++) {
                blockVector[j][0] = (block.charAt(j) % 65);
            }
            int[][] cipherMatrix = new int[3][1];

            for (int j = 0; j < 3; j++) {
                cipherMatrix[j][0] = 0;
                for (int x = 0; x < 3; x++) {
                    cipherMatrix[j][0] += keyMatrix[j][x] * blockVector[x][0];
                }
                cipherMatrix[j][0] = cipherMatrix[j][0] % 26;
            }

            for (int j = 0; j < 3; j++) {
                ciphertext.append((char) (cipherMatrix[j][0] + 65));
            }
        }

        return ciphertext.toString();
    }

    public static String decrypt(String text, String key) {
        int[][] keyMatrix = new int[3][3];
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                keyMatrix[i][j] = (key.charAt(k++) % 65);
            }
        }

        int[][] inverseKeyMatrix = inverseMatrix(keyMatrix);

        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < text.length(); i += 3) {
            String block = text.substring(i, i + 3);
            int[][] blockVector = new int[3][1];
            for (int j = 0; j < 3; j++) {
                blockVector[j][0] = (block.charAt(j) % 65);
            }
            int[][] plainMatrix = new int[3][1];

            for (int j = 0; j < 3; j++) {
                plainMatrix[j][0] = 0;
                for (int x = 0; x < 3; x++) {
                    plainMatrix[j][0] += inverseKeyMatrix[j][x] * blockVector[x][0];
                }
                plainMatrix[j][0] = (plainMatrix[j][0] + 26) % 26; // Ensure positive value
            }

            for (int j = 0; j < 3; j++) {
                plaintext.append((char) (plainMatrix[j][0] + 65));
            }
        }

        return plaintext.toString();
    }

    public static int[][] inverseMatrix(int[][] matrix) {
        int det = (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % 26;
        int detInverse = -1;
        for (int i = 1; i < 26; i++) {
            if ((det * i) % 26 == 1) {
                detInverse = i;
                break;
            }
        }

        int[][] inverse = new int[3][3];
        inverse[0][0] = (matrix[1][1] * detInverse) % 26;
        inverse[0][1] = (-matrix[0][1] * detInverse + 26) % 26;
        inverse[1][0] = (-matrix[1][0] * detInverse + 26) % 26;
        inverse[1][1] = (matrix[0][0] * detInverse) % 26;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                inverse[i][j] = (inverse[i][j] + 26) % 26; // Ensure positive values
            }
        }

        return inverse;
    }
}
