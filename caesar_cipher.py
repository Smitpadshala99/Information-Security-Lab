def get_shift_value(char):
    return ord(char.upper()) - ord('A')

def encrypt(text, shift):
    key = get_shift_value(shift)
    encrypted_text = ""

    for char in text:
        if char.isalpha():
            base = ord('A') if char.isupper() else ord('a')
            encrypted_char = chr((ord(char) - base + key) % 26 + base)
            encrypted_text += encrypted_char
        else:
            encrypted_text += char

    return encrypted_text

def decrypt(encrypted_text, shift):
    key = get_shift_value(shift)
    decrypted_text = ""

    for char in encrypted_text:
        if char.isalpha():
            base = ord('A') if char.isupper() else ord('a')
            decrypted_char = chr((ord(char) - base - key + 26) % 26 + base)
            decrypted_text += decrypted_char
        else:
            decrypted_text += char

    return decrypted_text

if __name__ == "__main__":
    text = input("Enter the text: ")
    shift = input("Enter the shift value (alphabet character): ")

    if len(shift) != 1 or not shift.isalpha():
        print("Invalid shift value. Please enter a single alphabet character.")
    else:
        choice = input("Encrypt or Decrypt? (E/D): ").upper()

        if choice == "E":
            encrypted_text = encrypt(text, shift)
            print("Encrypted text:", encrypted_text)
        elif choice == "D":
            decrypted_text = decrypt(text, shift)
            print("Decrypted text:", decrypted_text)
        else:
            print("Invalid choice. Please enter 'E' for encrypt or 'D' for decrypt.")
