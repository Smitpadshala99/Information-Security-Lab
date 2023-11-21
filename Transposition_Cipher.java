import java.util.*;

public class Transposition_Cipher {

    // Function to encrypt the message using the transposition cipher and return the new message
    public static String encrypt(String message, int key) {
        int rows = (int) Math.ceil((double) message.length() / key);
        char[][] grid = new char[rows][key];

        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < key; j++) {
                if (k < message.length()) {
                    grid[i][j] = message.charAt(k);
                    k++;
                }
            }
        }

        StringBuilder encryptedMessage = new StringBuilder();
        for (int j = 0; j < key; j++) {
            for (int i = 0; i < rows; i++) {
                if (grid[i][j] != 0) {
                    encryptedMessage.append(grid[i][j]);
                }
            }
        }

        return encryptedMessage.toString();
    }

    // Function to decrypt the encrypted message using the transposition cipher and return the new message
    public static String decrypt(String encryptedMessage, int key) {
        int rows = (int) Math.ceil((double) encryptedMessage.length() / key);
        char[][] grid = new char[rows][key];

        int k = 0;
        for (int j = 0; j < key; j++) {
            for (int i = 0; i < rows; i++) {
                if (k < encryptedMessage.length()) {
                    grid[i][j] = encryptedMessage.charAt(k);
                    k++;
                }
            }
        }

        StringBuilder decryptedMessage = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < key; j++) {
                if (grid[i][j] != 0) {
                    decryptedMessage.append(grid[i][j]);
                }
            }
        }

        return decryptedMessage.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a String: ");
        String message = sc.nextLine();
        System.out.println("Enter a key value: ");
        int key = sc.nextInt();
        sc.nextLine(); // Consume the newline character left by nextInt()

        System.out.print("Encrypt or Decrypt? (E/D): ");
        String choice = sc.nextLine().toUpperCase();

        if (choice.equals("E")) {
            String encryptedMessage = encrypt(message, key);
            System.out.println("Encrypted message: " + encryptedMessage);
        } else if (choice.equals("D")) {
            String decryptedMessage = decrypt(message, key);
            System.out.println("Decrypted message: " + decryptedMessage);
        } else {
            System.out.println("Invalid choice. Please enter 'E' for encrypt or 'D' for decrypt.");
        }
    }
}
