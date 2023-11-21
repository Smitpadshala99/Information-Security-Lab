import numpy as np
from sympy import Matrix

def prepare_text(text, n):
    text = text.lower()
    text = "".join(c for c in text if c.isalpha())
    padding = n - len(text) % n
    if padding != n:
        text += 'z' * padding
    return [ord(char) - ord('a') for char in text]

def encrypt(text, key):
    key = np.array(key).reshape(-1, len(key) // n)
    text = prepare_text(text, len(key))
    encrypted_text = []
    for i in range(0, len(text), len(key)):
        block = np.array(text[i:i + len(key)])
        encrypted_block = np.dot(key, block) % 26
        encrypted_text.extend(encrypted_block)
    return ''.join(chr(char + ord('a')) for char in encrypted_text)

def decrypt(text, key):
    key = np.array(key).reshape(-1, len(key) // n)
    key_inverse = Matrix(key).inv_mod(26)
    text = prepare_text(text, len(key))
    decrypted_text = []
    for i in range(0, len(text), len(key)):
        block = np.array(text[i:i + len(key)])
        decrypted_block = np.dot(key_inverse, block) % 26
        decrypted_text.extend(decrypted_block)
    return ''.join(chr(char + ord('a')) for char in decrypted_text)

n = int(input("Enter the size of the key matrix (n x n): "))
key_input = input(f"Enter the {n}x{n} key matrix as a single string: ").lower()
key = [ord(char) - ord('a') for char in key_input]

while True:
    choice = input("Choose an operation (e for encrypt/d for decrypt/exit): ").lower()

    if choice == 'e':
        plaintext = input("Enter the plaintext: ")
        ciphertext = encrypt(plaintext, key)
        print("Ciphertext:", ciphertext)
        break

    elif choice == 'd':
        ciphertext = input("Enter the ciphertext: ")
        plaintext = decrypt(ciphertext, key)
        print("Decrypted text:", plaintext)
        break

    elif choice == 'exit':
        break

    else:
        print("Invalid choice. Please choose 'encrypt', 'decrypt', or 'exit'.")
