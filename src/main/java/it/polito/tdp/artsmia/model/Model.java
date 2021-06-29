package it.polito.tdp.artsmia.model;

import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.Adiacenza;
import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private ArtsmiaDAO dao;
	//NON HO LA CLASSE ARTIST QUINDI NON FACCIO ID MAP, METTO SOLO INTEGER COME TIPO NEL GRAFO
	
	private SimpleWeightedGraph<Integer,DefaultWeightedEdge> grafo;
	List<Adiacenza> adiacenze;
	
	public Model() {
		dao = new ArtsmiaDAO();
		
	}

	public void creaGrafo(String ruolo) {
		grafo= new SimpleWeightedGraph<Integer,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		//vertici  --> metodo a cui passo solo come parametro il ruolo
		this.adiacenze= dao.getAdiacenze(ruolo);
		Graphs.addAllVertices(grafo, dao.getVertici(ruolo));
		
		//archi
		for(Adiacenza a: dao.getAdiacenze(ruolo)) {
			if(this.grafo.containsVertex(a.getId1()) && this.grafo.containsVertex(a.getId2())) {
				DefaultWeightedEdge e = this.grafo.getEdge(a.getId1(),a.getId2());
				if(e==null) {
					Graphs.addEdgeWithVertices(grafo,a.getId1(), a.getId2(), a.getPeso());
				}
			}
			
		}
	}
	
	public List<Adiacenza> getAdiacenze() {
		return adiacenze;
	}

	public void setAdiacenze(List<Adiacenza> adiacenze) {
		this.adiacenze = adiacenze;
	}

	public int numVertici() {
		if(this.grafo!=null)
			return this.grafo.vertexSet().size();
		return 0;
	}
	public int numArchi() {
		if(this.grafo!=null)
			return this.grafo.edgeSet().size();
		return 0;
	}
	
	public List<String> getRuoliTendina(){
		return dao.getRuoliTendina();
	}
}
