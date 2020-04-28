package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	private Citta Torino;
	private Citta Genova;
	private Citta Milano;
	
	
	private List<Citta> bestSoluzione;
	private double bestCosto;
	private MeteoDAO dao;

	public Model() {
		dao = new MeteoDAO();
	}

	// of course you can change the String output with what you think works best
	public List<Double> getUmiditaMedia(int mese) {
		List<Rilevamento> rilevamenti = dao.getAllRilevamenti(mese);
		List<Rilevamento> torino = new ArrayList<>();
		List<Rilevamento> milano = new ArrayList<>();
		List<Rilevamento> genova = new ArrayList<>();
		List<Double> risultato = new ArrayList<>();
		
		for(Rilevamento r : rilevamenti ) {
			if(r.getLocalita().equals("Torino")) {
				torino.add(r);
			}else if(r.getLocalita().equals("Milano")) {
				milano.add(r);
			}else {
				genova.add(r);
			}
		}
		double mediaTorino = calcolaUmiditaMedia(torino);
		double mediaMilano = calcolaUmiditaMedia(milano);
		double mediaGenova = calcolaUmiditaMedia(genova);
		
		risultato.add(mediaTorino);
		risultato.add(mediaMilano);
		risultato.add(mediaGenova);
		
		return risultato;
	}
	
	private double calcolaUmiditaMedia(List<Rilevamento> citta) {
		double somma = 0.0;
		int i = 0;
		for(Rilevamento r : citta) {
			somma += r.getUmidita();
			i++;
		}
		return somma/i;
	}

	// of course you can change the String output with what you think works best
	public List<Citta> trovaSequenza(int mese) {
		bestSoluzione = null;
		bestCosto = 100000000.0;
		Torino = new Citta("Torino", dao.getAllRilevamentiLocalitaMese(mese, "Torino"));
		Genova =  new Citta("Genova", dao.getAllRilevamentiLocalitaMese(mese, "Genova"));
		Milano = new Citta("Milano", dao.getAllRilevamentiLocalitaMese(mese, "Milano"));
		
		List<Citta> parziale = new ArrayList<>();
		ricorsiva(parziale,0);
		
		return bestSoluzione;
	}
	
	
	private void ricorsiva(List<Citta> parziale, int livello) {
		//Caso terminale
		
		if(livello == NUMERO_GIORNI_TOTALI && parziale!=null) {
			
			if(isCorretta(parziale)) {
				if(calcolaCosto(parziale)<bestCosto) {
					this.bestSoluzione = new ArrayList<>(parziale);
					bestCosto = calcolaCosto(parziale);
					return;
				}else {
					return;
				}
		}else {
			return;
		}
		}
		
		//genero sotto problema
		if(Torino.getCounter()<6) {
			
			parziale.add(Torino);
			Torino.increaseCounter();
			ricorsiva(parziale, livello+1);
			Torino.setCounter(Torino.getCounter()-1);
			parziale.remove(parziale.size()-1);	
		}
		
		if(Milano.getCounter()<6) {
			
			parziale.add(Milano);
			Milano.increaseCounter();;
			ricorsiva(parziale, livello+1);
			Milano.setCounter(Milano.getCounter()-1);
			parziale.remove(parziale.size()-1);
		
		}
		
		if(Genova.getCounter()<6) {
			parziale.add(Genova);
			Genova.increaseCounter();
			ricorsiva(parziale, livello+1);
			Genova.setCounter(Genova.getCounter()-1);
			parziale.remove(parziale.size()-1);
		}
		
	}
	
	private boolean isCorretta(List<Citta> parziale) {
		int j = 0;
		boolean giusta = true;
		for(int i=0; i<14; i++) {
			if(parziale.get(i).equals(parziale.get(i+1))) {
				j++;
			}else if(j<2) {
				j=0;
				giusta = false;
			}else {
				j=0;
			}
		}
		if(!parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-2)) ||
				!parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-3))	) {
			giusta = false;
		}
		return giusta;
	}

	private double calcolaCosto(List<Citta> sequenza) {
		
		double totale = 0;
		for(int i=0; i<15; i++) {
			totale += sequenza.get(i).getRilevamenti().get(i).getUmidita(); 
		}
		for(int j=3; j<15;j++) {
			if(!sequenza.get(j).getNome().equals(sequenza.get(j-1).getNome())) {
				totale += 100;
			}
		}
		return totale;
	}

}
