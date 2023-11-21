def generate_key_table(key):
    key_table = [['' for _ in range(5)] for _ in range(5)]
    key_without_duplicates = ''.join(sorted(set(key), key=key.index))

    alphabet = "abcdefghiklmnopqrstuvwxyz"
    key_index = 0
    alpha_index = 0

    for i in range(5):
        for j in range(5):
            if key_index < len(key_without_duplicates):
                key_table[i][j] = key_without_duplicates[key_index]
                key_index += 1
            else:
                while alphabet[alpha_index] in key_without_duplicates:
                    alpha_index += 1
                key_table[i][j] = alphabet[alpha_index]
                alpha_index += 1

    return key_table

def prepare_text(text):
    prepared_text = list(text)
    i = 0
    while i < len(prepared_text) - 1:
        if prepared_text[i] == prepared_text[i + 1]:
            prepared_text.insert(i + 1, 'x')
        i += 2
    if len(prepared_text) % 2 != 0:
        prepared_text.append('x')
    return ''.join(prepared_text)

def process_digraph(key_table, a, b, direction):
    a_index = [-1, -1]
    b_index = [-1, -1]

    # Find indices of characters a and b in the key table
    for i in range(5):
        for j in range(5):
            if key_table[i][j] == a:
                a_index[0] = i
                a_index[1] = j
            if key_table[i][j] == b:
                b_index[0] = i
                b_index[1] = j

    result = []

    if a_index[0] == b_index[0]:  # Same row
        result.append(key_table[a_index[0]][(a_index[1] + direction) % 5])
        result.append(key_table[b_index[0]][(b_index[1] + direction) % 5])
    elif a_index[1] == b_index[1]:  # Same column
        result.append(key_table[(a_index[0] + direction) % 5][a_index[1]])
        result.append(key_table[(b_index[0] + direction) % 5][b_index[1]])
    else:  # Rectangle
        result.append(key_table[a_index[0]][b_index[1]])
        result.append(key_table[b_index[0]][a_index[1]])

    return ''.join(result)

def encrypt(text, key_table):
    encrypted_text = []
    prepared_text = prepare_text(text)

    for i in range(0, len(prepared_text), 2):
        encrypted_text.append(process_digraph(key_table, prepared_text[i], prepared_text[i + 1], 1))

    return ''.join(encrypted_text)

def decrypt(text, key_table):
    decrypted_text = []

    for i in range(0, len(text), 2):
        decrypted_text.append(process_digraph(key_table, text[i], text[i + 1], 4))

    return ''.join(decrypted_text)

def main():
    key_text = input("Enter the key text: ").lower().replace("[^a-zA-Z]", "")
    key_table = generate_key_table(key_text)

    choice = input("Enter 'E' for encryption or 'D' for decryption: ").upper()
    
    text = input("Enter the text: ").lower().replace("[^a-zA-Z]", "")

    if choice == 'E':
        encrypted_text = encrypt(text, key_table)
        print("Ciphertext:", encrypted_text)
    elif choice == 'D':
        decrypted_text = decrypt(text, key_table)
        print("Plaintext:", decrypted_text)
    else:
        print("Invalid choice. Please enter 'E' or 'D'.")

if __name__ == "__main__":
    main()
