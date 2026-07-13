package Mix_letter;
import java.util.*;
public class Mix {

    String wrd;
    int len;
    String word;

    Mix() {
        wrd = "";
        len = wrd.length();
        word = "";
    }

    void feedword(){
        Scanner z = new Scanner(System.in);
        System.out.println("Enter two word: ");
        wrd = (z.nextLine()).toUpperCase();
    }

    void mix_word(Mix P,Mix Q){
        int len = Math.min(P.wrd.length(), Q.wrd.length());

        for(int i = 0; i < len; i++)
        {
            word = word + P.wrd.charAt(i);
            word = word + Q.wrd .charAt(i);
        }

        if(P.wrd.length() > len)
            word = word + P.wrd.substring(len);

        if(Q.wrd.length() > len)
            word = word + Q.wrd.substring(len);
    }

    void display(){
        System.out.println(word);
    }

    public static void main(String[]abs) {

        Mix P = new Mix();
        Mix Q = new Mix();
        P.feedword();
        Q.feedword();
        P.mix_word(P, Q);
        P.display();

    }
}
