import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
public class MyDate implements Comparable{
	int year,month,day;
	String content;
	MyDate(int yy,int mm,int dd,String ss){
		year=yy;month=mm;day=dd;content=ss;
	}
	MyDate(Date e,String ss){
		year=e.getYear();
		month=e.getMonth()+1;
		day=e.getDate();
		content=ss;
	}
	MyDate(Calendar e,String ss){
		year=e.YEAR;
		month=e.MONTH+1;
		day=e.DATE;
		content=ss;
	}
	MyDate(LocalDateTime e,String ss){
		year=e.getYear();
		month=e.getMonthValue();
		day=e.getDayOfMonth();
		content=ss;
	}
	public int compareTo(Object f) {
		if (!(f instanceof MyDate))return -1;
		MyDate e=(MyDate)f;
		long x=this.hashCode();
		long y=e.hashCode();
		if(x<y)return -1;
		else if(x==y)return 0;
		else return 1;
	}
	@Override
	public boolean equals(Object e) {
		if(!(e instanceof MyDate))return false;
		long x=this.hashCode();
		long y=e.hashCode();
		if(x==y)return true;
		else return false;
	}
	public int hashCode() {
		return year*13*32+month*32+day;
	}
	private LocalDateTime trans() {
		return LocalDateTime.of(year,month,day,0,0,0);
	}
	public void addDay(int e) {
		LocalDateTime ldt=trans();
		ldt.plusDays(e);
		year=ldt.getYear();
		month=ldt.getMonthValue();
		day=ldt.getDayOfMonth();
	}
	public void addMonth(int e) {
		LocalDateTime ldt=trans();
		ldt.plusMonths(e);
		year=ldt.getYear();
		month=ldt.getMonthValue();
		day=ldt.getDayOfMonth();
	}
	public void addYear(int e) {
		LocalDateTime ldt=trans();
		ldt.plusYears(e);
		year=ldt.getYear();
		month=ldt.getMonthValue();
		day=ldt.getDayOfMonth();
	}
	public void subDay(int e) {
		LocalDateTime ldt=trans();
		ldt.minusDays(e);
		year=ldt.getYear();
		month=ldt.getMonthValue();
		day=ldt.getDayOfMonth();
	}
	public void subMonth(int e) {
		LocalDateTime ldt=trans();
		ldt.minusMonths(e);
		year=ldt.getYear();
		month=ldt.getMonthValue();
		day=ldt.getDayOfMonth();
	}
	public void subYear(int e) {
		LocalDateTime ldt=trans();
		ldt.minusYears(e);
		year=ldt.getYear();
		month=ldt.getMonthValue();
		day=ldt.getDayOfMonth();
	}
}
