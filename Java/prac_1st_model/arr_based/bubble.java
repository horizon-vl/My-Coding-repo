import java.util.*;
public class bubble{
    int arr[];
    int n;
    public bubble(int size){
        n=size;
        arr=new int[n];
        Scanner z=new Scanner(System.in);
        System.out.println("Enter "+n+" elements: ");
        for(int i=0;i<n;i++){
            arr[i]=z.nextInt();
        }
    }
    public void sort(){
        for(int i=0;i<n-1;i++){
            for(int j=0;j<n-i-1;j++){
                if(arr[j]>arr[j+1]){
                    int temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
    }

    public void display(){
        System.out.println("Sorted Array: ");
        for(int i=0;i<n;i++){
            System.out.println(arr[i]+" ");
        }
        System.out.println();
    }

    public static void main(String[]abs){
        Scanner z=new Scanner(System.in);
        System.out.println("Enter the size of an array: ");
        int size=z.nextInt();

        bubble b=new bubble(size);

        b.sort();
        b.display();
    }
}
