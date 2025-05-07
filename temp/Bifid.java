import java.util.List;
import java.util.ArrayList;

public class Bifid {
    private static final String ALPHABET = "abcçdefgğhıijklmnoöpqrstuüvwxyz";
    private static char alphabetsMatrix[][];

    static {
        alphabetsMatrix = new char[6][6];
        int index = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (index < ALPHABET.length()) {
                    alphabetsMatrix[i][j] = ALPHABET.charAt(index++);
                } else {
                    alphabetsMatrix[i][j] = ' '; // Fill remaining spaces with a placeholder
                }
            }
        }
    }

    private String matrixKey;

    public void setMatrixKey(String key) {
        if (key == null || key.isEmpty()) {
            this.matrixKey = generateRandomKey();
        } else {
            this.matrixKey = key;
        }
    }

    private String generateRandomKey() {
        StringBuilder randomKey = new StringBuilder();
        List<Character> chars = new ArrayList<>();
        for (char c : ALPHABET.toCharArray()) {
            chars.add(c);
        }
        java.util.Collections.shuffle(chars);
        for (char c : chars) {
            randomKey.append(c);
        }
        return randomKey.toString();
    }
     
    public static String Encrypt(String input) {
        StringBuilder encrypted = new StringBuilder();
        String[] bifidTable = {
            "a", "b", "c", "d", "e",
            "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o",
            "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y",
            "z"
        };
        
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                int index = Character.toLowerCase(c) - 'a';
                encrypted.append(bifidTable[index]);
            } else {
                encrypted.append(c);
            }
        }
        
        return encrypted.toString();
    }

    public static String Decrypt(String input) {
        StringBuilder decrypted = new StringBuilder();
        String[] bifidTable = {
            "a", "b", "c", "d", "e",
            "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o",
            "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y",
            "z"
        };
        
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                int index = Character.toLowerCase(c) - 'a';
                decrypted.append(bifidTable[index]);
            } else {
                decrypted.append(c);
            }
        }
        
        return decrypted.toString();
    }
}
