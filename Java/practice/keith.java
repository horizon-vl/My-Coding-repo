import java.util.*;
public class keith{
    public static void main(String[]abs){
        Scanner z=new Scanner(System.in);
        System.out.println("Enter a number: ");
        int num=z.nextInt();
        int temp=num;
        int d=0;
        while(temp>0){
            d++;
            temp/=10;
        }
        int[]arr=new int[d];
        temp=num;
        for(int i=0;i<d;i++){
            arr[i]=temp%10;
            temp/=10;
        }
    }
}
