import java.util.*;
public class smith{
    static int sumofdig(int n){
        int sum=0;
        while(n>0){
            sum+=n%10;
            n/=10;
        }
        return sum;
    }
    static boolean isPrime(int n){
        if(n<=1)
            return false;
        for(int i=2;i<=n;i++){
            if(n%i==0){
                return false;
            }
        }
        return true;
    }
    public static void main(String[]abs){
        Scanner z=new Scanner(System.in);
        System.out.println("Enter a number:");
        int n=z.nextInt();

        if(isPrime(n)){
            System.out.println("Not smith");
            return;
        }

        int sumNum=sumofdig(n);
        int sumPrimeFactor=0;
        int temp=n;
        int i=2;

        while(temp>1){
            if(temp%i==0){
                sumPrimeFactor+=sumofdig(i);
                temp/=i;
            }
            else{
                i++;
            }
        }
        if(sumNum==sumPrimeFactor){
            System.out.println(n+" is a smith number.");
        }
        else{
            System.out.println(n+" is not a smith number.");
        }
    }
}
