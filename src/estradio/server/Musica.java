package estradio.server;

public class Musica {

	private int id;
	private String titulo;
	private Artista artista;
	private Genero genero;
	private int nivel;

	public Musica(int id, String titulo, Artista artista, Genero genero, int nivel) {
		this.id = id;
		this.titulo = titulo;
		this.artista = artista;
		this.genero = genero;
		this.nivel = nivel;
	}
	
	public Musica( String titulo, Artista artista, Genero genero, int nivel) {
		this.titulo = titulo;
		this.artista = artista;
		this.genero = genero;
		this.nivel = nivel;
	}

	public int getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public Artista getArtista() {
		return artista;
	}

	public Genero getGenero() {
		return genero;
	}

	public int getNivel() {
		return nivel;
	}

}
