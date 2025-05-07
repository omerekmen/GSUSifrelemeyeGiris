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
            
            String processedInput = OnCalisma(input);

            System.out.println("""
                    \n
                    ADIM 1 - GİRİLEN METİNİ İŞLE
                    ---------------------------------------------
                    Girilen metin:\n""" + input + 
                    """ 
                    \n
                    İşlenmiş metin:\n""" + processedInput);

            System.out.print("""
                    \n
                    ADIM 2 - ŞİFRELEME ALGORİTMASI SEÇ
                    ---------------------------------------------
                    Lütfen şifreleme algoritmasını seçiniz:
                    ---------------------------------------------
                    1) Sezar Şifrelemesi için '1' tuşlayın.
                    2) Sezar Kutu Şifrelemesi için '2' tuşlayın.
                    3) Bifid şifrelemesi için '3' tuşlayın.
                    4) Sezar Kutu Şifrelemesi İyileştirilmiş Versiyonu için '4' tuşlayın.
                    5) Sezar Kutu Şifrelemesi İyileştirilmiş Versiyonunun İyileştirilmiş Versiyonu için '5' tuşlayın.
                    6) Özel şifreleme için '6' tuşlayın.
                    
                    Çıkmak için 'q' tuşlayın.
                    """);

            String choice = scanner.nextLine();
            if (choice.equals("q")) {
                System.out.println("Çıkılıyor...");
                return;
            } else if (choice.equals("1")) {
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Sezar şifrelemesi seçildi.");
                System.out.println("-------------------------------------------------------------------------");

                String encryptedInput = sezarEncryption(processedInput);
                String decryptedInput = sezarDecryption(encryptedInput);

                System.out.println("Şifrelenmiş metin: " + encryptedInput);
                System.out.println("Çözülmüş metin:    " + decryptedInput);
                return;
            } else if (choice.equals("2")) {
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Sezar Kutu şifrelemesi seçildi.");
                System.out.println("-------------------------------------------------------------------------");

                String encryptedInput = sezarKutuEncryption(processedInput);
                String decryptedInput = sezarKutuDecryption(encryptedInput);

                System.out.println("Şifrelenmiş metin: " + encryptedInput);
                System.out.println("Çözülmüş metin:    " + decryptedInput);
                return;
            } else if (choice.equals("3")) {
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Bifid şifrelemesi seçildi.");
                System.out.println("-------------------------------------------------------------------------");

                String encryptedInput = bfidEncryption(processedInput);
                String decryptedInput = bfidDecryption(encryptedInput);

                System.out.println("Şifrelenmiş metin: " + encryptedInput);
                System.out.println("Çözülmüş metin:    " + decryptedInput);
                return;
            } else if (choice.equals("4")) {
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Sezar Kutu şifrelemesi İyileştirilmiş Versiyonu seçildi.");
                System.out.println("-------------------------------------------------------------------------");

                String encryptedInput = sezarKutuIyilestirilmisEncryption(processedInput);
                String decryptedInput = sezarKutuIyilestirilmisDecryption(encryptedInput);

                System.out.println("Şifrelenmiş metin: " + encryptedInput);
                System.out.println("Çözülmüş metin:    " + decryptedInput);
                return;
            } else if (choice.equals("5")) {
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Sezar Kutu şifrelemesi İyileştirilmiş Versiyonun İyileştirilmiş Versiyonu seçildi.");
                System.out.println("-------------------------------------------------------------------------");

                String encryptedInput = sezarKutuIyilestirilmis2Encryption(processedInput);
                String decryptedInput = sezarKutuIyilestirilmis2Decryption(encryptedInput);

                System.out.println("Şifrelenmiş metin: " + encryptedInput);
                System.out.println("Çözülmüş metin:    " + decryptedInput);
                return;
            } else if (choice.equals("6")) {
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Özel Şifreleme seçildi.");
                System.out.println("-------------------------------------------------------------------------");

                String encryptedInput = ozelEncryption(processedInput);
                String decryptedInput = ozelDecryption(encryptedInput);

                System.out.println("Şifrelenmiş metin: " + encryptedInput);
                System.out.println("Çözülmüş metin:    " + decryptedInput);
            } else {
                System.out.println("Geçersiz seçim. Çıkılıyor...");
                return;
            }
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

        // input = input.replaceAll("istanbul", "konstantinopolis");

        return input;
    }

    public static String sezarEncryption(String input) {
        encryptedAlphabet = new ArrayList<>(originalAlphabet);
        Collections.shuffle(encryptedAlphabet);

        if (originalAlphabet == encryptedAlphabet) {
            System.out.println("Şifrelenmiş alfabe, orjinal alfabe ile aynı olamaz. Yeniden şifreleme yapılıyor...");
            sezarEncryption(input);
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

    public static String sezarKutuEncryption(String input) {

        return "";
    }
    public static String sezarKutuDecryption(String input) {
        
        return "";
    } 
    
    public static String bfidEncryption(String input) {

        return "";
    }
    public static String bfidDecryption(String input) {
        
        return "";
    } 

    public static String sezarKutuIyilestirilmisEncryption(String input) {

        return "";
    }

    public static String sezarKutuIyilestirilmisDecryption(String input) {
        
        return "";
    } 

    public static String sezarKutuIyilestirilmis2Encryption(String input) {

        return "";
    }

    public static String sezarKutuIyilestirilmis2Decryption(String input) {
        
        return "";
    } 

    public static String ozelEncryption(String input) {

        return "";
    }

    public static String ozelDecryption(String input) {
        
        return "";
    } 
}