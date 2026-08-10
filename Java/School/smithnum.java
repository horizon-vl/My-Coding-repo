import java.util.*;
public class smithnum {
    int digitSum(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum = sum + digit;
            n = n / 10;
        }
        return sum;
    }
    boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        Scanner z = new Scanner(System.in);
        System.out.println("Enter a number:");
        int n = z.nextInt();
        int original = n;
        int sum1 = 0;
        int sum2 = 0;
        smithnum s = new smithnum();
        sum1 = s.digitSum(n);
        for (int i = 2; i <= n; i++) {
            while (n % i == 0 && s.isPrime(i)) {
                sum2 = sum2 + s.digitSum(i);
                n = n / i;
            }
        }
        if (sum1 == sum2) {
            System.out.println(original + " is a Smith number.");
        }
        else {
            System.out.println(original + " is not a Smith number.");
        }
    }
}
