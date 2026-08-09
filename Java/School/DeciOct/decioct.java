package DeciOct;
import java.util.*;
public class decioct{
    static Scanner z=new Scanner(System.in);
    int num;
    String oct;
    decioct(){
            num=0;
            oct="";
        }

        void getnum(){
            System.out.println("Enter a number:");
            num=z.nextInt();
        }

        void convert(int num){
            if(num>0){
                int quo=num/8;
                int rem=num%8;
                oct=rem+oct;
                convert(quo);
            }
        }

        void display(){
            System.out.println("Decimal:"+num);
            System.out.println("Octal equivalent:"+oct);
        }

        public static void main(String[]abs){
            decioct d=new decioct();
            d.getnum();
            d.convert(d.num);
            d.display();
        }
}
