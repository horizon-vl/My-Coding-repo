import java.util.Scanner;

class comp_magic
{
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter m: ");
        int m = sc.nextInt();

        System.out.print("Enter n: ");
        int n = sc.nextInt();

        if (m >= n || m <= 0 || n <= 0)
        {
            System.out.println("Invalid input");
            return;
        }

        int count = 0;

        System.out.println("The composite magic numbers are:");

        for (int i = m; i <= n; i++)
        {
            // Check whether the number is composite
            int factors = 0;

            for (int j = 1; j <= i; j++)
            {
                if (i % j == 0)
                    factors++;
            }

            // Find the eventual sum of digits
            int num = i;

            while (num > 9)
            {
                int sum = 0;

                while (num > 0)
                {
                    sum = sum + num % 10;
                    num = num / 10;
                }

                num = sum;
            }

            // Check composite and magic
            if (factors > 2 && num == 1)
            {
                System.out.print(i + " ");
                count++;
            }
        }

        System.out.println();
        System.out.println("Frequency of composite magic numbers: " + count);
    }
}
