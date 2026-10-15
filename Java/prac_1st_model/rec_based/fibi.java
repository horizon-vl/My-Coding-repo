import java.util.*;
public class fibi{
    int n;
    public fibi(int num){
        n=num;
    }
    public int findfibi(int x){
        if(x==0){
            return 0;
        }
        if(x==1){
            return 1;
        }
        return findfibi(x-1)+findfibi(x-2);
    }
    public static void main(String[]abs){
        Scanner z=new Scanner(System.in);
        System.out.println("Enter the term position(n): ");
        int input=z.nextInt();

        fibi f=new fibi(input);

        int result=f.findfibi(f.n);
        System.out.println("The "+f.n+"th fibi term is: "+result);
    }
}
