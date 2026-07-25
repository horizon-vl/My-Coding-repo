package DeciHex;
import java.util.*;
public class DeciHex{
    static Scanner z=new Scanner(System.in);
    int num;
    String hexa;
    DeciHex(){
        num=0;
        hexa="";
    }
    int getNum(){
        System.out.println("Enter a positive number:");
        num=z.nextInt();
        return num;
    }
    void convert(int n){
        char[]eq={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

        if(n>0){
            int quo=n/16;
            int rem=n%16;
            hexa=eq[rem]+hexa;
            convert(quo);
        }
    }
    void display(){
        System.out.println("Decimal form: "+num);
        System.out.println("HexaDecimal: "+hexa);
    }
    public static void main(String[]abs){
        DeciHex d=new DeciHex();
        d.getNum();
        d.convert(d.num);
        d.display();
    }
}
