import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
public class DaysMatter {
	static MyList<MyDate> memory=new MyList<MyDate>();
	static int T;
	public static void main(String args[]) {
		Scanner in=new Scanner(System.in);
		//0=add someday,1=add today,2=erase memory on someday,
		//3=delete kth memory,4=return kth memory,5=clear,6=get array and output
		T=in.nextInt();
		for(int i=1;i<=T;i++) {
			//System.out.println("Executing "+i);
			int a=in.nextInt();
			if(a==0) {
				int y,m,d;String c;
				y=in.nextInt();
				m=in.nextInt();
				d=in.nextInt();
				c=in.next();
				memory.add(new MyDate(y,m,d,c));
			}
			else if(a==1) {
				LocalDateTime ldt=LocalDateTime.now();
				String c=in.next();
				memory.add(new MyDate(ldt,c));
			}
			else if(a==2) {
				int y,m,d;
				y=in.nextInt();
				m=in.nextInt();
				d=in.nextInt();
				memory.erase(new MyDate(y,m,d,""));
			}
			else if(a==3) {
				int k;
				k=in.nextInt();
				memory.remove(memory.find_k(k));
			}
			else if(a==4) {
				int k;
				k=in.nextInt();
				MyDate x=memory.find_k(k).value;
				System.out.println(x.year+"年"+x.month+"月"+x.day+"日："+x.content);
				System.out.println("");
			}
			else if(a==5) {
				memory.clear();
			}
			else if(a==6) {
				Object[] s=memory.toArray();
				for(Object t:s) {
					MyDate x=(MyDate)t;
					LocalDateTime t1=LocalDateTime.now();
					System.out.println(x.year+"年"+x.month+"月"+x.day+"日："+x.content);
				}
				System.out.println("");
			}
			else System.out.println("Invalid Operations!");
		}
		
	}
}
/*
Sample:
19
1 hello!
0 2008 1 1 hi!
0 2009 2 2 hi
6
0 2020 11 9 yello!
6
2 2008 1 7
6
0 2009 2 2 yes?
6
2 2009 2 2
6
4 2
3 3
6
5
6
1 whoisthere?
6
 */