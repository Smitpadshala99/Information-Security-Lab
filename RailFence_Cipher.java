import java.util.Arrays;
import java.util.Scanner;

public class RailFence_Cipher {

    public static String encrypt(String plainText, int key) {
        char[][] rail = new char[key][plainText.length()];
 
        for (int i = 0; i < key; i++)
            Arrays.fill(rail[i], '\n');
 
        boolean dirDown = false;
        int row = 0, col = 0;
 
        for (int i = 0; i < plainText.length(); i++) {
 
            if (row == 0 || row == key - 1)
                dirDown = !dirDown;
 
            rail[row][col++] = plainText.charAt(i);
 
            if (dirDown)
                row++;
            else
                row--;
        }
 
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < key; i++)
            for (int j = 0; j < plainText.length(); j++)
                if (rail[i][j] != '\n')
                    encrypted.append(rail[i][j]);
 
        printRailFencePattern(rail);
        return encrypted.toString();
    }

    public static String decrypt(String cipherText, int key) {
        char[][] rail = new char[key][cipherText.length()];
 
        for (int i = 0; i < key; i++)
            Arrays.fill(rail[i], '\n');
 
        boolean dirDown = true;
 
        int row = 0, col = 0;
 
        for (int i = 0; i < cipherText.length(); i++) {
            if (row == 0)
                dirDown = true;
            if (row == key - 1)
                dirDown = false;
 
            rail[row][col++] = '*';
 
            if (dirDown)
                row++;
            else
                row--;
        }
 
        int index = 0;
        for (int i = 0; i < key; i++)
            for (int j = 0; j < cipherText.length(); j++)
                if (rail[i][j] == '*'
                    && index < cipherText.length())
                    rail[i][j] = cipherText.charAt(index++);
 
        StringBuilder decrypted = new StringBuilder();
 
        row = 0;
        col = 0;
        for (int i = 0; i < cipherText.length(); i++) {
            if (row == 0)
                dirDown = true;
            if (row == key - 1)
                dirDown = false;
 
            if (rail[row][col] != '*')
                decrypted.append(rail[row][col++]);
 
            if (dirDown)
                row++;
            else
                row--;
        }
        printRailFencePattern(rail);
        return decrypted.toString();
    }

    public static void printRailFencePattern(char[][] rail) {
        for (int i = 0; i < rail.length; i++) {
            for (int j = 0; j < rail[i].length; j++) {
                System.out.print(rail[i][j] == '\n' ? "*" : rail[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter plain text: ");
        String plainText = sc.nextLine();
        plainText.toLowerCase();

        System.out.print("Enter key(int): ");
        int key = sc.nextInt();
        sc.nextLine();
        
        System.out.print("Enter 'E' for encryption or 'D' for decryption: ");
        char choice = sc.nextLine().toUpperCase().charAt(0);

        if (choice == 'E') {
            String encryptedMessage = encrypt(plainText, key);
            System.out.println("\nEncrypted Message: ");
            System.out.println(encryptedMessage);
        } else if (choice == 'D') {
            String decryptedMessage = decrypt(plainText, key);
            System.out.println("\nDecrypted Message: ");
            System.out.println(decryptedMessage);
        } else {
            System.out.println("Invalid choice. Please enter 'E' or 'D'.");
        }
    }
}
