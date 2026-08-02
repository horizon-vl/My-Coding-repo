package DeciBin;
import java.util.*;
public class DeciBin{
    static Scanner z=new Scanner(System.in);
    int num;
    String bin;
        DeciBin(){
            num=0;
            bin="";
        }

        void getnum(){
            System.out.println("Enter a number:");
            num=z.nextInt();
        }

        void convert(int num){
            if(num>0){
                int quo=num/2;
                int rem=num%2;
                bin=rem+bin;
                convert(quo);
            }
        }

        void display(){
            System.out.println("Decimal:"+num);
            System.out.println("Binary equivalent:"+bin);
        }

        public static void main(String[]abs){
            DeciBin d=new DeciBin();
            d.getnum();
            d.convert(d.num);
            d.display();
        }
}
