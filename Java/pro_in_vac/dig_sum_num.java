import java.util.Scanner;

class dig_sum_num
{
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter M: ");
        int M = sc.nextInt();

        System.out.print("Enter N: ");
        int N = sc.nextInt();

        if (M < 100 || M > 10000 || N <= 0 || N >= 100)
        {
            System.out.println("Invalid Input");
        }
        else
        {
            int num = M + 1;

            while (true)
            {
                int temp = num;
                int sum = 0;

                while (temp > 0)
                {
                    sum = sum + temp % 10;
                    temp = temp / 10;
                }

                if (sum == N)
                    break;

                num++;
            }

            int count = 0;
            int temp = num;

            while (temp > 0)
            {
                count++;
                temp = temp / 10;
            }

            System.out.println("The required number = " + num);
            System.out.println("Total number of digits = " + count);
        }
    }
}
