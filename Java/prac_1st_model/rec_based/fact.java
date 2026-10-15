import java.util.*;
public class fact{
    int n;
    public fact(int num){
        n=num;
    }
    public int findfact(int x){
        if(x==0||x==1){
            return 1;
        }
        return x*findfact(x-1);
    }
    public static void main(String[]abs){
        Scanner z=new Scanner(System.in);
        System.out.println("Enter a number:");
        int input=z.nextInt();
        fact f=new fact(input);
        int result=f.findfact(f.n);
        System.out.println("Factorial of "+f.n+" is: "+result);
    }
}
