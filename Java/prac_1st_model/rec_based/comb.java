import java.util.Scanner;
public class comb {
    int n, r;
    public comb(int numN, int numR) {
        n = numN;
        r = numR;
    }

    public int calculateNCR(int n, int r) {
        if (r == 0 || r == n) {
            return 1;
        }
        if (r > n) {
            return 0;
        }
        return calculateNCR(n - 1, r - 1) + calculateNCR(n - 1, r);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter total items (n): ");
        int n = sc.nextInt();
        System.out.print("Enter items to choose (r): ");
        int r = sc.nextInt();
        if (n < r || n < 0 || r < 0) {
            System.out.println("Invalid input! Ensure n >= r >= 0.");
            return;
        }
        comb obj = new comb(n, r);
        int result = obj.calculateNCR(obj.n, obj.r);
        System.out.println("The value of " + obj.n + "C" + obj.r + " is: " + result);
    }
}
