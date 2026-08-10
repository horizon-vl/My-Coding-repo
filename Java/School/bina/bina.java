package bina;
import java.util.*;
public class bina{
    public static void main(String[]abs){
        Scanner z=new Scanner(System.in);
        System.out.println("Enter 5 elements in order: ");
        int[]arr=new int[5];
        for(int i=0;i<5;i++){
            arr[i]=z.nextInt();
        }
        int min=0,max=4,mid=0;

        System.out.println("Enter the number to be searched:");
        int num=z.nextInt();
        while(min<max){
            mid=(min+max)/2;
            if(arr[mid]<num){
                min=mid+1;
            }
            else if(arr[mid]>num){
                max=mid-1;
            }
            else{
                break;
            }
        }
        if(arr[mid]==num){
            System.out.println("Found");
        }
        else{
            System.out.println("Not Found");
        }
    }
}
