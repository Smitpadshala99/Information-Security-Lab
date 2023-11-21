def encrypt(plain_text, key):
    rail = [['\n' for _ in range(len(plain_text))] for _ in range(key)]

    dir_down = False
    row, col = 0, 0

    for char in plain_text:
        if row == 0 or row == key - 1:
            dir_down = not dir_down

        rail[row][col] = char
        col += 1

        if dir_down:
            row += 1
        else:
            row -= 1

    encrypted = ''.join(char for row in rail for char in row if char != '\n')

    print_rail_fence_pattern(rail)
    return encrypted


def decrypt(cipher_text, key):
    rail = [['\n' for _ in range(len(cipher_text))] for _ in range(key)]

    dir_down = True
    row, col = 0, 0

    for _ in range(len(cipher_text)):
        if row == 0:
            dir_down = True
        if row == key - 1:
            dir_down = False

        rail[row][col] = '*'
        col += 1

        if dir_down:
            row += 1
        else:
            row -= 1

    index = 0
    for i in range(key):
        for j in range(len(cipher_text)):
            if rail[i][j] == '*' and index < len(cipher_text):
                rail[i][j] = cipher_text[index]
                index += 1

    decrypted = ''
    row, col = 0, 0

    for _ in range(len(cipher_text)):
        if row == 0:
            dir_down = True
        if row == key - 1:
            dir_down = False

        if rail[row][col] != '*':
            decrypted += rail[row][col]
            col += 1

        if dir_down:
            row += 1
        else:
            row -= 1

    print_rail_fence_pattern(rail)
    return decrypted


def print_rail_fence_pattern(rail):
    for row in rail:
        for char in row:
            print('*' if char == '\n' else char, end='')
        print()


if __name__ == "__main__":
    plain_text = input("Enter plain text: ").lower()
    key = int(input("Enter key(int): "))
    choice = input("Enter 'E' for encryption or 'D' for decryption: ").upper()

    if choice == 'E':
        encrypted_message = encrypt(plain_text, key)
        print("\nEncrypted Message:")
        print(encrypted_message)
    elif choice == 'D':
        decrypted_message = decrypt(plain_text, key)
        print("\nDecrypted Message:")
        print(decrypted_message)
    else:
        print("Invalid choice. Please enter 'E' or 'D'.")
