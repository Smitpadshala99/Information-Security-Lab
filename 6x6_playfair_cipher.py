def sanitize_str(s):
    return ''.join(filter(str.isalnum, s)).upper()

def make_grid(keyword):
    keyword = sanitize_str(keyword)
    charset = [c for c in keyword]
    
    for c in "0123456789":
        if c not in charset:
            charset.append(c)

    for c in "ABCDEFGHIJKLMNOPQRSTUVWXYZ":
        if c not in charset:
            charset.append(c)

    grid = [charset[i:i+6] for i in range(0, 36, 6)]
    return grid


def find_char(grid, c):
    for i, row in enumerate(grid):
        if c in row:
            return i, row.index(c)
    raise ValueError(f"Char '{c}' not found in grid!")

def run_playfair(message, key, decode):
    grid = make_grid(key)
    print("Grid:")
    for row in grid:
        print(" ".join(row))
    
    message = sanitize_str(message)
    if len(message) % 2 != 0:
        message += 'X'

    shift = -1 if decode else 1

    result = []
    i = 0
    while i < len(message):
        c1, c2 = message[i], message[i+1]
        row1, col1 = find_char(grid, c1)
        row2, col2 = find_char(grid, c2)

        if row1 == row2:
            new_c1 = grid[row1][(col1 + shift) % 6]
            new_c2 = grid[row2][(col2 + shift) % 6]
        elif col1 == col2:
            new_c1 = grid[(row1 + shift) % 6][col1]
            new_c2 = grid[(row2 + shift) % 6][col2]
        else:
            new_c1 = grid[row1][col2]
            new_c2 = grid[row2][col1]

        result.extend([new_c1, new_c2])
        i += 2

    return " ".join(result)

def main():
    decode = True
    while True:
        choice = input("Choose an operation (e for encrypt/d for decrypt/exit): ").lower()

        if choice == 'e':
            decode = False
            message = input("Enter the plaintext: ")
            key = input("Enter the key: ")
            new_message = run_playfair(message, key, decode)
            print()
            print(f"Encoded message: {new_message}")
            break

        elif choice == 'd':
            decode = True
            message = input("Enter the ciphertext: ")
            key = input("Enter the key: ")
            new_message = run_playfair(message, key, decode)
            print()
            print(f"Decoded message: {new_message}")
            break

        elif choice == 'exit':
            break

        else:
            print("Invalid choice. Please choose 'encrypt', 'decrypt', or 'exit.")

if __name__ == "__main__":
    main()
