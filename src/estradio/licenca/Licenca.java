package estradio.licenca;

import estradio.server.Musica;

public interface Licenca {

	int getNivel();
	String getNome();
	int getNDownloads();
	//void decNDownloads();
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

}
