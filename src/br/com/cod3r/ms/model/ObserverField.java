package br.com.cod3r.ms.model;

@FunctionalInterface
public interface ObserverField {
	
	public void eventOcurred(Field f, FieldEvents e);
	
}
