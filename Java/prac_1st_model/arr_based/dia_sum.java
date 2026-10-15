import java.util.*;
public class dia_sum{
    int arr[][];
    int n;
    dia_sum(int size){
        n = size;
        arr=new int[n][n];
        Scanner z = new Scanner(System.in);
        System.out.println("Enter elements for the " + n + " x " + n + " matrix:");
        for (int i = 0; i < n; i++) {
             for (int j = 0; j < n; j++) {
                arr[i][j] = z.nextInt();
            }
        }
    }
    public void calculateDiagonals() {
            int leftSum = 0;
            int rightSum = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j) {
                        leftSum += arr[i][j];
                    }
                    if (i + j == n - 1) {
                        rightSum += arr[i][j];
                    }
                }
            }

            System.out.println("Sum of Left Diagonal = " + leftSum);
            System.out.println("Sum of Right Diagonal = " + rightSum);
        }
        public static void main(String[] args) {
                Scanner sc = new Scanner(System.in);
                System.out.print("Enter the size of the square matrix (n): ");
                int size = sc.nextInt();
                dia_sum d = new dia_sum(size);
                d.calculateDiagonals();
            }
}
