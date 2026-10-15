import java.util.*;
public class kap {
    public static void main(String[] abs) {
        Scanner z = new Scanner(System.in);
        System.out.println("Enter a number:");
        int n = z.nextInt();
        long sq = (long) n * n;
        int numDigits = String.valueOf(n).length();
        long div = 1;
        for (int i = 0; i < numDigits; i++) {
            div *= 10;
        }
        long firstp = sq / div;
        long secondp = sq % div;

        if (firstp + secondp == n) {
            System.out.println("Kaprekar");
        } else {
            System.out.println("Not Kaprekar");
        }
    }
}
