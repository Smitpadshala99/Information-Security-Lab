import java.util.Scanner;

public class vigenere_cipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);        
        System.out.print("Enter 'E' for encryption or 'D' for decryption: ");
        char choice = scanner.nextLine().toUpperCase().charAt(0);
        
        if (choice == 'E') {
            performEncryption(scanner);
        } else if (choice == 'D') {
            performDecryption(scanner);
        } else {
            System.out.println("Invalid choice. Please enter 'E' for encryption or 'D' for decryption.");
        }
        
        scanner.close();
    }
    
    public static void performEncryption(Scanner scanner) {
        System.out.print("Enter the plaintext: ");
        String plaintext = scanner.nextLine().toUpperCase();
        
        System.out.print("Enter the keyword: ");
        String keyword = scanner.nextLine().toUpperCase();
        
        String key = generateKey(plaintext, keyword);
        String cipherText = cipherText(plaintext, key);
        
        System.out.println("Key: " + key);
        System.out.println("CipherText: " + cipherText);
    }
    
    public static void performDecryption(Scanner scanner) {
        System.out.print("Enter the ciphertext: ");
        String cipherText = scanner.nextLine().toUpperCase();
        
        System.out.print("Enter the keyword: ");
        String keyword = scanner.nextLine().toUpperCase();
        
        String key = generateKey(cipherText, keyword);
        String originalText = originalText(cipherText, key);
        
        System.out.println("Key: " + key);
        System.out.println("Original/Decrypted Text: " + originalText);
    }
    
    public static String generateKey(String string, String key) {
        StringBuilder generatedKey = new StringBuilder();
        int keyLength = key.length();
        
        for (int i = 0; i < string.length(); i++) {
            generatedKey.append(key.charAt(i % keyLength));
        }
        
        return generatedKey.toString();
    }
    
    public static String cipherText(String string, String key) {
        StringBuilder cipherText = new StringBuilder();
        
        for (int i = 0; i < string.length(); i++) {
            char plainChar = string.charAt(i);
            char keyChar = key.charAt(i);
            int encryptedChar = (plainChar + keyChar - 2 * 'A') % 26 + 'A';
            cipherText.append((char) encryptedChar);
        }
        
        return cipherText.toString();
    }
    
    public static String originalText(String cipherText, String key) {
        StringBuilder originalText = new StringBuilder();
        
        for (int i = 0; i < cipherText.length(); i++) {
            char cipherChar = cipherText.charAt(i);
            char keyChar = key.charAt(i);
            int decryptedChar = (cipherChar - keyChar + 26) % 26 + 'A';
            originalText.append((char) decryptedChar);
        }
        
        return originalText.toString();
    }
}
