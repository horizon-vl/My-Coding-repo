import java.util.*;

public class mix {

    String wrd;
    static Scanner z = new Scanner(System.in);
    String word;

    mix() {
        wrd = "";
        word = "";
    }

    void feedword() {
        System.out.println("Enter a word:");
        wrd = z.nextLine().toUpperCase();
    }

    void mix_word(mix P, mix Q) {

        word = "";

        int len = Math.min(P.wrd.length(), Q.wrd.length());

        for (int i = 0; i < len; i++) {
            word = word + P.wrd.charAt(i);
            word = word + Q.wrd.charAt(i);
        }

        if (P.wrd.length() > len) {
            word = word + P.wrd.substring(len);
        }

        if (Q.wrd.length() > len) {
            word = word + Q.wrd.substring(len);
        }
    }

    void display() {
        System.out.println("Mixed word: " + word);
    }

    public static void main(String[] abs) {

        mix P = new mix();
        mix Q = new mix();

        P.feedword();
        Q.feedword();

        P.mix_word(P, Q);

        P.display();
    }
}
