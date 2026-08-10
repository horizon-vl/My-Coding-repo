package Ni;
import java.util.*;
public class Ni {
    boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] abs) {
        Scanner z = new Scanner(System.in);
        System.out.println("Enter an even number less than 100:");
        int N = z.nextInt();
        Ni obj = new Ni();
        for (int i = 2; i <= N; i++) {
            for (int j = i; j <= N; j++) {
                if (obj.isPrime(i) && obj.isPrime(j)) {
                    if (i + j == N) {
                        System.out.println("[" + i + "," + j + "]");
                    }
                }
            }
        }
    }
}
