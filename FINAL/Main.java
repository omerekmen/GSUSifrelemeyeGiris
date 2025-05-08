/////////////////////////////////////////////////////////////////
///////////////////// ÖMER EKMEN | 18504269 /////////////////////
/////////////////////////////////////////////////////////////////

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.LinkedHashSet;
import java.awt.Point;
import java.util.List;
import java.util.Locale;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Main {
    private static List<Character> originalAlphabet = Arrays.asList('a', 'b', 'c', 'ç', 'd', 'e', 'f', 'g', 'ğ', 'h',
            'ı', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'ö', 'p', 'q', 'r', 's', 't', 'u', 'ü', 'v', 'w', 'x', 'y', 'z');
    public static String bifidAlphabet = "abcçdefgğhıijklmnoöpqrsştuüvwxyz.!?*";
    private static List<Character> encryptedAlphabet;

    private static final int SIZE = 6;
    private List<Character> polybiusSquare;

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

            System.out
                    .print("""
                            \n
                            ADIM 2 - ŞİFRELEME ALGORİTMASI SEÇ
                            ---------------------------------------------
                            Lütfen şifreleme algoritmasını seçiniz:
                            ---------------------------------------------
                            1) Sezar Şifrelemesi için '1' tuşlayın.
                            2) Sezar Kutu Şifrelemesi için '2' tuşlayın.
                            3) Bifid şifrelemesi için '3' tuşlayın.
                            4) Sezar Kutu Şifrelemesi İyileştirilmiş Versiyonu için '4' tuşlayın.
                            5) Sacco Şifresi (Sezar Kutu şifrelemesi İyileştirilmiş Versiyonun İyileştirilmiş Versiyonu) için '5' tuşlayın.
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

                SezarSifreleme sezarSifreleme = new SezarSifreleme();
                String encryptedInput = sezarSifreleme.encrypt(processedInput);
                String decryptedInput = sezarSifreleme.decrypt(encryptedInput);

                System.out.println("Şifrelenmiş metin: " + encryptedInput);
                System.out.println("Çözülmüş metin:    " + decryptedInput);
                return;
            } else if (choice.equals("2")) {
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Sezar Kutu şifrelemesi seçildi.");
                System.out.println("-------------------------------------------------------------------------");

                SezarKutuSifreleme sezarKutuSifreleme = new SezarKutuSifreleme(bifidAlphabet);
                String encryptedInput = sezarKutuSifreleme.encrypt(processedInput);
                String decryptedInput = sezarKutuSifreleme.decrypt(encryptedInput);

                System.out.println("Şifrelenmiş metin: " + encryptedInput);
                System.out.println("Çözülmüş metin:    " + decryptedInput);
                return;
            } else if (choice.equals("3")) {
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Bifid şifrelemesi seçildi.");
                System.out.println("-------------------------------------------------------------------------");

                String key = bifidAlphabet;
                StringBuilder shuffledKey = new StringBuilder(key);
                for (int i = shuffledKey.length() - 1; i > 0; i--) {
                    int j = (int) (Math.random() * (i + 1));
                    char temp = shuffledKey.charAt(i);
                    shuffledKey.setCharAt(i, shuffledKey.charAt(j));
                    shuffledKey.setCharAt(j, temp);
                }
                key = shuffledKey.toString();

                System.out.println("Şifreleme Alfabesi: " + key);
                System.out.println("-------------------------------------------------------------------------");

                BifidSifreleme bifid = new BifidSifreleme(6, key);

                bifid.display();
                String encrypted = bifid.encrypt(processedInput);
                String decrypted = bifid.decrypt(encrypted);

                System.out.println("Şifrelenmiş metin: " + encrypted);
                System.out.println("Çözülmüş metin:    " + decrypted);
                return;
            } else if (choice.equals("4")) {
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Sezar Kutu şifrelemesi İyileştirilmiş Versiyonu seçildi.");
                System.out.println("-------------------------------------------------------------------------");
                System.out.print("Lütfen anahtar kelimeyi giriniz: ");
                String key = scanner.nextLine();
                if (key.isEmpty()) {
                    System.out.println("Anahtar kelime boş olamaz. Çıkılıyor...");
                    return;
                }
                System.out.println("Anahtar kelime: " + key);
                System.out.println("-------------------------------------------------------------------------");

                SezarKutuSifrelemeIyilestirilmis sezarKutuSifrelemeIyilestirilmis = new SezarKutuSifrelemeIyilestirilmis(key);
                String encryptedInput = sezarKutuSifrelemeIyilestirilmis.encrypt(processedInput, key);
                String decryptedInput = sezarKutuSifrelemeIyilestirilmis.decrypt(encryptedInput, key);

                System.out.println("Şifrelenmiş metin: " + encryptedInput);
                System.out.println("Çözülmüş metin:    " + decryptedInput);
                return;
            } else if (choice.equals("5")) {
                System.out.println("-------------------------------------------------------------------------");
                System.out.println(
                        "Sacco Şifresi (Sezar Kutu şifrelemesi İyileştirilmiş Versiyonun İyileştirilmiş Versiyonu) seçildi.");
                System.out.println("-------------------------------------------------------------------------");
                System.out.print("Lütfen anahtar kelimeyi giriniz: ");
                String key = scanner.nextLine();
                if (key.isEmpty()) {
                    System.out.println("Anahtar kelime boş olamaz. Çıkılıyor...");
                    return;
                }
                System.out.println("Anahtar kelime: " + key);
                System.out.println("-------------------------------------------------------------------------");

                SaccoSifreleme saccoSifreleme = new SaccoSifreleme(key);
                
                String encryptedInput = saccoSifreleme.encrypt(processedInput, key);
                String decryptedInput = saccoSifreleme.decrypt(encryptedInput, key);

                System.out.println("Şifrelenmiş metin: " + encryptedInput);
                System.out.println("Çözülmüş metin:    " + decryptedInput);
                return;
            } else if (choice.equals("6")) {
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Özel Şifreleme seçildi.");
                System.out.println("-------------------------------------------------------------------------");
                System.out.print("Lütfen anahtar kelimeyi giriniz: ");
                String key = scanner.nextLine();
                if (key.isEmpty()) {
                    System.out.println("Anahtar kelime boş olamaz. Çıkılıyor...");
                    return;
                }
                System.out.println("Anahtar kelime: " + key);
                System.out.println("-------------------------------------------------------------------------");

                OzelSifreleme ozelSifreleme = new OzelSifreleme(key);
                String encryptedInput = ozelSifreleme.encrypt(processedInput, key);
                String decryptedInput = ozelSifreleme.decrypt(encryptedInput, key);

                System.out.println("Şifrelenmiş metin: " + encryptedInput);
                System.out.println("Çözülmüş metin:    " + decryptedInput);
            } else {
                System.out.println("Geçersiz seçim. Çıkılıyor...");
                return;
            }
        }
    }

    // Metni işleme
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

    // Sezar Kutu Şifrelemesi İyileştirilmiş Versiyonunun İyileştirilmiş Versiyonu
    private static int[] getSaccoKeyOrder(String key) {
        key = key.toLowerCase();
        int length = key.length();
        Character[] chars = new Character[length];
        for (int i = 0; i < length; i++)
            chars[i] = key.charAt(i);

        // Sort list of characters with their original positions
        List<Map.Entry<Character, Integer>> entries = new ArrayList<>();
        for (int i = 0; i < length; i++)
            entries.add(Map.entry(chars[i], i));
        // Sort by letter, then by index to preserve left-to-right order for same letter
        entries.sort(Comparator.<Map.Entry<Character, Integer>, Character>comparing(Map.Entry::getKey)
                .thenComparing(Map.Entry::getValue));

        int[] order = new int[length];
        int num = 1;
        boolean[] assigned = new boolean[length];

        for (Map.Entry<Character, Integer> entry : entries) {
            int idx = entry.getValue();
            if (!assigned[idx]) {
                order[idx] = num++;
                assigned[idx] = true;
            }
        }
        return order;
    }

    private static int findPositionOfOrder(int[] order, int orderValue) {
        for (int i = 0; i < order.length; i++) {
            if (order[i] == orderValue) {
                return i;
            }
        }
        return -1; // Eğer bulunamazsa -1 döndürür
    }

    public static String saccoEncryption(String input, String key) {
        int[] order = getSaccoKeyOrder(key);
        int n = key.length();
        int inputLen = input.length();

        // Print key and order arrays for debugging
        System.out.println("Key letters: " + String.join("  ", key.split("")));
        System.out.print("Key order:   ");
        for (int x : order)
            System.out.print(x + "  ");
        System.out.println();

        // Calculate block size = sum 1..n = n*(n+1)/2
        int blockSize = n * (n + 1) / 2;

        int fullGroups = inputLen / blockSize;
        int remainder = inputLen % blockSize;

        // Total rows = fullGroups * n + extra rows for remainder
        int totalRows = fullGroups * n;

        int extraRows = 0;
        int rem = remainder;
        for (int i = 0; i < n; i++) {
            int needed = i + 1;
            if (rem > 0) {
                extraRows++;
                rem -= needed;
            } else
                break;
        }
        totalRows += extraRows;

        // Initialize table with '_'
        char[][] table = new char[totalRows][n];
        for (char[] row : table) {
            Arrays.fill(row, '_');
        }

        // Fill table with input letters per group
        int idx = 0;
        for (int group = 0; group < fullGroups + extraRows; group++) {
            for (int r = 0; r < n; r++) {
                int colsToFill = findPositionOfOrder(order, r + 1) + 1;
                System.out.println("Filling group " + group + ", row " + r + ", colsToFill: " + colsToFill);
                for (int c = 0; c < colsToFill; c++) {
                    if (idx < inputLen) {
                        table[group * n + r][c] = input.charAt(idx++);
                    }
                }
            }
        }

        System.out.println("-------------------------------------------------");
        for (char ch : key.toCharArray()) {
            System.out.print(ch + "  ");
        }
        System.out.println();

        for (int o : order) {
            System.out.print(o + "  ");
        }
        System.out.println();

        for (int r = 0; r < totalRows; r++) {
            for (int c = 0; c < n; c++) {
                System.out.print(table[r][c] + "  ");
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------------");

        StringBuilder encrypted = new StringBuilder();
        for (int ord = 1; ord <= n; ord++) {
            for (int c = 0; c < n; c++) {
                if (order[c] == ord) {
                    for (int r = 0; r < totalRows; r++) {
                        if (table[r][c] != '_') {
                            encrypted.append(table[r][c]);
                        }
                    }
                }
            }
        }

        return encrypted.toString();
    }

    public static String saccoDecryption(String input, String key) {
        int[] order = getSaccoKeyOrder(key);
        int col = key.length();
        int row = (int) Math.ceil((double) input.length() / col);

        int[] colLens = new int[col];
        int baseLen = input.length() / col;
        int extra = input.length() % col;

        int[] orderRev = Arrays.copyOfRange(order, extra, col);
        Arrays.sort(orderRev);

        for (int i = 0; i < orderRev.length; i++) {
            int dotIndex = extra > 0 ? (orderRev[i] - 1) * (baseLen + 1) + (baseLen) : -1;

            if (dotIndex != -1) {
                input = input.substring(0, dotIndex) + "." + input.substring(dotIndex);
            }
        }

        for (int j = 0; j < col; j++) {
            colLens[j] = baseLen;
        }
        for (int k = 1, given = 0; given < col && k <= col; k++) {
            for (int j = 0; j < col; j++) {
                if (order[j] == k && given < col) {
                    colLens[j]++;
                    given++;
                }
            }
        }

        char[][] table = new char[row][col];
        int idx = 0;
        for (int k = 1; k <= col; k++) {
            for (int j = 0; j < col; j++) {
                if (order[j] == k) {
                    for (int i = 0; i < colLens[j]; i++) {
                        table[i][j] = input.charAt(idx++);
                    }
                }
            }
        }

        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (table[i][j] != 0)
                    decrypted.append(table[i][j]);
            }
        }
        return decrypted.toString().replace(".", "");
    }

}


final class SezarSifreleme {
    private static List<Character> originalAlphabet = Arrays.asList('a', 'b', 'c', 'ç', 'd', 'e', 'f', 'g', 'ğ', 'h',
            'ı', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'ö', 'p', 'q', 'r', 's', 't', 'u', 'ü', 'v', 'w', 'x', 'y', 'z');
    private static List<Character> encryptedAlphabet;

    public SezarSifreleme() {
        encryptedAlphabet = new ArrayList<>(originalAlphabet);
        Collections.shuffle(encryptedAlphabet);

        if (originalAlphabet == encryptedAlphabet) {
            System.out.println("Şifrelenmiş alfabe, orjinal alfabe ile aynı olamaz. Yeniden şifreleme yapılıyor...");
            new SezarSifreleme();
        }
    }

    public String encrypt(String input) {
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

    public String decrypt(String input) {
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
}

final class SezarKutuSifreleme {
    public SezarKutuSifreleme(String key) {

    }

    public String encrypt(String input) {
        int column = 5;
        int row = (int) Math.ceil((double) input.length() / column);
        char[][] table = new char[row][column];
        int idx = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (idx < input.length()) {
                    table[i][j] = input.charAt(idx++);
                } else {
                    table[i][j] = '.';
                }
            }
        }
        StringBuilder encrypted = new StringBuilder();
        for (int j = 0; j < column; j++) {
            for (int i = 0; i < row; i++) {
                encrypted.append(table[i][j]);
            }
        }
        return encrypted.toString();
    }

    public String decrypt(String input) {
        int column = 5;
        int row = (int) Math.ceil((double) input.length() / column);
        char[][] table = new char[row][column];
        int idx = 0;
        for (int j = 0; j < column; j++) {
            for (int i = 0; i < row; i++) {
                if (idx < input.length()) {
                    table[i][j] = input.charAt(idx++);
                }
            }
        }
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                decrypted.append(table[i][j]);
            }
        }
        while (decrypted.length() > 0 && decrypted.charAt(decrypted.length() - 1) == '.') {
            decrypted.deleteCharAt(decrypted.length() - 1);
        }
        return decrypted.toString();
    }
}

final class BifidSifreleme {

    public BifidSifreleme(int aN, String aText) {
        if (aText.length() != aN * aN) {
            throw new IllegalArgumentException("Anahtar Alfabe Hatalı!");
        }

        grid = new char[aN][aN];
        int row = 0;
        int col = 0;
        for (char ch : aText.toCharArray()) {
            grid[row][col] = ch;
            coordinates.put(ch, new Point(row, col));
            col += 1;
            if (col == aN) {
                col = 0;
                row += 1;
            }
        }

        if (aN == 5) {
            coordinates.put('J', coordinates.get('I'));
        }
    }

    public String encrypt(String aText) {
        List<Integer> rowOne = new ArrayList<Integer>();
        List<Integer> rowTwo = new ArrayList<Integer>();
        for (char ch : aText.toCharArray()) {
            Point coordinate = coordinates.get(ch);
            rowOne.add(coordinate.x);
            rowTwo.add(coordinate.y);
        }

        rowOne.addAll(rowTwo);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rowOne.size() - 1; i += 2) {
            result.append(grid[rowOne.get(i)][rowOne.get(i + 1)]);
        }
        return result.toString();
    }

    public String decrypt(String aText) {
        List<Integer> row = new ArrayList<Integer>();
        for (char ch : aText.toCharArray()) {
            Point coordinate = coordinates.get(ch);
            row.add(coordinate.x);
            row.add(coordinate.y);
        }

        final int middle = row.size() / 2;
        List<Integer> rowOne = row.subList(0, middle);
        List<Integer> rowTwo = row.subList(middle, row.size());
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < middle; i++) {
            result.append(grid[rowOne.get(i)][rowTwo.get(i)]);
        }
        return result.toString();
    }

    public void display() {
        Arrays.stream(grid).forEach(row -> System.out.println(Arrays.toString(row)));
    }

    private char[][] grid;
    private Map<Character, Point> coordinates = new HashMap<Character, Point>();

}

final class SezarKutuSifrelemeIyilestirilmis {
    public SezarKutuSifrelemeIyilestirilmis(String key) {

    }

    private static int[] getKeyOrder(String key) {
        key = key.toLowerCase();
        List<Character> keyChars = new ArrayList<>();
        for (char c : key.toCharArray())
            keyChars.add(c);

        List<Character> sorted = new ArrayList<>(keyChars);
        Collections.sort(sorted);

        int[] order = new int[key.length()];
        int[] used = new int[key.length()];
        int num = 1;
        for (char ch : sorted) {
            for (int i = 0; i < keyChars.size(); i++) {
                if (keyChars.get(i) == ch && used[i] == 0) {
                    order[i] = num++;
                    used[i] = 1;
                    break;
                }
            }
        }
        return order;
    }

    public String encrypt(String input, String key) {
        int[] order = getKeyOrder(key);
        int col = key.length();
        int row = (int) Math.ceil((double) input.length() / col);

        char[][] table = new char[row][col];
        int idx = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (idx < input.length()) {
                    table[i][j] = input.charAt(idx++);
                } else {
                    table[i][j] = 0;
                }
            }
        }

        System.out.println("-------------------------------------------------");
        System.out.println(String.join("  ", key.split("")));
        System.out.println(Arrays.stream(order).mapToObj(String::valueOf).reduce((a, b) -> a + "  " + b).orElse(""));
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(table[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------------");

        StringBuilder encrypted = new StringBuilder();
        for (int k = 1; k <= col; k++) {
            for (int j = 0; j < col; j++) {
                if (order[j] == k) {
                    for (int i = 0; i < row; i++) {
                        if (table[i][j] != 0)
                            encrypted.append(table[i][j]);
                    }
                }
            }
        }
        return encrypted.toString();
    }

    public String decrypt(String input, String key) {
        int[] order = getKeyOrder(key);
        int col = key.length();
        int row = (int) Math.ceil((double) input.length() / col);

        int[] colLens = new int[col];
        int baseLen = input.length() / col;
        int extra = input.length() % col;

        int[] orderRev = Arrays.copyOfRange(order, extra, col);
        Arrays.sort(orderRev);

        for (int i = 0; i < orderRev.length; i++) {
            int dotIndex = extra > 0 ? (orderRev[i] - 1) * (baseLen + 1) + (baseLen) : -1;

            if (dotIndex != -1) {
                input = input.substring(0, dotIndex) + "." + input.substring(dotIndex);
            }
        }

        for (int j = 0; j < col; j++) {
            colLens[j] = baseLen;
        }
        for (int k = 1, given = 0; given < col && k <= col; k++) {
            for (int j = 0; j < col; j++) {
                if (order[j] == k && given < col) {
                    colLens[j]++;
                    given++;
                }
            }
        }

        char[][] table = new char[row][col];
        int idx = 0;
        for (int k = 1; k <= col; k++) {
            for (int j = 0; j < col; j++) {
                if (order[j] == k) {
                    for (int i = 0; i < colLens[j]; i++) {
                        table[i][j] = input.charAt(idx++);
                    }
                }
            }
        }

        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (table[i][j] != 0)
                    decrypted.append(table[i][j]);
            }
        }
        return decrypted.toString().replace(".", "");
    }
}

final class SaccoSifreleme {
    public SaccoSifreleme(String key) {

    }

    private static int[] getSaccoKeyOrder(String key) {
        key = key.toLowerCase();
        int length = key.length();
        Character[] chars = new Character[length];
        for (int i = 0; i < length; i++)
            chars[i] = key.charAt(i);

        List<Map.Entry<Character, Integer>> entries = new ArrayList<>();
        for (int i = 0; i < length; i++)
            entries.add(Map.entry(chars[i], i));

        entries.sort(Comparator.<Map.Entry<Character, Integer>, Character>comparing(Map.Entry::getKey)
                .thenComparing(Map.Entry::getValue));

        int[] order = new int[length];
        int num = 1;
        boolean[] assigned = new boolean[length];

        for (Map.Entry<Character, Integer> entry : entries) {
            int idx = entry.getValue();
            if (!assigned[idx]) {
                order[idx] = num++;
                assigned[idx] = true;
            }
        }
        return order;
    }

    private static int findPositionOfOrder(int[] order, int orderValue) {
        for (int i = 0; i < order.length; i++) {
            if (order[i] == orderValue) {
                return i;
            }
        }
        return -1; // Eğer bulunamazsa -1 döndürür
    }

    public String encrypt(String input, String key) {
        int[] order = getSaccoKeyOrder(key);
        int n = key.length();
        int inputLen = input.length();

        // Print key and order arrays for debugging
        System.out.println("Key letters: " + String.join("  ", key.split("")));
        System.out.print("Key order:   ");
        for (int x : order)
            System.out.print(x + "  ");
        System.out.println();

        int blockSize = n * (n + 1) / 2;

        int fullGroups = inputLen / blockSize;
        int remainder = inputLen % blockSize;

        int totalRows = fullGroups * n;

        int extraRows = 0;
        int rem = remainder;
        for (int i = 0; i < n; i++) {
            int needed = i + 1;
            if (rem > 0) {
                extraRows++;
                rem -= needed;
            } else
                break;
        }
        totalRows += extraRows;

        char[][] table = new char[totalRows][n];
        for (char[] row : table) {
            Arrays.fill(row, '_');
        }

        int idx = 0;
        for (int group = 0; group < fullGroups + extraRows; group++) {
            for (int r = 0; r < n; r++) {
                int colsToFill = findPositionOfOrder(order, r + 1) + 1;
                for (int c = 0; c < colsToFill; c++) {
                    if (idx < inputLen) {
                        table[group * n + r][c] = input.charAt(idx++);
                    }
                }
            }
        }

        System.out.println("-------------------------------------------------");
        for (char ch : key.toCharArray()) {
            System.out.print(ch + "  ");
        }
        System.out.println();

        for (int o : order) {
            System.out.print(o + "  ");
        }
        System.out.println();

        for (int r = 0; r < totalRows; r++) {
            for (int c = 0; c < n; c++) {
                System.out.print(table[r][c] + "  ");
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------------");

        StringBuilder encrypted = new StringBuilder();
        for (int ord = 1; ord <= n; ord++) {
            for (int c = 0; c < n; c++) {
                if (order[c] == ord) {
                    for (int r = 0; r < totalRows; r++) {
                        if (table[r][c] != '_') {
                            encrypted.append(table[r][c]);
                        }
                    }
                }
            }
        }

        return encrypted.toString();
    }

    public String decrypt(String input, String key) {
        int[] order = getSaccoKeyOrder(key);
        int col = key.length();
        int row = (int) Math.ceil((double) input.length() / col);

        Map<Integer, Integer> colLetterCount = new HashMap<>();
        int inputLen = input.length();
        int[] colFillCount = new int[col];

        while (inputLen > 0) {
            for (int i = 0; i < col; i++) {
                int columnOrder = findPositionOfOrder(order, i + 1);

                for (int j = 0; j < columnOrder; j++) {
                    if (inputLen > 0) {
                        colFillCount[j] += 1;
                    }
                }
                inputLen -= columnOrder;

                if (inputLen > 0 && i == col - 1) {
                    i = 0;
                }
            }
        }

        for (int i = 0; i < col; i++) {
            int columnOrder = findPositionOfOrder(order, i + 1);
            colLetterCount.put(i+1, colFillCount[columnOrder]);
        }

        System.out.println("Column letter count: " + colLetterCount);

        int[] colLens = new int[col];
        int baseLen = input.length() / col;
        int extra = input.length() % col;

        int[] orderRev = Arrays.copyOfRange(order, extra, col);
        Arrays.sort(orderRev);

        for (int i = 0; i < orderRev.length; i++) {
            int dotIndex = extra > 0 ? (orderRev[i] - 1) * (baseLen + 1) + (baseLen) : -1;

            if (dotIndex != -1) {
                input = input.substring(0, dotIndex) + "." + input.substring(dotIndex);
            }
        }

        for (int j = 0; j < col; j++) {
            colLens[j] = baseLen;
        }
        for (int k = 1, given = 0; given < col && k <= col; k++) {
            for (int j = 0; j < col; j++) {
                if (order[j] == k && given < col) {
                    colLens[j]++;
                    given++;
                }
            }
        }

        char[][] table = new char[row][col];
        int idx = 0;
        for (int k = 1; k <= col; k++) {
            for (int j = 0; j < col; j++) {
                if (order[j] == k) {
                    for (int i = 0; i < colLens[j]; i++) {
                        table[i][j] = input.charAt(idx++);
                    }
                }
            }
        }

        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (table[i][j] != 0)
                    decrypted.append(table[i][j]);
            }
        }
        return decrypted.toString().replace(".", "");
    }
}

final class OzelSifreleme {
    private final String keyword;

    public OzelSifreleme(String key) {
        this.keyword = key; // Anahtar kelimeyi sakla.
    }

    public String encrypt(String input, String key) {
        System.out.println("Özel Şifreleme Başlatıldı...");

        // Adım 1: Bifid Şifreleme kullan
        System.out.println("Adım 1: Bifid Şifrelemesi Uygulanıyor...");
        BifidSifreleme bifidSifreleme = new BifidSifreleme(6, Main.bifidAlphabet);
        String bifidEncrypted = bifidSifreleme.encrypt(input);
        System.out.println("Bifid Şifrelemesi Sonucu: " + bifidEncrypted);

        // Adım 2: Sezar Kutu Şifrelemesi İyileştirilmiş Versiyonu kullan
        System.out.println("Adım 2: Sezar Kutu Şifrelemesi İyileştirilmiş Versiyonu Uygulanıyor...");
        SezarKutuSifrelemeIyilestirilmis sezarKutuSifreleme = new SezarKutuSifrelemeIyilestirilmis(keyword);
        String finalEncrypted = sezarKutuSifreleme.encrypt(bifidEncrypted, keyword);
        System.out.println("Son Şifreleme Sonucu: " + finalEncrypted);

        System.out.println("Özel Şifreleme Tamamlandı.");
        return finalEncrypted;
    }

    public String decrypt(String input, String key) {
        System.out.println("Özel Şifreleme Çözülüyor...");

        // Adım 1: Sezar Kutu Şifrelemesi İyileştirilmiş Versiyonu Çözme
        System.out.println("Adım 1: Sezar Kutu Şifrelemesi İyileştirilmiş Versiyonu Çözme Uygulanıyor...");
        SezarKutuSifrelemeIyilestirilmis sezarKutuSifreleme = new SezarKutuSifrelemeIyilestirilmis(keyword);
        String sezarDecrypted = sezarKutuSifreleme.decrypt(input, keyword);
        System.out.println("Sezar Kutu Çözme Sonucu: " + sezarDecrypted);

        // Adım 2: Bifid Çözme
        System.out.println("Adım 2: Bifid Çözme Uygulanıyor...");
        BifidSifreleme bifidSifreleme = new BifidSifreleme(6, Main.bifidAlphabet);
        String finalDecrypted = bifidSifreleme.decrypt(sezarDecrypted);
        System.out.println("Son Çözme Sonucu: " + finalDecrypted);

        System.out.println("Özel Çözme Tamamlandı.");
        return finalDecrypted;
    }
}

