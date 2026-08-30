import java.util.*;

public class dig_combo
{
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int n = sc.nextInt();

        // Check whether number is a valid 3-digit number
        if (n <= 100 || n >= 1000)
        {
            System.out.println("Invalid Number");
            return;
        }

        int a = n / 100;
        int b = (n / 10) % 10;
        int c = n % 10;

        // Check for zero
        if (a == 0 || b == 0 || c == 0)
        {
            System.out.println("Invalid Number");
        }
        else
        {
            System.out.println(a + "" + b + "" + c);
            System.out.println(a + "" + c + "" + b);
            System.out.println(b + "" + a + "" + c);
            System.out.println(b + "" + c + "" + a);
            System.out.println(c + "" + a + "" + b);
            System.out.println(c + "" + b + "" + a);
        }
    }
}
