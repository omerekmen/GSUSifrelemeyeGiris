import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Main {
    private static List<Character> originalAlphabet = Arrays.asList('a', 'b', 'c', 'ç', 'd', 'e', 'f', 'g', 'ğ', 'h', 'ı', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'ö', 'p', 'q', 'r', 's', 't', 'u', 'ü', 'v', 'w', 'x', 'y', 'z');
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
                System.out.print("Lütfen anahtar kelimeyi giriniz: ");
                String key = scanner.nextLine();
                if (key.isEmpty()) {
                    System.out.println("Anahtar kelime boş olamaz. Çıkılıyor...");
                    return;
                }
                System.out.println("Anahtar kelime: " + key);
                System.out.println("-------------------------------------------------------------------------");

                String encryptedInput = bifidEncryption(processedInput, key);
                String decryptedInput = bifidDecryption(encryptedInput, key);

                System.out.println("Şifrelenmiş metin: " + encryptedInput);
                System.out.println("Çözülmüş metin:    " + decryptedInput);
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

                String encryptedInput = sezarKutuIyilestirilmisEncryption(processedInput, key);
                String decryptedInput = sezarKutuIyilestirilmisDecryption(encryptedInput, key);

                System.out.println("Şifrelenmiş metin: " + encryptedInput);
                System.out.println("Çözülmüş metin:    " + decryptedInput);
                return;
            } else if (choice.equals("5")) {
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Sacco Şifresi (Sezar Kutu şifrelemesi İyileştirilmiş Versiyonun İyileştirilmiş Versiyonu) seçildi.");
                System.out.println("-------------------------------------------------------------------------");
                System.out.print("Lütfen anahtar kelimeyi giriniz: ");
                String key = scanner.nextLine();
                if (key.isEmpty()) {
                    System.out.println("Anahtar kelime boş olamaz. Çıkılıyor...");
                    return;
                }
                System.out.println("Anahtar kelime: " + key);
                System.out.println("-------------------------------------------------------------------------");

                String encryptedInput = saccoEncryption(processedInput, key);
                String decryptedInput = saccoDecryption(encryptedInput, key);

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
                String encryptedInput = ozelEncryption(processedInput, key);
                String decryptedInput = ozelDecryption(encryptedInput, key);

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


    // Sezar Şifrelemesi
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


    // Sezar Kutu Şifrelemesi
    public static String sezarKutuEncryption(String input) {
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

    public static String sezarKutuDecryption(String input) {
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
    

    // Bifid Şifrelemesi
    private static int[] getCoordinates(char ch, List<Character> polybiusSquare) {
        ch = Character.toLowerCase(ch);
        int index = polybiusSquare.indexOf(ch);
        if (index == -1) {
            throw new IllegalArgumentException("Alfabede bulunmayan karakter: " + ch);
        }
        return new int[] { (index / SIZE) + 1, (index % SIZE) + 1 };
    }

    private static char getCharFromCoordinates(int row, int col, List<Character> polybiusSquare) {
        int index = (row - 1) * SIZE + (col - 1);
        if (index < 0 || index >= polybiusSquare.size()) {
            throw new IllegalArgumentException("Geçersiz koordinat: " + row + ", " + col);
        }
        return polybiusSquare.get(index);
    }
    
    private static List<Character> createPolybiusSquare(String key, List<Character> alphabet) {
        key = key.toLowerCase(Locale.forLanguageTag("tr"));
        LinkedHashSet<Character> set = new LinkedHashSet<>();

        for (char c : key.toCharArray()) {
            if (alphabet.contains(c)) {
                set.add(c);
            }
        }
        for (char c : alphabet) {
            set.add(c);
        }

        List<Character> square = new ArrayList<>(set);

        while (square.size() < SIZE * SIZE) {
            square.add(' '); // boş hücre
        }

        return square;
    }

    public static String bifidEncryption(String input, String key) {
        List<Character> square = createPolybiusSquare(key, originalAlphabet);

        StringBuilder filtered = new StringBuilder();
        input = input.toLowerCase(Locale.forLanguageTag("tr"));
        for (char c : input.toCharArray()) {
            if (square.contains(c) && c != ' ')
                filtered.append(c);
        }
        String processed = filtered.toString();

        int length = processed.length();
        if (length == 0)
            return "";

        int[] rows = new int[length];
        int[] cols = new int[length];

        for (int i = 0; i < length; i++) {
            int[] coord = getCoordinates(processed.charAt(i), square);
            rows[i] = coord[0];
            cols[i] = coord[1];
        }

        int[] combined = new int[length * 2];
        System.arraycopy(rows, 0, combined, 0, length);
        System.arraycopy(cols, 0, combined, length, length);

        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char ch = getCharFromCoordinates(combined[i], combined[length + i], square);
            encrypted.append(ch);
        }
        return encrypted.toString();
    }

    public static String bifidDecryption(String input, String key) {
        List<Character> square = createPolybiusSquare(key, originalAlphabet);

        StringBuilder filtered = new StringBuilder();
        input = input.toLowerCase(Locale.forLanguageTag("tr"));
        for (char c : input.toCharArray()) {
            if (square.contains(c) && c != ' ')
                filtered.append(c);
        }
        String processed = filtered.toString();

        int length = processed.length();
        if (length == 0)
            return "";

        int[] coords = new int[length * 2];

        for (int i = 0; i < length; i++) {
            int[] coord = getCoordinates(processed.charAt(i), square);
            coords[i] = coord[0];
            coords[length + i] = coord[1];
        }

        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char ch = getCharFromCoordinates(coords[i], coords[i + length], square);
            decrypted.append(ch);
        }

        return decrypted.toString();
    }


    // Sezar Kutu Şifrelemesi İyileştirilmiş Versiyonu
    private static int[] getKeyOrder(String key) {
        key = key.toLowerCase();
        List<Character> keyChars = new ArrayList<>();
        for (char c : key.toCharArray()) keyChars.add(c);

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
    
    public static String sezarKutuIyilestirilmisEncryption(String input, String key) {
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
                        if (table[i][j] != 0) encrypted.append(table[i][j]);
                    }
                }
            }
        }
        return encrypted.toString();
    }
    
    public static String sezarKutuIyilestirilmisDecryption(String input, String key) {
        int[] order = getKeyOrder(key);
        int col = key.length();
        int row = (int) Math.ceil((double) input.length() / col);

        int[] colLens = new int[col];
        int baseLen = input.length() / col;
        int extra = input.length() % col;

        int[] orderRev = Arrays.copyOfRange(order, extra, col);
        Arrays.sort(orderRev);

        for (int i = 0; i < orderRev.length; i++) {
            int dotIndex = extra > 0 ? (orderRev[i] - 1) * (baseLen+1) + (baseLen) : -1;
    
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
                if (table[i][j] != 0) decrypted.append(table[i][j]);
            }
        }
        return decrypted.toString().replace(".", "");
    } 


    // Sezar Kutu Şifrelemesi İyileştirilmiş Versiyonunun İyileştirilmiş Versiyonu
    private static int[] getSaccoKeyOrder(String key) {
        key = key.toLowerCase();
        int length = key.length();
        Character[] chars = new Character[length];
        for (int i = 0; i < length; i++) chars[i] = key.charAt(i);

        // Sort list of characters with their original positions
        List<Map.Entry<Character, Integer>> entries = new ArrayList<>();
        for (int i = 0; i < length; i++) entries.add(Map.entry(chars[i], i));
        // Sort by letter, then by index to preserve left-to-right order for same letter
        entries.sort(Comparator.<Map.Entry<Character,Integer>,Character>comparing(Map.Entry::getKey)
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
        for (char[] row : table)
            Arrays.fill(row, '_');

        // Fill table with input letters per group
        int idx = 0;
        for (int group = 0; group < fullGroups; group++) {
            for (int r = 0; r < n; r++) {
                int colsToFill = r + 1;
                for (int c = 0; c < colsToFill; c++) {
                    if (idx < inputLen) {
                        table[group * n + r][c] = input.charAt(idx++);
                    }
                }
            }
        }

        int startRow = fullGroups * n;
        for (int r = 0; r < extraRows; r++) {
            int colsToFill = r + 1;
            for (int c = 0; c < colsToFill; c++) {
                if (idx < inputLen) {
                    table[startRow + r][c] = input.charAt(idx++);
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
        int[] order = getKeyOrder(key);
        int col = key.length();
        int row = (int) Math.ceil((double) input.length() / col);

        int[] colLens = new int[col];
        int baseLen = input.length() / col;
        int extra = input.length() % col;

        int[] orderRev = Arrays.copyOfRange(order, extra, col);
        Arrays.sort(orderRev);

        for (int i = 0; i < orderRev.length; i++) {
            int dotIndex = extra > 0 ? (orderRev[i] - 1) * (baseLen+1) + (baseLen) : -1;
    
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
                if (table[i][j] != 0) decrypted.append(table[i][j]);
            }
        }
        return decrypted.toString().replace(".", "");
    }


    // Özel Şifreleme
    public static String ozelEncryption(String input, String key) {
        int[] order = getKeyOrder(key);
        int col = key.length();
        int row = (int) Math.ceil((double) input.length() / col);

        // Create a table filled with placeholder characters
        char[][] table = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                table[i][j] = '_'; // Initialize with underscore for visualization
            }
        }

        // Fill the table according to the key order
        int idx = 0;
        for (int k = 1; k <= col; k++) {
            for (int j = 0; j < col; j++) {
                if (order[j] == k) {
                    for (int i = 0; i < row && idx < input.length(); i++) {
                        table[i][j] = input.charAt(idx++);
                    }
                }
            }
        }

        // Print the table with headers
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

        // Generate encrypted text by reading columns according to the order
        StringBuilder encrypted = new StringBuilder();
        for (int k = 1; k <= col; k++) {
            for (int j = 0; j < col; j++) {
                if (order[j] == k) {
                    for (int i = 0; i < row; i++) {
                        if (table[i][j] != '_')
                            encrypted.append(table[i][j]);
                    }
                }
            }
        }
        return encrypted.toString();
    }

    public static String ozelDecryption(String input, String key) {
        // Her harfi bir önceki harfe çevir
        StringBuilder decrypted = new StringBuilder();
        for (char c : input.toCharArray()) {
            int idx = originalAlphabet.indexOf(c);
            if (idx != -1) {
                idx = (idx - 1 + originalAlphabet.size()) % originalAlphabet.size();
                decrypted.append(originalAlphabet.get(idx));
            } else {
                decrypted.append(c);
            }
        }
        return decrypted.toString();
    } 
}
