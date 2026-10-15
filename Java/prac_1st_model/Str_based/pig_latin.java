import java.util.*;
public class pig_latin{
    String str;
    public pig_latin(String s){
        str=s.toUpperCase();
    }
    public void conv_pig(){
        int len=str.length();
        int vow=-1;
        for(int i=0;i<len;i++){
            char ch=str.charAt(i);
            if(ch=='A'||ch=='E'||ch=='I'||ch=='O'||ch=='U'){
                vow=i;
                break;
            }
        }
        String pigword="";
        if(vow==0){
            pigword=str+"AY";
        }
        else if(vow==-1){
            pigword=str+"AY";
        }
        else{
            String consta=str.substring(0,vow);
            String rest=str.substring(vow);
            pigword=rest+consta+"AY";
        }
        System.out.println("Original word:"+str);
        System.out.println("Pig Latin form:"+pigword);
    }
    public static void main(String[]abs){
        Scanner z=new Scanner(System.in);
        System.out.println("Enter a single word: ");
        String input=z.next();

        pig_latin p=new pig_latin(input);
        p.conv_pig();
    }
}
