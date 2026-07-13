import java.util.*;
public class Number {
    int n;
    Number(int nn){
        n=nn;
    }
    int factorial(int a){
        int fact=1;
        for (int i = 1; i <= a; i++) {
            fact *= i;
        }
        return fact;
    }
    void display(){
        System.out.println("Value of n = " + n);
    }
}

class Series extends Number {
    int sum;
    Series(int nn){
        super(nn);
        sum=0;
    }
    void calSum(){
        sum=0;
        for (int i = 1; i <= n; i++) {
            sum += factorial(i);
        }
    }
    void display(){
        super.display();
        System.out.println("Sum of series = " + sum);
    }
    public static void main(String[] args) {
        Scanner z = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int n = z.nextInt();

        Series s = new Series(n);
        s.calSum();
        s.display();
        z.close();
    }
}
