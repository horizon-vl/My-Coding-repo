import java.util.Scanner;

class trig_num {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the value of n: ");
        int n = sc.nextInt();

        int sum = 0;

        System.out.println("The triangular numbers are:");

        for (int i = 1; sum + i <= n; i++) {
            sum = sum + i;
            System.out.print(sum + " ");
        }

        sc.close();
    }
}
