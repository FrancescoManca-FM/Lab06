/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.meteo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class FXMLController {

	private Model model;
	private ObservableList<String> mesi;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxMese"
    private ChoiceBox<String> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnUmidita"
    private Button btnUmidita; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcola"
    private Button btnCalcola; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaSequenza(ActionEvent event) {
    	txtResult.clear();
    	String mese = boxMese.getValue();
    	if(mese == null) {
    		txtResult.appendText("Si prega di scegliere un mese dalla Choice Box");
    		return;
    	}
    	List<Citta> sequenza = model.trovaSequenza(Integer.parseInt(mese));
    	txtResult.appendText("La sequenza migliore per il mese "+mese+"/13 è:\n");
    	for(Citta a : sequenza) {
    		txtResult.appendText(a.getNome()+"\n");
    	}
    	
    }

    @FXML
    void doCalcolaUmidita(ActionEvent event) {
    	txtResult.clear();
    	String mese = boxMese.getValue();
    	if(mese == null) {
    		txtResult.appendText("Si prega di scegliere un mese dalla Choice Box");
    		return;
    	}
    	List<Double> medie = model.getUmiditaMedia(Integer.parseInt(mese));
    	txtResult.appendText("La media dei livelli di umidità nel mese "+mese+"/13 è:\n" );
    	txtResult.appendText("Torino: "+medie.get(0)+"\nMilano: "+medie.get(1)+"\nGenova: "+medie.get(2));
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;	
		mesi = FXCollections.observableArrayList();
		mesi.addAll("1","2","3","4","5","6","7","8","9","10","11","12");
		boxMese.setItems(mesi);
		
	}
}

