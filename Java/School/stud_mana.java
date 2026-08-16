import java.util.*;
public class stud_mana{
    static Scanner z=new Scanner(System.in);
    String name;int mark1,mark2,mark3;
    int[]avg;String[]names;
    stud_mana(){
        name="";
        mark1=0;mark2=0;mark3=0;
    }
    public void getStudents(){
        System.out.println("Enter 5 student names and 3 of their marks:");
        for(int i=0;i<5;i++){
            names[i]=z.next();
            mark1=z.nextInt();
            mark2=z.nextInt();
            mark3=z.nextInt();
            avg[i]=(mark1+mark2+mark3)/3;
        }
    }

}
