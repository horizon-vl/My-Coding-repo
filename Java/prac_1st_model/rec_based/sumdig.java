import java.util.*;
public class sumdig{
    public int find_sum(int x){
        if(x==0){
            return 0;
        }
        return (x%10)+find_sum(x/10);
    }
    public static void main(String[]abs){
        Scanner z=new Scanner(System.in);
        System.out.println("Enter a number: ");
        int inp=z.nextInt();
        sumdig s=new sumdig();
        int result=s.find_sum(inp);
        System.out.println("Sum: "+result);
    }
}
