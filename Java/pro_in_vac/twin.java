import java.util.*;

class twin
{
    static boolean isPrime(int n)
    {
        if(n < 2)
            return false;

        for(int i = 2; i <= Math.sqrt(n); i++)
        {
            if(n % i == 0)
                return false;
        }

        return true;
    }

    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Number read is: ");
        int n = sc.nextInt();

        int p1 = 0, p2 = 0;
        int minDistance = Integer.MAX_VALUE;

        // Search twin prime pairs around N
        for(int i = 2; i <= n + 100; i++)
        {
            if(isPrime(i) && isPrime(i + 2))
            {
                int distance = Math.min(
                    Math.abs(n - i),
                    Math.abs(n - (i + 2))
                );

                if(distance < minDistance)
                {
                    minDistance = distance;
                    p1 = i;
                    p2 = i + 2;
                }
            }
        }

        System.out.println("p1 = " + p1 + ", p2 = " + p2);
    }
}
