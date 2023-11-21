def generateKey(string, key):
    key = list(key)
    if len(string) == len(key):
        return key
    else:
        for i in range(len(string) - len(key)):
            key.append(key[i % len(key)])
    return "".join(key)

def cipherText(string, key):
    cipher_text = []
    for i in range(len(string)):
        x = (ord(string[i]) + ord(key[i])) % 26
        x += ord('A')
        cipher_text.append(chr(x))
    return "".join(cipher_text)

def originalText(cipher_text, key):
    orig_text = []
    for i in range(len(cipher_text)):
        x = (ord(cipher_text[i]) - ord(key[i]) + 26) % 26
        x += ord('A')
        orig_text.append(chr(x))
    return "".join(orig_text)

def main():
    while True:
        choice = input("Choose an option (E)ncrypt, (D)ecrypt, (Q)uit: ").upper()
        if choice == "Q":
            break
        elif choice == "E":
            string = input("Enter the plaintext: ").upper()
            keyword = input("Enter the keyword: ").upper()

            key = generateKey(string, keyword)
            cipher_text = cipherText(string, key)

            print("Ciphertext:", cipher_text)
        elif choice == "D":
            cipher_text = input("Enter the ciphertext: ").upper()
            keyword = input("Enter the keyword: ").upper()

            key = generateKey(cipher_text, keyword)
            orig_text = originalText(cipher_text, key)

            print("Original/Decrypted Text:", orig_text)
        else:
            print("Invalid option. Please choose 'E', 'D', or 'Q'.")

if __name__ == "__main__":
    main()
