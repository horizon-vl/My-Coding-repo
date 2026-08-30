import java.util.Scanner;

class smith
{
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int n = sc.nextInt();

        int temp = n;
        int sumDigits = 0;
        int sumFactors = 0;
        int factors = 0;

        // Sum of digits of the original number
        while (temp > 0)
        {
            sumDigits = sumDigits + temp % 10;
            temp = temp / 10;
        }

        // Find prime factors
        temp = n;

        for (int i = 2; i <= temp; i++)
        {
            while (temp % i == 0)
            {
                factors++;

                int x = i;

                // Sum of digits of the prime factor
                while (x > 0)
                {
                    sumFactors = sumFactors + x % 10;
                    x = x / 10;
                }

                temp = temp / i;
            }
        }

        // A Smith number must be composite
        if (factors > 1 && sumDigits == sumFactors)
            System.out.println(n + " is a Smith number.");
        else
            System.out.println(n + " is not a Smith number.");
    }
}
