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

n = 2
key_input = "hill"  

plaintext = "Smit"  

key = [ord(char) - ord('a') for char in key_input]
ciphertext = encrypt(plaintext, key)
print("Ciphertext:", ciphertext)

decrypted_text = decrypt(ciphertext, key)
print("Decrypted text:", decrypted_text)
