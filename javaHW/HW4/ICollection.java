public interface ICollection<E> {
	void add(E o);
	boolean remove(Object o);
	boolean contains(Object o);
	boolean isEmpty();
	Object Find(E o);
	boolean erase(E o);
	void clear();
	int size();
	ICollection<E> getUnion(ICollection<E> c);
	E[] toArray();
}
