package it.polito.tdp.artsmia.db;

public class Adiacenza implements Comparable<Adiacenza>{

	private Integer id1;
	private Integer id2;
	private Double peso;
	
	public Adiacenza(Integer id1, Integer id2, Double peso) {
		super();
		this.id1 = id1;
		this.id2 = id2;
		this.peso = peso;
	}
	public Integer getId1() {
		return id1;
	}
	public void setId1(Integer id1) {
		this.id1 = id1;
	}
	public Integer getId2() {
		return id2;
	}
	public void setId2(Integer id2) {
		this.id2 = id2;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Adiacenza o) {
		// TODO Auto-generated method stub
		return o.peso.compareTo(this.peso);
	}
	@Override
	public String toString() {
		return "Adiacenza [id1=" + id1 + ", id2=" + id2 + ", peso=" + peso + "]";
	}
	

}
