import java.util.*;

public class cap_count {

    int freq = 1;

    cap_count() {
        freq = 0;
    }

    public void count(String sent) {

        StringTokenizer st = new StringTokenizer(sent);
        int count = st.countTokens();

        for (int i = 0; i < count; i++) {

            String word = st.nextToken();

            if (Character.isUpperCase(word.charAt(0))) {
                freq = freq + 1;
            }
        }
    }

    public static void main(String[] abs) {

        Scanner z = new Scanner(System.in);

        System.out.println("Enter a sentence:");
        String sent = z.nextLine();

        cap_count cc = new cap_count();

        cc.count(sent);

        System.out.println("Frequency of capitalized words: " + cc.freq);
    }
}
