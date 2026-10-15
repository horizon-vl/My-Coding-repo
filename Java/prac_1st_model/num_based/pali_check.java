import java.util.*;
public class pali_check{
    public static void main(String[]abs){
        Scanner z=new Scanner(System.in);
        System.out.println("Enter a number:");
        int a=z.nextInt();
        int temp=a;
        int rev=0;
        while(a>0){
            rev=rev*10+(a%10);
            a/=10;
        }
        if(rev==temp){
            System.out.println("Palindrome...");
        }
        else{
            System.out.println("Not Palindrome...");
        }
    }
}
