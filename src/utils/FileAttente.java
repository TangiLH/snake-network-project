package utils;

import java.util.Vector;

public class FileAttente {
	private Vector<Object> file;
	
	public FileAttente() {
		this.file=new Vector<>(); 
	}
	
	public void add(Object o) {
		file.addFirst(o);
	}
	
	public Object pop() {
		Object o=file.getLast();
		file.removeLast();
		return o;
	}
	
	public Object get() {
		Object o=file.getLast();
		return o;
	}
	
	public int size() {
		return file.size();
	}
}
