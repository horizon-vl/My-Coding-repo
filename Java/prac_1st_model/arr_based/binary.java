import java.util.*;
public class binary{
    int arr[];
    int n;
    binary(int size){
        n=size;
        arr = new int[n];
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter " + n + " elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        Arrays.sort(arr);

        System.out.print("Array after sorting: ");
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    public void search(int key){
        int lb=0;
        int ub=n-1;
        boolean found=false;
        int pos=-1;
        while(lb<=ub){
            int mid=(lb+ub)/2;
            if(arr[mid]==key){
                found=true;
                pos=mid;
                break;
            }
            else if(arr[mid]<key){
                lb=mid+1;
            }
            else{
                ub=mid-1;
            }
        }
        if(found)
            System.out.println("Success! Element " + key + " found at index position " + pos);
        else
             System.out.println("Sorry, element " + key + " is not present in the array.");
    }
    public static void main(String[]abs){
        Scanner z=new Scanner(System.in);
        System.out.println("Enter the size of an array:");
        int size=z.nextInt();
        binary b=new binary(size);
        System.out.print("Enter the element you want to search for: ");
        int key = z.nextInt();
        b.search(key);
    }
}
