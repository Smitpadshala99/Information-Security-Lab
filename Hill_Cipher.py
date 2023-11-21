def matrix_multiply(matrix1, matrix2):
    result = []
    for i in range(len(matrix1)):
        row = []
        for j in range(len(matrix2[0])):
            product = 0
            for k in range(len(matrix2)):
                product += matrix1[i][k] * matrix2[k][j]
            row.append(product % 26)
        result.append(row)
    return result

def validate_key(key_matrix):
    determinant = (key_matrix[0][0] * key_matrix[1][1]) - (key_matrix[0][1] * key_matrix[1][0])
    determinant %= 26
    if determinant == 0:
        return False
    return True

def hill_cipher(text, key_matrix, mode='encrypt'):
    text = text.upper().replace(" ", "")
    
    if not validate_key(key_matrix):
        raise ValueError("Invalid key. Make sure it's a non-singular integer matrix.")

    n = len(key_matrix)
    if len(text) % n != 0:
        raise ValueError(f"The length of the text must be a multiple of {n}.")

    result = []
    alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'

    if mode == 'decrypt':
        determinant = (key_matrix[0][0] * key_matrix[1][1]) - (key_matrix[0][1] * key_matrix[1][0])
        determinant %= 26
        inverse_det = None

        for i in range(26):
            if (determinant * i) % 26 == 1:
                inverse_det = i
                break
        
        if inverse_det is None:
            raise ValueError("The key is not invertible in the modulo 26 field.")

        key_matrix[0][0], key_matrix[1][1] = key_matrix[1][1], key_matrix[0][0]
        key_matrix[0][1] *= -1
        key_matrix[1][0] *= -1

        for i in range(n):
            for j in range(n):
                key_matrix[i][j] = (key_matrix[i][j] * inverse_det) % 26

    for i in range(0, len(text), n):
        block = text[i:i + n]
        block_vector = [alphabet.index(letter) for letter in block]
        
        if mode == 'encrypt':
            result_matrix = matrix_multiply([block_vector], key_matrix)
        elif mode == 'decrypt':
            result_matrix = matrix_multiply([block_vector], key_matrix)

        result += [alphabet[val] for val in result_matrix[0]] #type: ignore 

    return ''.join(result)

def main():
    print("Hill Cipher")
    while True:
        choice = input("Choose an option (E)ncrypt, (D)ecrypt, (Q)uit: ").strip().upper()
        if choice == "Q":
            break
        elif choice == "E" or choice == "D":
            text = input("Enter the text: ").strip()
            key_str = input("Enter the key matrix as space-separated integers (e.g., '3 2 2 5' for a 2x2 matrix): ").strip()
            key = [int(x) for x in key_str.split()]
            key_matrix = [[key[0], key[1]], [key[2], key[3]]]

            if choice == "E":
                result = hill_cipher(text, key_matrix, 'encrypt')
                print("Encrypted text:", result)
            else:
                result = hill_cipher(text, key_matrix, 'decrypt')
                print("Decrypted text:", result)
        else:
            print("Invalid option. Please choose 'E', 'D', or 'Q'.")

if __name__ == "__main__":
    main()
