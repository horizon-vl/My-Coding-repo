import java.util.*;

class twin
{
    static boolean isPrime(int n)
    {
        if(n < 2)
            return false;

        for(int i = 2; i < n; i++)
        {
            if(n % i == 0)
                return false;
        }

        return true;
    }

    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int n = sc.nextInt();

        for(int i = n; ; i--)
        {
            if(isPrime(i) && isPrime(i + 2))
            {
                System.out.println("p1 = " + i);
                System.out.println("p2 = " + (i + 2));
                break;
            }
        }
    }
}
