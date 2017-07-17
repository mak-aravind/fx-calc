package mak.fxcalc.util;

public class ParsedObject<T> {

	private T t;
	
	public ParsedObject(T t) {
		this.t = t;
	}
	
	public T getTableData(){
		return t;
	}
}