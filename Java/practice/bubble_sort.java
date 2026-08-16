import java.util.*;
public class bubble_sort{
    public static void main(String[]abs){
        Scanner z=new Scanner(System.in);
        System.out.println("Enter 5 elements: ");
        int[]arr=new int[5];
        for(int i=0;i<5;i++){
            arr[i]=z.nextInt();
        }
        int temp;
        for(int i=0;i<5-1;i++){
            for(int j=0;j<5-1-i;j++){
                if(arr[j]>arr[j+1]){
                    temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
        System.out.println("Sorted: ");
        for(int i=0;i<5;i++){
            System.out.println(arr[i]+" ");
        }
    }
}
