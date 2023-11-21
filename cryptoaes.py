from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.primitives.ciphers.aead import AESGCM
import os

def generate_key():
    # Generate a random 256-bit key
    key = os.urandom(32)
    return key

def generate_nonce():
    # Generate a random 96-bit nonce (IV)
    nonce = os.urandom(12)
    return nonce

def encrypt_aes(plaintext, key, nonce):
    cipher = Cipher(algorithms.AES(key), modes.GCM(nonce))
    encryptor = cipher.encryptor()
    ciphertext = encryptor.update(plaintext) + encryptor.finalize()
    return (ciphertext, encryptor.tag)

def decrypt_aes(ciphertext, key, nonce, tag):
    cipher = Cipher(algorithms.AES(key), modes.GCM(nonce, tag))
    decryptor = cipher.decryptor()
    plaintext = decryptor.update(ciphertext) + decryptor.finalize()
    return plaintext

def main():
    print("AES-GCM Encryption and Decryption")
    plaintext = input("Enter your plaintext: ").encode()

    key = generate_key()
    nonce = generate_nonce()

    ciphertext, tag = encrypt_aes(plaintext, key, nonce)
    decrypted_text = decrypt_aes(ciphertext, key, nonce, tag)

    print("\nPlaintext:", plaintext.decode())
    print("Ciphertext:", ciphertext.hex())
    print("Decrypted Text:", decrypted_text.decode())

if __name__ == "__main__":
    main()
