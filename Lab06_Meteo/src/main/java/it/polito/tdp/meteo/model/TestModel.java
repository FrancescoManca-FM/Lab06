package it.polito.tdp.meteo.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
		System.out.println(m.getUmiditaMedia(6));
		System.out.println(m.trovaSequenza(2).toString());

	}

}
