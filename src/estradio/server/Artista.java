package estradio.server;

import java.util.ArrayList;

public class Artista {
	
	private String nome;
	private ArrayList<Musica> musicas = new ArrayList<Musica>();
	
	public Artista(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
	public void addMusicas(Musica m) {
		musicas.add(m);
	}

	public ArrayList<Musica> getMusicas() {
		return musicas;
	}
}
