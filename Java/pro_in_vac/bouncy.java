import java.util.*;

class bouncy
{
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Input: ");
        int n = sc.nextInt();

        if(n < 100)
        {
            System.out.println("Output: Not a Bouncy Number");
            return;
        }

        int num = n;
        int prev = num % 10;
        num = num / 10;

        boolean increasing = true;
        boolean decreasing = true;

        while(num > 0)
        {
            int digit = num % 10;

            if(digit < prev)
                increasing = false;

            if(digit > prev)
                decreasing = false;

            prev = digit;
            num = num / 10;
        }

        if(!increasing && !decreasing)
            System.out.println("Output: Bouncy Number");
        else
            System.out.println("Output: Not a Bouncy Number");
    }
}
