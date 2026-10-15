import java.util.*;
public class buzz {
    public static void main(String[] abs) {
        Scanner z = new Scanner(System.in);
        System.out.println("Enter a number:");
        int num = z.nextInt();
        String num2 = String.valueOf(num);
        int a = Character.getNumericValue(num2.charAt(0));
        if (num % 7 == 0 || a == 7) {
            System.out.println("Buzzzzzz");
        } else {
            System.out.println("Not buzz");
        }
    }
}
