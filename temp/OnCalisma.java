public class OnCalisma {
    public static String Process(String input) {
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
}
