 import java.util.*;
 public class keith
 {
     public static void main(String args[])
     {
         Scanner sc = new Scanner(System.in);
         System.out.print("Enter a natural number: ");
         int n = sc.nextInt();
         int original = n;
         int temp = n;
         int d = 0;
         while (temp > 0)
         {
             d++;
             temp = temp / 10;
         }
         int arr[] = new int[d];
         temp = n;
         for (int i = d - 1; i >= 0; i--)
         {
             arr[i] = temp % 10;
             temp = temp / 10;
         }
         int sum = 0;
         boolean flag = false;
         while (true)
         {
             sum = 0;
             for (int i = 0; i < d; i++)
             {
                 sum = sum + arr[i];
             }
             if (sum == original)
             {
                 flag = true;
                 break;
             }
             if (sum > original)
             {
                 break;
             }
             for (int i = 0; i < d - 1; i++)
             {
                 arr[i] = arr[i + 1];
             }
             arr[d - 1] = sum;
         }
         if (flag)
         {
             System.out.println(original + " is a Keith number.");
         }
         else
         {
             System.out.println(original + " is not a Keith number.");
         }
     }
 }
