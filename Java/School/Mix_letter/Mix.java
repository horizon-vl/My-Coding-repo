package Mix_letter;

import java.util.*;

public class Mix {

    static Scanner z = new Scanner(System.in);

    String wrd;
    String word;

    Mix() {
        wrd = "";
        word = "";
    }

    void feedword() {
        System.out.print("Enter a word: ");
        wrd = z.nextLine().toUpperCase();
    }

    void mix_word(Mix P, Mix Q) {

        word = ""; // Clear previous contents

        int len = Math.min(P.wrd.length(), Q.wrd.length());

        for (int i = 0; i < len; i++) {
            word = word + P.wrd.charAt(i);
            word = word + Q.wrd.charAt(i);
        }

        if (P.wrd.length() > len)
            word = word + P.wrd.substring(len);

        if (Q.wrd.length() > len)
            word = word + Q.wrd.substring(len);
    }

    void display() {
        System.out.println("Mixed Word: " + word);
    }

    public static void main(String args[]) {

        Mix P = new Mix();
        Mix Q = new Mix();
        Mix R = new Mix();

        P.feedword();
        Q.feedword();

        R.mix_word(P, Q);
        R.display();

        z.close();
    }
}
