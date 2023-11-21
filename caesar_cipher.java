import java.util.Scanner;

public class caesar_cipher {

    public static int getShiftValue(char ch) {
        if (Character.isUpperCase(ch)) {
            return ch - 'A';
        } else {
            return ch - 'a';
        }
        
    }

    public static String encrypt(String text, char shift) {
        int key = getShiftValue(shift);
        StringBuilder encryptedText = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                char encryptedChar = (char) ((c - base + key) % 26 + base);
                encryptedText.append(encryptedChar);
            } else {
                encryptedText.append(c);
            }
        }

        return encryptedText.toString();
    }

    public static String decrypt(String encryptedText, char shift) {
        int key = getShiftValue(shift);
        StringBuilder decryptedText = new StringBuilder();

        for (char c : encryptedText.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                char decryptedChar = (char) ((c - base - key + 26) % 26 + base);
                decryptedText.append(decryptedChar);
            } else {
                decryptedText.append(c);
            }
        }

        return decryptedText.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the text: ");
        String text = sc.nextLine();

        System.out.print("Enter the shift value (alphabet character): ");
        char shift = sc.nextLine().charAt(0);

        System.out.print("Encrypt or Decrypt? (E/D): ");
        String choice = sc.nextLine().toUpperCase();

        if (choice.equals("E")) {
            String encryptedText = encrypt(text, shift);
            System.out.println("Encrypted text: " + encryptedText);
        } else if (choice.equals("D")) {
            String decryptedText = decrypt(text, shift);
            System.out.println("Decrypted text: " + decryptedText);
        } else {
            System.out.println("Invalid choice. Please enter 'E' for encrypt or 'D' for decrypt.");
        }

    }
}
