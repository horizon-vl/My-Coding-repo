import java.util.*;
public class min_max{
    public static void main(String[]abs){
        Scanner z=new Scanner(System.in);
        System.out.println("Enter the size of array: ");
        int n=z.nextInt();
        int[]arr=new int[n];
        System.out.println("Enter "+n+" numbers: ");
        for(int i=0;i<n;i++){
            arr[i]=z.nextInt();
        }
        int min=arr[0],max=arr[0];

        for(int i=0;i<n;i++){
            if(arr[0]>max){
                max=arr[i];
            }
            else if(arr[i]<min){
                min=arr[i];
            }
            else{
                continue;
            }
        }
        System.out.println("Max:"+max+", Min:"+min);
    }
}
