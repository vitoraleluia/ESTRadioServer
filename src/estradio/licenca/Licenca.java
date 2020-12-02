package estradio.licenca;

import estradio.server.Musica;

public interface Licenca {

	int getNivel();
	String getNome();
	int getNDownloads();
	int[] getAnuncio();

	//Numero de quantos generos/artistas/musicas pode classificar
	int getPrefGeneros();
	int getPrefArtistas();
	int getPrefMusicas();

	//Numero de quantas Musicas (das preferencias generos/artistas/musicas) a playlist vai ter 
	int getnPreferenciasMusicas();
	int getnPreferenciasArtistas();
	int getnPreferenciasGeneros();
	int getnTotalMusicas();

	boolean podeOuvir(Musica musica);
	boolean podeDownload(Musica m);
	boolean podeSaltarMusica(Musica m);
}
