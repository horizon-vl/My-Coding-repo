import java.util.*;

class evil_num
{
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Input: ");
        int n = sc.nextInt();

        int num = n;
        int count = 0;
        String binary = "";

        while(num > 0)
        {
            int r = num % 2;
            binary = r + binary;

            if(r == 1)
                count++;

            num = num / 2;
        }

        System.out.println("Binary Equivalent: " + binary);
        System.out.println("No. of 1's: " + count);

        if(count % 2 == 0)
            System.out.println("Output: Evil Number");
        else
            System.out.println("Output: Not an Evil Number");
    }
}
