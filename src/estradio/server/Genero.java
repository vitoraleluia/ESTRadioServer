package estradio.server;

import java.util.ArrayList;


public class Genero {
	
	private String nome;
	private ArrayList<Musica> musicas = new ArrayList<Musica>();

	public Genero(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public ArrayList<Musica> getMusicas(){
		return musicas;
	}

	public void addMusica(Musica m) {
		musicas.add(m);
	}
	
	public void removeMusica(Musica m) {
		musicas.remove(m);
	}
	

}
