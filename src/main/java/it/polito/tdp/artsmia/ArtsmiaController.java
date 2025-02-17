package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.db.Adiacenza;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;
    
    

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	
    	txtResult.appendText("COPPIE ADIACENTI \n");
    	List<Adiacenza> result = this.model.getAdiacenze();
    	for(Adiacenza a: result) {
    		txtResult.appendText(a+"\n");
    	}
    	}

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    	txtResult.clear();
    	Integer id;
    	
    	try {
    		id = Integer.parseInt(txtArtista.getText());
    	} catch(NumberFormatException e) {
    		txtResult.appendText("INSERIRE UN ID NEL FORMATO CORRETTO\n");
    		return ;
    	}
    	
    	if(!this.model.grafoContiene(id)) {
    		txtResult.appendText("L'ARTISTA NON E' NEL GRAFO!\n");
    		return ;
    	}
    	
    	List<Integer> percorso = this.model.trovaPercorso(id);
    	txtResult.appendText("PERCORSO PIU' LUNGO: " + percorso.size() + " \n");
    	for(Integer v : percorso) {
    		txtResult.appendText(v + " ");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	String scelta= this.boxRuolo.getValue();
    	if(scelta==null) {
    		txtResult.appendText("seleziona un ruolo");
    		return;
    	}
    	this.model.creaGrafo(scelta);
    	txtResult.appendText("GRAFO CREATO \n");
    	
    	txtResult.appendText("# VERTICI "+this.model.numVertici()+"\n");
    	txtResult.appendText("# ARCHI "+this.model.numArchi()+"\n");
    	
    }

    public void setModel(Model model) {
    	this.model = model;
    	this.boxRuolo.getItems().addAll(this.model.getRuoliTendina());
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}
