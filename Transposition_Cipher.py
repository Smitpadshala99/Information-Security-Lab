import math


def encrypt(message, key):
    rows = math.ceil((len(message) / key))  
    grid = [['' for _ in range(key)] for _ in range(rows)]

    k = 0
    for i in range(rows):
        for j in range(key):
            if k < len(message):
                grid[i][j] = message[k]
                k += 1

    encrypted_message = ''.join(grid[j][i] for i in range(key) for j in range(rows))
    return encrypted_message


def decrypt(encrypted_message, key):
    rows = math.ceil((len(encrypted_message) / key))  

    grid = [['' for _ in range(key)] for _ in range(rows)]

    k = 0
    for j in range(key):
        for i in range(rows):
            if k < len(encrypted_message):
                grid[i][j] = encrypted_message[k]
                k += 1

    decrypted_message = ''.join(grid[i][j] for i in range(rows) for j in range(key))
    return decrypted_message


if __name__ == "__main__":
    message = input("Enter a String: ")
    key = int(input("Enter a key value: "))

    choice = input("Encrypt or Decrypt? (E/D): ").upper()

    if choice == "E":
        encrypted_message = encrypt(message, key)
        print("Encrypted message:", encrypted_message)
    elif choice == "D":
        decrypted_message = decrypt(message, key)
        print("Decrypted message:", decrypted_message)
    else:
        print("Invalid choice. Please enter 'E' for encrypt or 'D' for decrypt.")
