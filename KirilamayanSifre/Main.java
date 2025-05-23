/**********************************/
/* Ödev 2 - Kırılamayan Şifreleme */
/* Vigenere Şifreleme Algoritması */
/*****  Ömer Ekmen - 18504269 *****/
/**********************************/

import java.util.*;

public class Main {
    private static final char[] ALPHABET = {
            'a', 'b', 'c', 'ç', 'd', 'e', 'f', 'g', 'ğ', 'h', 'ı', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'ö', 'p', 'r',
            's', 'ş', 't', 'u', 'ü', 'v', 'y', 'z'
    };

    private static final int ALPHABET_SIZE = ALPHABET.length;
    private static char[][] vigenereSquare;
    private static String vigenereKey;

    public static void main(String[] args) {
        generateVigenereSquare();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Lütfen bir anahtar giriniz:");
            vigenereKey = OnCalisma(scanner.nextLine());
            System.out.println("---------------------------------------------------------");
            System.out.println("Lütfen bir metin giriniz:");
            String input = scanner.nextLine();
            System.out.println("---------------------------------------------------------");

            String processedInput = OnCalisma(input);

            System.out.println("---------------------------------------------------------");

            System.out.println("Orjinal metin: " + input);
            System.out.println("İşlenmiş metin: " + processedInput);

            System.out.println("---------------------------------------------------------");
            System.out.println("Anahtar: " + vigenereKey);
            System.out.println("---------------------------------------------------------");
            System.out.println("Vigenère Alfabesi: \n");
            System.out.print("  | ");
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                System.out.print(Character.toUpperCase(ALPHABET[i]) + " ");
            }
            System.out.println();
            System.out.print("--+");
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                System.out.print("--");
            }
            System.out.println();
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                System.out.print(Character.toUpperCase(ALPHABET[i]) + " | ");
                for (int j = 0; j < ALPHABET_SIZE; j++) {
                    System.out.print(vigenereSquare[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println("---------------------------------------------------------");

            String encrypted = encrypt(processedInput, vigenereKey);
            String decrypted = decrypt(encrypted, vigenereKey);

            System.out.println("Encrypted: " + encrypted);
            System.out.println("Decrypted: " + decrypted);
        }
    }

    public static String OnCalisma(String input) {
        input = input.replaceAll("\\s+", "");
        input = input.toLowerCase();
        input = input.replaceAll("0", "sıfır")
                .replaceAll("1", "bir")
                .replaceAll("2", "iki")
                .replaceAll("3", "üç")
                .replaceAll("4", "dört")
                .replaceAll("5", "beş")
                .replaceAll("6", "altı")
                .replaceAll("7", "yedi")
                .replaceAll("8", "sekiz")
                .replaceAll("9", "dokuz");

        input = input.replaceAll("\\p{Punct}", "");

        return input;
    }

    private static void generateVigenereSquare() {
        vigenereSquare = new char[ALPHABET_SIZE][ALPHABET_SIZE];
        Random rand = new Random();

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            for (int j = 0; j < ALPHABET_SIZE; j++) {
                vigenereSquare[i][j] = ALPHABET[(i + j) % ALPHABET_SIZE];
            }
        }

        for (int swap = 0; swap < ALPHABET_SIZE * 2; swap++) {
            int row1 = rand.nextInt(ALPHABET_SIZE);
            int row2 = rand.nextInt(ALPHABET_SIZE);
            for (int j = 0; j < ALPHABET_SIZE; j++) {
                char temp = vigenereSquare[row1][j];
                vigenereSquare[row1][j] = vigenereSquare[row2][j];
                vigenereSquare[row2][j] = temp;
            }

            int col1 = rand.nextInt(ALPHABET_SIZE);
            int col2 = rand.nextInt(ALPHABET_SIZE);
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                char temp = vigenereSquare[i][col1];
                vigenereSquare[i][col1] = vigenereSquare[i][col2];
                vigenereSquare[i][col2] = temp;
            }
        }
    }

    private static String encrypt(String message, String key) {
        StringBuilder sb = new StringBuilder();
        message = message.toLowerCase(Locale.forLanguageTag("tr"));
        key = key.toLowerCase(Locale.forLanguageTag("tr"));
        int keyIndex = 0;

        for (char m : message.toCharArray()) {
            int mIdx = indexInAlphabet(m);
            if (mIdx == -1) {
                sb.append(m);
                continue;
            }
            int kIdx = indexInAlphabet(key.charAt(keyIndex % key.length()));
            if (kIdx == -1) {
                sb.append(m); 
                continue;
            }
            sb.append(vigenereSquare[mIdx][kIdx]);
            keyIndex++;
        }
        return sb.toString();
    }

    private static String decrypt(String encrypted, String key) {
        StringBuilder sb = new StringBuilder();
        encrypted = encrypted.toLowerCase(Locale.forLanguageTag("tr"));
        key = key.toLowerCase(Locale.forLanguageTag("tr"));
        int keyIndex = 0;

        for (char c : encrypted.toCharArray()) {
            int kIdx = indexInAlphabet(key.charAt(keyIndex % key.length()));
            if (kIdx == -1) {
                sb.append(c);
                continue;
            }

            int mIdx = -1;
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                if (vigenereSquare[i][kIdx] == c) {
                    mIdx = i;
                    break;
                }
            }

            if (mIdx == -1) {
                sb.append(c);
            } else {
                sb.append(ALPHABET[mIdx]);
                keyIndex++;
            }
        }
        return sb.toString();
    }

    private static int indexInAlphabet(char c) {
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (ALPHABET[i] == c)
                return i;
        }
        return -1;
    }
}