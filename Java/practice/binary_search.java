import java.util.*;

public class binary_search {
    public static void main(String[] abs) {

        Scanner z = new Scanner(System.in);

        int[] arr = new int[5];

        System.out.println("Enter 5 elements in ascending order:");

        for (int i = 0; i < 5; i++) {
            arr[i] = z.nextInt();
        }

        int min = 0;
        int max = 4;
        int mid = 0;

        System.out.println("Enter a number to be searched:");
        int num = z.nextInt();

        boolean found = false;

        while (min <= max) {

            mid = (min + max) / 2;

            if (arr[mid] < num) {
                min = mid + 1;
            }
            else if (arr[mid] > num) {
                max = mid - 1;
            }
            else {
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("Found at position " + (mid + 1));
        }
        else {
            System.out.println("Not found");
        }
    }
}
