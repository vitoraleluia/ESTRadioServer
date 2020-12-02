package estradio.server;

import java.util.ArrayList;
import java.util.HashMap;

import estradio.licenca.Licenca;

public class Utilizador {

	private String nome;
	private Licenca licenca;

	private HashMap<Genero, Integer> generosClassificados = new HashMap<Genero, Integer>();
	//	private ArrayList<Genero> generosClassificados = new ArrayList<Genero>();
	//	private ArrayList<Integer> generosClassificacao = new ArrayList<Integer>();

	private HashMap<Artista, Integer> artistasClassificados = new HashMap<Artista, Integer>();
	//	private ArrayList<Artista> artistasClassificados = new ArrayList<Artista>();
	//	private ArrayList<Integer> artistasClassificacao = new ArrayList<Integer>();

	private HashMap<Musica, Integer> musicasClassificadas = new HashMap<Musica, Integer>();
	//	private ArrayList<Musica> musicasClassificados = new ArrayList<Musica>();
	//	private ArrayList<Integer> musicasClassificacao = new ArrayList<Integer>();

	private HashMap<Musica,Boolean> musicasDescarregadas = new HashMap<Musica,Boolean>();
	//private ArrayList<Boolean> musicasDescarregadas = new ArrayList<Boolean>();

	private ArrayList<Musica> playlist = new ArrayList<Musica>();

	public Utilizador(String nome, Licenca licenca) {
		this.nome = nome;
		this.licenca = licenca;
	}

	public String getNome() {
		return nome;
	}

	public Licenca getLicenca() {
		return licenca;
	}

	public void addGenerosClassificados(Genero g, int avaliacao) {
		generosClassificados.put(g, avaliacao);
	}

	public void removeGenerosClassificados(Genero g) {
		generosClassificados.remove(g);
	}

	public HashMap<Genero, Integer> getGenerosClassificados() {
		return generosClassificados;
	}

	public void addArtistasClassificados(Artista a, int avaliacao) {
		artistasClassificados.put(a, avaliacao);
	}

	public void removeArtistasClassificados(Artista a) {
		artistasClassificados.remove(a);
	}

	public HashMap<Artista, Integer> getArtistasClassificados() {
		return artistasClassificados;
	}

	public void addMusicasClassificadas(Musica m, int avaliacao) {
		musicasClassificadas.put(m, avaliacao);
	}

	public void removeMusicasClassificadas(Musica m) {
		musicasClassificadas.remove(m);
	}

	public HashMap<Musica, Integer> getMusicasClassificadas() {
		return musicasClassificadas;
	}

	public void addMusicasDescarregadas(Musica m) {
		musicasDescarregadas.put(m, true);
	}

	public HashMap<Musica, Boolean> getMusicasDescarregadas() {
		return musicasDescarregadas;
	}

	public ArrayList<Musica> getPlaylist() {
		return playlist;
	}

	public void setPlaylist(ArrayList<Musica> playlist) {
		this.playlist = playlist;
	}


}
