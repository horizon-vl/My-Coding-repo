import java.util.*;

class fasc
{
    // Method to check whether a number is Fascinating
    static boolean isFascinating(int num)
    {
        String str = "" + num + (num * 2) + (num * 3);

        int freq[] = new int[10];

        // Count the frequency of each digit
        for(int i = 0; i < str.length(); i++)
        {
            int digit = str.charAt(i) - '0';

            // Ignore zero
            if(digit != 0)
                freq[digit]++;
        }

        // Digits 1 to 9 must occur exactly once
        for(int i = 1; i <= 9; i++)
        {
            if(freq[i] != 1)
                return false;
        }

        return true;
    }

    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter m: ");
        int m = sc.nextInt();

        System.out.print("Enter n: ");
        int n = sc.nextInt();

        // Validate input
        if(m >= n || m <= 99 || m >= 10000 ||
           n <= 99 || n >= 10000)
        {
            System.out.println("Invalid Input");
            return;
        }

        int count = 0;

        System.out.print("The Fascinating numbers are: ");

        for(int i = m; i <= n; i++)
        {
            if(isFascinating(i))
            {
                if(count > 0)
                    System.out.print(", ");

                System.out.print(i);
                count++;
            }
        }

        if(count == 0)
            System.out.print("NIL");

        System.out.println();

        System.out.println("The frequency of Fascinating numbers: "
                           + count);
    }
}
