import java.util.*;

public class PlayfairCipher {

    static String prepareText(String text) {
        StringBuilder preparedText = new StringBuilder(text);
        for (int i = 0; i < preparedText.length() - 1; i += 2) {
            if (preparedText.charAt(i) == preparedText.charAt(i + 1)) {
                preparedText.insert(i + 1, 'x');
            }
        }
        if (preparedText.length() % 2 != 0) {
            preparedText.append('x');
        }
        return preparedText.toString();
    }

    static String processDigraph(char[][] keyTable, char a, char b, int direction) {
        int[] aIndex = new int[2];
        int[] bIndex = new int[2];

        // Find indices of characters a and b in the key table
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keyTable[i][j] == a) {
                    aIndex[0] = i;
                    aIndex[1] = j;
                }
                if (keyTable[i][j] == b) {
                    bIndex[0] = i;
                    bIndex[1] = j;
                }
            }
        }

        StringBuilder result = new StringBuilder();

        if (aIndex[0] == bIndex[0]) {  // Same row
            result.append(keyTable[aIndex[0]][(aIndex[1] + direction) % 5]);
            result.append(keyTable[bIndex[0]][(bIndex[1] + direction) % 5]);
        } else if (aIndex[1] == bIndex[1]) {  // Same column
            result.append(keyTable[(aIndex[0] + direction) % 5][aIndex[1]]);
            result.append(keyTable[(bIndex[0] + direction) % 5][bIndex[1]]);
        } else {  // Rectangle
            result.append(keyTable[aIndex[0]][bIndex[1]]);
            result.append(keyTable[bIndex[0]][aIndex[1]]);
        }

        return result.toString();
    }

    static String encrypt(String text, char[][] keyTable) {
        StringBuilder encryptedText = new StringBuilder();
        String preparedText = prepareText(text);

        for (int i = 0; i < preparedText.length(); i += 2) {
            encryptedText.append(processDigraph(keyTable, preparedText.charAt(i), preparedText.charAt(i + 1), 1));
        }

        return encryptedText.toString();
    }

    static String decrypt(String text, char[][] keyTable) {
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            decryptedText.append(processDigraph(keyTable, text.charAt(i), text.charAt(i + 1), 4));
        }

        return decryptedText.toString();
    }

    static char[][] generateKeyTable(String key) {
        char[][] keyTable = new char[5][5];
        String keyWithoutDuplicates = "";

        for (char c : key.toCharArray()) {
            if (keyWithoutDuplicates.indexOf(c) == -1) {
                keyWithoutDuplicates += c;
            }
        }

        String alphabet = "abcdefghiklmnopqrstuvwxyz";
        int keyIndex = 0;
        int alphaIndex = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keyIndex < keyWithoutDuplicates.length()) {
                    keyTable[i][j] = keyWithoutDuplicates.charAt(keyIndex++);
                } else {
                    while (keyWithoutDuplicates.indexOf(alphabet.charAt(alphaIndex)) != -1) {
                        alphaIndex++;
                    }
                    keyTable[i][j] = alphabet.charAt(alphaIndex++);
                }
            }
        }

        return keyTable;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the key text: ");
        String keyText = sc.nextLine();
        keyText.toLowerCase();

        char[][] keyTable = generateKeyTable(keyText);

        System.out.print("Enter 'E' for encryption or 'D' for decryption: ");
        char choice = sc.nextLine().toUpperCase().charAt(0);

        System.out.print("Enter the text: ");
        String text = sc.nextLine();
        text.toLowerCase();

        if (choice == 'E') {
            String encryptedText = encrypt(text, keyTable);
            System.out.println("Ciphertext: " + encryptedText);
        } else if (choice == 'D') {
            String decryptedText = decrypt(text, keyTable);
            System.out.println("Plaintext: " + decryptedText);
        } else {
            System.out.println("Invalid choice. Please enter 'E' or 'D'.");
        }

    }
}
