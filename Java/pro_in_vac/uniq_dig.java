import java.util.Scanner;

class uniq_dig
{
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter m: ");
        int m = sc.nextInt();

        System.out.print("Enter n: ");
        int n = sc.nextInt();

        int count = 0;

        System.out.println("The unique-digit integers are:");

        for (int i = m; i <= n; i++)
        {
            int num = i;
            boolean unique = true;

            while (num > 0)
            {
                int digit = num % 10;
                int temp = num / 10;

                while (temp > 0)
                {
                    if (digit == temp % 10)
                    {
                        unique = false;
                        break;
                    }

                    temp = temp / 10;
                }

                if (!unique)
                    break;

                num = num / 10;
            }

            if (unique)
            {
                System.out.print(i + ", ");
                count++;
            }
        }

        System.out.println();
        System.out.println("Frequency of unique-digit integers is: " + count);
    }
}
