package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
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
	private List<Integer> best;  //PER IL PERCORSO MIGLIORE
	
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
	public List<Integer> trovaPercorso(Integer sorgente){
		this.best = new ArrayList<Integer>();
		List<Integer> parziale = new ArrayList<Integer>();
		parziale.add(sorgente);
		//lancio la ricorsione
		ricorsione(parziale, -1);
		
		return best;
	}
	
	private void ricorsione(List<Integer> parziale, int peso) {
		
		Integer ultimo = parziale.get(parziale.size() - 1);
		//ottengo i vicini
		List<Integer> vicini = Graphs.neighborListOf(this.grafo, ultimo);
		for(Integer vicino : vicini) {
			if(!parziale.contains(vicino) && peso == -1) {
				parziale.add(vicino);
				ricorsione(parziale, (int) this.grafo.getEdgeWeight(this.grafo.getEdge(ultimo, vicino)));
				parziale.remove(vicino);
			} else {
				if(!parziale.contains(vicino) && this.grafo.getEdgeWeight(this.grafo.getEdge(ultimo, vicino)) == peso) {
					parziale.add(vicino);
					ricorsione(parziale, peso);
					parziale.remove(vicino);
				}
			}
		}
		
		if(parziale.size() > best.size()) {
			this.best = new ArrayList<>(parziale);
		}
		
	}
	//metodo che verifica se l'input è un id appartenente a un vertice
	public boolean grafoContiene(Integer id) {
		if(this.grafo.containsVertex(id))
			return true;
		return false;
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
