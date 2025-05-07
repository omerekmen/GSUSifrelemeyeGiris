import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    private static List<Character> originalAlphabet = Arrays.asList('a', 'b', 'c', 'ç', 'd', 'e', 'f', 'g', 'ğ', 'h', 'ı', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'ö', 'p', 'q', 'r', 's', 't', 'u', 'ü', 'v', 'w', 'x', 'y', 'z');
    private static List<Character> encryptedAlphabet;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Lütfen bir metin giriniz:");
            String input = scanner.nextLine();
            
            String processedInput = OnCalisma.Process(input);

            System.out.println("""
                    \n
                    ADIM 1 - GİRİLEN METİNİ İŞLE
                    ---------------------------------------------
                    Girilen metin:    """ + input + 
                    """ 
                    İşlenmiş metin:   """ + processedInput);

            System.out.print("""
                    \n
                    Sezar Şifreleme ve Kutu Şifreleme Uygulaması
                    ---------------------------------------------
                    1) Bifid şifrelemesi için '1' tuşlayın.
                    2) Kutu şifrelemesi için '2' tuşlayın.
                    3) Custom şifreleme için '3' tuşlayın.
                    1) Sezar şifrelemesi için '1' tuşlayın.
                    2) Kutu şifrelemesi için '2' tuşlayın.
                    Çıkmak için 'q' tuşlayın.
                    """);

            String choice = scanner.nextLine();
            if (choice.equals("q")) {
                System.out.println("Çıkılıyor...");
                return;
            } else if (choice.equals("1")) {
                System.out.println("Sezar şifrelemesi seçildi.");
                return;
            } else if (choice.equals("2")) {
                System.out.println("Kutu şifrelemesi seçildi.");
            } else {
                System.out.println("Geçersiz seçim. Çıkılıyor...");
                return;
            }

            // String encryptedInput = sezarSifreleme(processedInput);
            // String decryptedInput = sezarDecryption(encryptedInput);
            
            String encryptedInput = sezarKutuSifreleme(processedInput);
            String decryptedInput = sezarKutuDecryption(encryptedInput);
            
            System.out.println("Şifrelenmiş metin: " + encryptedInput);
            System.out.println("Çözülmüş metin:    " + decryptedInput);
        }
    }

    public static String sezarSifreleme(String input) {
        encryptedAlphabet = new ArrayList<>(originalAlphabet);
        Collections.shuffle(encryptedAlphabet);

        if (originalAlphabet == encryptedAlphabet) {
            System.out.println("Şifrelenmiş alfabe, orjinal alfabe ile aynı olamaz. Yeniden şifreleme yapılıyor...");
            sezarSifreleme(input);
        }

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Orjinal alfabe:     " + originalAlphabet);
        System.out.println("Şifrelenmiş alfabe: " + encryptedAlphabet);
        System.out.println("-------------------------------------------------------------------------");
        
        StringBuilder encrypted = new StringBuilder();
        
        for (char c : input.toCharArray()) {
            int index = originalAlphabet.indexOf(c);
            if (index != -1) {
                encrypted.append(encryptedAlphabet.get(index));
            } else {
                encrypted.append(c);
            }
        }
        
        return encrypted.toString();
    }

    public static String sezarDecryption(String input) {
        StringBuilder decrypted = new StringBuilder();
        
        for (char c : input.toCharArray()) {
            int index = encryptedAlphabet.indexOf(c);
            if (index != -1) {
                decrypted.append(originalAlphabet.get(index));
            } else {
                decrypted.append(c);
            }
        }
        
        return decrypted.toString();
    }

    public static String sezarKutuSifreleme(String input) {
        int length = input.length();
        int size = (int) Math.ceil(Math.sqrt(length));
        char[][] sezarBox = new char[size][size];

        for (int i = 0; i < size * size; i++) {
            int row = i % size;
            int col = i / size;
            if (i < length) {
                sezarBox[row][col] = input.charAt(i);
            } else {
                sezarBox[row][col] = ' ';
            }
        }

        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (sezarBox[i][j] != ' ') {
                    encrypted.append(sezarBox[i][j]);
                }
            }
        }

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Sezar kutu: ");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(sezarBox[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------------------------------------");

        return encrypted.toString();
    }

    public static String sezarKutuDecryption(String input) {
        int length = input.length();
        int size = (int) Math.ceil(Math.sqrt(length));
        char[][] sezarBox = new char[size][size];
        
        int index = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (index < length) {
                    sezarBox[row][col] = input.charAt(index++);
                } else {
                    sezarBox[row][col] = ' ';
                }
            }
        }
        
        // Read the original message column by column
        StringBuilder decrypted = new StringBuilder();
        for (int col = 0; col < size; col++) {
            for (int row = 0; row < size; row++) {
                if (row * size + col < length) {  // Check if this position would be in bounds
                    decrypted.append(sezarBox[row][col]);
                }
            }
        }
        
        return decrypted.toString();
    } 
}