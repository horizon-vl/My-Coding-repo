import java.util.*;
public class menu_driven{
    public void fibin(){
            int a = 0, b = 1;
            int cou = 0;
            while (cou < 1000) {
                int count = 0;
                for (int i = 1; i <= a; i++) {
                    if (a % i == 0) {
                        count++;
                    }
                }
                if (count == 2) {
                    System.out.println(a);
                    cou++;
                }
                int c = a + b;
                a = b;
                b = c;
        }
    }
    public void primef(int n){
        for (int i = 2; i <= n; i++) {
             if (n % i == 0) {
                int count = 0;
                for (int j = 1; j <= i; j++) {
                    if (i % j == 0) {
                        count++;
                    }
                }
                if (count == 2) {
                    System.out.print(i + " ");
                }
            }
        }
    }

    public static void main(String[] abs) {
        Scanner z = new Scanner(System.in);
        menu_driven m = new menu_driven();
        boolean is_running = true;
        while (is_running) {
            System.out.println("\nEnter a choice:");
            System.out.println("1 - Fibonacci prime numbers");
            System.out.println("2 - Prime factors");
            System.out.println("3 - Exit");
            char ch = z.next().charAt(0);
            switch (ch) {
                case '1':
                    m.fibin();
                    break;
                case '2':
                    System.out.println("Enter a number:");
                    int n = z.nextInt();
                    m.primef(n);
                    break;
                case '3':
                    is_running = false;
                    System.out.println("Program terminated.");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
        z.close();
    }
}
