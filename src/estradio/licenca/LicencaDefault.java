package estradio.licenca;

import estradio.server.Musica;

public class LicencaDefault  implements Licenca{

	private String nome;

	private int nivel;
	private int nDownload;
	private int anuncio[] = new int[2]; //Anuncio publicitario a cada 3 músicas

	private int nPreferenciasGeneros; //Numero de musicas das preferencias (genero)
	private int prefGeneros;

	private int nPreferenciasArtistas; //Numero de musicas das preferencias (genero)
	private int prefArtista;

	private int nPreferenciasMusicas; //Numero de musicas das preferencias (genero)
	private int prefMusica;

	private int nTotalMusicas;

	/**
	 * @param nome da licenca
	 * @param nivel das musicas que o utilizador (que possui a licenca) pode ouvir
	 * @param nDownload Numero de downloads maximo
	 * @param anuncioMin numero minimo de musicas entre anuncios
	 * @param anuncioMax numero maximo de musicas entre anuncios
	 * @param nPreferenciasGeneros Numero de musicas dos generos preferidos na playlist
	 * @param prefGeneros Numero de generos que pode classificar
	 * @param nPreferenciasArtistas Numero de musicas dos artistas preferidos na playlist
	 * @param prefArtista Numero de artistas que pode classificar
	 * @param nPreferenciasMusicas Numero de musicas das musicas preferidos na playlist
	 * @param prefMusica numero de musicas que pode classificar
	 * @param nTotalMusicas numero total de musicas na playlist
	 */
	public LicencaDefault(String nome, int nivel, int nDownload, int anuncioMin, int anuncioMax, int nPreferenciasGeneros,
			int prefGeneros, int nPreferenciasArtistas, int prefArtista, int nPreferenciasMusicas, int prefMusica,
			int nTotalMusicas) {

		this.nome = nome;
		this.nivel = nivel;
		this.nDownload = nDownload;
		anuncio[0] = anuncioMin;
		anuncio[1] = anuncioMax;
		this.nPreferenciasGeneros = nPreferenciasGeneros;
		this.prefGeneros = prefGeneros;
		this.nPreferenciasArtistas = nPreferenciasArtistas;
		this.prefArtista = prefArtista;
		this.nPreferenciasMusicas = nPreferenciasMusicas;
		this.prefMusica = prefMusica;
		this.nTotalMusicas = nTotalMusicas;
	}



	public int getnTotalMusicas() {
		return nTotalMusicas;
	}

	public int getNivel() {
		return nivel;
	}

	public int getNDownloads() {
		return nDownload;
	}

	public int[] getAnuncio() {
		return anuncio;
	}

	public int getnPreferenciasGeneros() {
		return nPreferenciasGeneros;
	}

	public int getnPreferenciasArtistas() {
		return nPreferenciasArtistas;
	}

	public int getnPreferenciasMusicas() {
		return nPreferenciasMusicas;
	}

	public int getPrefGeneros() {
		return prefGeneros;
	}

	public int getPrefArtistas() {
		return prefArtista;
	}

	public int getPrefMusicas() {
		return prefMusica;
	}

	public String getNome() {
		return nome;
	}

	public boolean podeOuvir(Musica m) {
		if(m.getNivel() <= nivel)
			return true;

		return false;
	}

	public boolean podeDownload(Musica m) {
		return true;
	}

	@Override
	public boolean podeSaltarMusica(Musica m) {
		return false;
	}

}
