
public class MyList<E> implements ICollection<E> {
	node<E> head,tail;
	int count=0;
	long id;
	static long total_id=0;
	//there should not be over 2^64 MyLists
	public static class node<E>{
		E value;
		long belong_id=-1;
		node<E> next,pre;
		node(){}
		node(E x){
			value=x;
		}
	}
	//create an empty list
	MyList(){id=++total_id;}
	//create a list with one given element
	MyList(E x){
		id=++total_id;
		head=tail=new node<E>(x);
		head.belong_id=id;
		count=1;
	}
	public void add(E x) {
		if(x instanceof Comparable) {
			node<E> now=head;
			if(head==null) {head=tail=new node<E>(x);count=1;head.belong_id=id;return;}
			while(now!=null && ((Comparable)now.value).compareTo(x)<0)now=now.next;
			if(now==head) {//before head
				node<E> y=new node<E>(x);
				if(head!=null)head.pre=y;
				y.next=head;
				head=y;
				y.belong_id=id;
			}
			else if(now==null) {//after tail
				node<E> y=new node<E>(x);
				if(tail!=null)tail.next=y;
				y.pre=tail;
				tail=y;
				y.belong_id=id;
				}
			else {
				node<E> y=new node<E>(x);
				now.pre.next=y;
				y.pre=now.pre;
				y.next=now;
				now.pre=y;
				y.belong_id=id;
			}
		}
		else {
			node<E> y=new node<E>(x);
			tail.pre.next=y;
			y.pre=tail.pre;
			tail=y;
			y.belong_id=id;
		}
	count++;
	}
	public void clear() {
		head=tail=null;
		count=0;
	}
	public boolean isEmpty() {
		return count>0; 
	}
	public boolean remove(Object y) {
		if(!(y instanceof node<?>))return false;
		node<E>x;
		try {x=(node<E>)y;}
		catch(Exception e){return false;}
		if(x.belong_id!=id)return false;
		if(head==x) {
			if(tail==x) {
				head=tail=null;
				count=0;
			}
			else {
				head.next.pre=null;
				head=head.next;
				count--;
			}
		}
		else if(tail==x) {
				tail.pre.next=null;
				tail=tail.pre;
				count--;
		}
		else {
			x.pre.next=x.next;
			x.next.pre=x.pre;
			count--;
		}
		return true;
	}
	public boolean erase(E y) {
		node<E> now=head;
		boolean tag=false;
		while(now!=null) {
			if(now.value.equals(y)) {
				remove(now);
				tag=true;
			}
			now=now.next;
		}
		return tag;
	}
	public boolean contains(Object y) {
		if(!(y instanceof node<?>))return false;
		node<E>x;
		try {x=(node<E>)y;}
		catch(Exception e){return false;}
		return x.belong_id==id;
	}
	public int size() {
		return count;
	}
	public E[] toArray() {
		Object[] a=new Object[size()];
		int c=0;
		node<E> now=head;
		while(now!=null) {
			a[c]=now.value;
			c++;
			now=now.next;
		}
		return (E[])a;
	}
	public MyList<E> getUnion(ICollection<E> d){
		MyList<E>c,ret=new MyList<E>();
		try{c=(MyList<E>)d;}
		catch(Exception e){return null;}
		node<E>now=this.head;
		while(now!=null) {
			ret.add(now.value);
		}
		now=c.head;
		while(now!=null) {
			ret.add(now.value);
		}
		return ret;
	}
	public E getHead() {
		return head.value;
	}
	public E getTail() {
		return tail.value;
	}
	public E Find(E o) {
		node<E> now=head;
		while(now!=null) {
			if(now.value.equals(o)) {
				return now.value;
			}
		}
		return null;
	}
	public node<E> find_k(int k) {
		node<E> now=head;
		for(int i=1;i<k;i++) {
			if(now==null)return null;
			now=now.next;
		}
		return now;
	}
}
