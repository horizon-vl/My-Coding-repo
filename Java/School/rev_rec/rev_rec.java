package rev_rec;
import java.util.*;
public class rev_rec {
    int rev = 0;
    void rec(int n) {
        if (n == 0)
            return;
        rev = rev * 10 + (n % 10);
        rec(n / 10);
    }
    public static void main(String[] args) {
        Scanner z = new Scanner(System.in);
        System.out.println("Enter a number:");
        int num = z.nextInt();
        rev_rec r = new rev_rec();
        r.rec(num);
        System.out.println("Reversed number: " + r.rev);
        z.close();
    }
}
