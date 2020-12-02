package estradio.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import estradio.licenca.*;

/**
 * Representa o servidor central da ESTRadio, onde se armazenam todas as
 * musicas, generos, artistas, utilizadores, etc...
 */
public class ESTRadioServer {

	private ArrayList<Genero> generos = new ArrayList<Genero>();
	private ArrayList<Artista> artistas = new ArrayList<Artista>();
	private ArrayList<Musica> musicas = new ArrayList<Musica>();
	private ArrayList<Utilizador> utilizadores = new ArrayList<Utilizador>();

	//Musicas de niveis 1 a 10
	private ArrayList<Musica> nivel1 = new ArrayList<Musica>();
	private ArrayList<Musica> nivel2 = new ArrayList<Musica>();
	private ArrayList<Musica> nivel3 = new ArrayList<Musica>();
	private ArrayList<Musica> nivel4 = new ArrayList<Musica>();
	private ArrayList<Musica> nivel5 = new ArrayList<Musica>();
	private ArrayList<Musica> nivel6 = new ArrayList<Musica>();
	private ArrayList<Musica> nivel7 = new ArrayList<Musica>();
	private ArrayList<Musica> nivel8 = new ArrayList<Musica>();
	private ArrayList<Musica> nivel9 = new ArrayList<Musica>();
	private ArrayList<Musica> nivel10 = new ArrayList<Musica>();

	//Musicas entre Niveis
	public ESTRadioServer() {
		lerGeneros();
		lerArtistas();
		lerMusicas();
		lerUsers();
	}

	/**
	 * método para ler a informacaoo dos generos, presente no ficheiro
	 * serverdata/generos.dat
	 */
	private void lerGeneros() {
		String nomeFich = "serverdata/generos.dat"; // nome do ficheiro a ler
		try {// criar um FileReader para o ficheiro pretendido e "envolve-lo" (wrap)// com um
			// BufferedReader
			BufferedReader fin = new BufferedReader(new FileReader(nomeFich));

			String linha = fin.readLine(); // ler a primeira linha

			while (linha != null) {// separar a informação na linha, dividida por tabula��es
				Genero g = new Genero(linha);
				addGenero(g);
				linha = fin.readLine(); // ler a proxima linha}fin.close();
			}
			fin.close();
		} catch (FileNotFoundException e) {
			System.out.printf("O ficheiro %s n�o existe", nomeFich);
		} catch (IOException e) {
			System.out.printf("Erro na leitura de %s", nomeFich);
		}
	}

	/**
	 * m�todo para ler a informacao dos artistas, presente no ficheiro
	 * serverdata/artistas.dat
	 */
	private void lerArtistas() {
		String nomeFich = "serverdata/artistas.dat"; // nome do ficheiro a ler
		try {// criar um FileReader para o ficheiro pretendido e "envolve-lo" (wrap)// com um
			// BufferedReader
			BufferedReader fin = new BufferedReader(new FileReader(nomeFich));

			String linha = fin.readLine(); // ler a primeira linha

			while (linha != null) {// separar a informa��o na linha, dividida por tabula��es
				Artista a = new Artista(linha);
				addArtista(a);
				linha = fin.readLine(); // ler a pr�xima linha}fin.close();
			}
			fin.close();
		} catch (FileNotFoundException e) {
			System.out.printf("O ficheiro %s n�o existe", nomeFich);
		} catch (IOException e) {
			System.out.printf("Erro na leitura de %s", nomeFich);
		}
	}

	/**
	 * metodo para ler a informacaoo das musicas, presente no ficheiro
	 * serverdata/musicas.dat
	 */
	private void lerMusicas() {
		String nomeFich = "serverdata/musicas.dat"; // nome do ficheiro a ler
		try {// criar um FileReader para o ficheiro pretendido e "envolve-lo" (wrap)// com um
			// BufferedReader
			BufferedReader fin = new BufferedReader(new FileReader(nomeFich));

			String linha = fin.readLine(); // ler a primeira linha

			while (linha != null) {// separar a informa��o na linha, dividida por tabula��es
				String infoMusica[] = linha.split("	");
				int id = Integer.parseInt(infoMusica[0]);
				Artista artista = new Artista("n tem artista");

				for (Artista a : artistas) {
					if (a.getNome().equals(infoMusica[2]))
						artista = a;
				}

				Genero genero = new Genero("n tem genero");
				for (Genero g : generos) {
					if (g.getNome().equals(infoMusica[3]))
						genero = g;
				}

				int nivel = Integer.parseInt(infoMusica[4]);
				Musica m = new Musica(id, infoMusica[1], artista, genero, nivel);
				artista.addMusicas(m);
				genero.addMusica(m);
				musicas.add(m);
				lerNivelMusica(nivel, m);
				linha = fin.readLine(); // ler a pr�xima linha
			}
			fin.close();
		} catch (FileNotFoundException e) {
			System.out.printf("O ficheiro %s nao existe", nomeFich);
		} catch (IOException e) {
			System.out.printf("Erro na leitura de %s", nomeFich);
		}
	}

	/**
	 * m�todo para ler a informa��o dos utilizadores, presente no ficheiro
	 * serverdata/users.dat
	 */
	private void lerUsers() {
		String nomeFich = "serverdata/users.dat"; // nome do ficheiro a ler
		try {
			// criar um FileReader para o ficheiro pretendido e "envolve-lo" (wrap)// com um
			// BufferedReader
			BufferedReader fin = new BufferedReader(new FileReader(nomeFich));

			String linha = fin.readLine(); // ler a primeira linha

			// Ve numero de utilizadores presentes, informacao na primeira linha
			String nUtilizadoresString = linha;
			int nUtiliazadores = Integer.parseInt(nUtilizadoresString);

			// Para cada autilizador ver o nGenerosClassificados, nArtistasClassificados e nMusicasClassificadas
			for (int i = 0; i < nUtiliazadores; i++) {
				linha = fin.readLine();

				if (linha.equalsIgnoreCase("-----"))
					linha = fin.readLine();

				String info[] = linha.split(", ");
				// System.out.println(linha);
				int nGeneros = Integer.parseInt(info[2]);
				int nArtistas = Integer.parseInt(info[3]);
				int nMusicas = Integer.parseInt(info[4]);

				//TODO licencas
				Licenca licenca;
				switch (info[1]) {
				case "Grátis":
					licenca = new Gratis();
					break;			
				default:
					licenca = new Simples();
					break;
				}

				Utilizador u = new Utilizador(info[0], licenca);

				for (int j = 0; j < nGeneros; j++) {
					linha = fin.readLine(); // ler a próxima linha
					String infoGeneros[] = linha.split(", ");
					int avaliacao = Integer.parseInt(infoGeneros[1]);

					for (Genero g : generos) {
						if (g.getNome().equals(infoGeneros[0]))
							u.addGenerosClassificados(g, avaliacao);
					}
				}

				for (int j = 0; j < nArtistas; j++) {
					linha = fin.readLine(); // ler a próxima linha
					String infoArtistas[] = linha.split(", ");
					int avaliacao = Integer.parseInt(infoArtistas[1]);

					for (Artista a : artistas) {
						if (a.getNome().equals(infoArtistas[0]))
							u.addArtistasClassificados(a, avaliacao);
					}
				}

				for (int j = 0; j < nMusicas; j++) {
					linha = fin.readLine(); // ler a próxima linha
					String infoMusicas[] = linha.split(", ");
					int avaliacao = Integer.parseInt(infoMusicas[1]);

					for (Musica m : musicas) {
						if (m.getId() == Integer.parseInt(infoMusicas[0]))
							u.addMusicasClassificadas(m, avaliacao);
					}
				}

				addUtilizador(u);
			}

			fin.close();
		} catch (FileNotFoundException e) {
			System.out.printf("O ficheiro %s não existe", nomeFich);
		} catch (IOException e) {
			System.out.printf("Erro na leitura de %s", nomeFich);
		}
	}

	private void lerNivelMusica(int nivel, Musica m) {
		switch (nivel) {
		case 1:
			nivel1.add(m);
			break;
		case 2:
			nivel2.add(m);
			break;
		case 3:
			nivel3.add(m);
			break;
		case 4:
			nivel4.add(m);
			break;
		case 5:
			nivel5.add(m);
			break;
		case 6:
			nivel6.add(m);
			break;
		case 7:
			nivel7.add(m);
			break;
		case 8:
			nivel8.add(m);
			break;
		case 9:
			nivel9.add(m);
			break;
		case 10:
			nivel10.add(m);
			break;
		}

	}

	private void addUtilizador(Utilizador u) {
		utilizadores.add(u);
	}

	private void addGenero(Genero g) {
		generos.add(g);
	}

	public ArrayList<Genero> getGeneros() {
		return generos;
	}

	private void addArtista(Artista a) {
		artistas.add(a);
	}

	public ArrayList<Artista> getArtistas() {
		return artistas;
	}

	public ArrayList<Musica> getMusicas() {
		return musicas;
	}

	public ArrayList<Utilizador> getUtilizadores() {
		return utilizadores;
	}

	public ArrayList<Musica> getNivel1a3() {
		ArrayList<Musica> nivel1a3 = new ArrayList<Musica>();
		for(Musica m: nivel1)
			nivel1a3.add(m);
		for(Musica m: nivel2)
			nivel1a3.add(m);
		for(Musica m: nivel3)
			nivel1a3.add(m);

		return nivel1a3;
	}

	public ArrayList<Musica> getNivel1a5() {
		ArrayList<Musica> nivel1a5 = getNivel1a3();
		for(Musica m: nivel4)
			nivel1a5.add(m);
		for(Musica m: nivel5)
			nivel1a5.add(m);

		return nivel1a5;
	}

	public ArrayList<Musica> getMusicasNivel(Licenca l){
		ArrayList<Musica> musicasNivel = new ArrayList<Musica>();
		String nomeLicenca = l.getNome();
		System.out.println(nomeLicenca);
		//Entrega as musicas consoante o nivel
		switch (nomeLicenca) {
		case "Grátis":
			return musicasNivel = getNivel1a3();
		case "Simples":
			if(true /*TODO podeOuvir musicas dos niveis 4 e 5*/){
				return musicasNivel = getNivel1a5();
			}else{
				return musicasNivel = getNivel1a3();
			}
		case "Avançada":
			if(true /*TODO podeOuvir 20 musicas dos niveis 7 ou 8 e 5 do 9 ao 10*/) {
				// Premium l = new Premium();
				musicasNivel.addAll(musicas);
				return musicasNivel;
			}
			else if ( false/*TODO 5 musicas do nivel superior a 8*/) {

			} 
			else{
				/*Musicas de 1 a 6*/
			}
		case "Premium":
			musicasNivel.addAll(musicas);
			return musicasNivel;
		}

		return musicasNivel;
	}

	public ArrayList<Musica> gerarPlaylist(Utilizador u) {
		//TODO Case para cada licenca para fazer a lista de musicas baseado no nivel
		Licenca licenca = u.getLicenca();
		ArrayList<Musica> musicasNivel = getMusicasNivel(licenca);

		//Listas das musicas/artistas/generos classificados
		HashMap<Genero,Integer> generosClassificados = u.getGenerosClassificados();
		HashMap<Artista,Integer> artistasClassificados = u.getArtistasClassificados();
		HashMap<Musica,Integer> musicasClassificadas = u.getMusicasClassificadas();

		//Listas baseadas no nivel e nas classificacoes
		ArrayList<Musica> musicasGenerosClassificados = new ArrayList<Musica>();
		ArrayList<Musica> musicasArtistasClassificados = new ArrayList<Musica>();
		ArrayList<Musica> musicasMusicasClassificadas = new ArrayList<Musica>();

		//Listas de musicas do nivel baseado nos generos/artistas/muiscas classificadas
		for (Musica m : musicasNivel) { 

			for (Musica mu : musicasClassificadas.keySet()) 
				if (m.equals(mu))
					for (int i = 0; i < musicasClassificadas.get(mu); i++) 
						musicasMusicasClassificadas.add(m); 

			for (Artista a : artistasClassificados.keySet()) 
				if (m.getArtista().equals(a))
					for (int i = 0; i < artistasClassificados.get(a); i++) 
						musicasArtistasClassificados.add(m);

			for (Genero g : generosClassificados.keySet()) 
				if (m.getGenero().equals(g)) 
					for (int i = 0; i < generosClassificados.get(g); i++) 
						musicasGenerosClassificados.add(m);
		}

		//Selecao das musicas baseado nas preferencias e na licenca 
		ArrayList<Musica> playlist = new ArrayList<Musica>(); 
		int indexAleatorio;

		playlist.addAll(gerarMusicasPlaylist(licenca, musicasMusicasClassificadas, licenca.getnPreferenciasMusicas()));
		playlist.addAll(gerarMusicasPlaylist(licenca, musicasArtistasClassificados, licenca.getnPreferenciasArtistas()));
		playlist.addAll(gerarMusicasPlaylist(licenca, musicasGenerosClassificados, licenca.getnPreferenciasGeneros()));

		//Caso a playlist não tenha músicas suficientes, vai adicionar mais músicas
		Musica aleatoria;
		while (playlist.size() < licenca.getnTotalMusicas()) { 
			do {
				indexAleatorio = ThreadLocalRandom.current().nextInt( musicasNivel.size() );
				aleatoria = musicasNivel.get(indexAleatorio);
			}while(!licenca.podeOuvir(aleatoria));
			playlist.add(musicasNivel.get(indexAleatorio)); 
		}

		//Misturar as músicas 
		for (int i = 0; i < playlist.size(); i++) { 
			indexAleatorio = ThreadLocalRandom.current().nextInt(playlist.size() - 1); 
			int indexAleatorioB = ThreadLocalRandom.current().nextInt(playlist.size() - 1);

			Musica mA = playlist.get(indexAleatorio); 
			Musica mB = playlist.get(indexAleatorioB);

			playlist.set(indexAleatorio, mB); 
			playlist.set(indexAleatorioB, mA); 
		}

		//Colocar os anuncios, baseado na licenca
		int intervaloAnuncios[] = licenca.getAnuncio();
		if( intervaloAnuncios[0] > 0) {
			Musica anuncio = new Musica("Anúncio", new Artista("---"), new Genero("---"), 1);
			int nAnuncios = (int)Math.floor(licenca.getnTotalMusicas()/intervaloAnuncios[0]);
			int tamanhoPlaylistComAnuncios = licenca.getnTotalMusicas() + nAnuncios;
			int indexRandom = ThreadLocalRandom.current().nextInt(intervaloAnuncios[0],intervaloAnuncios[1]);

			for (int i = indexRandom; i < tamanhoPlaylistComAnuncios ; i+= indexRandom + 1) {
				playlist.add(i, anuncio);
				indexRandom = ThreadLocalRandom.current().nextInt(intervaloAnuncios[0],intervaloAnuncios[1]);
			}
		}

		return playlist;
	}

	private ArrayList<Musica> gerarMusicasPlaylist(Licenca l, ArrayList<Musica> musicasClassificadas, int nPreferencias) {
		ArrayList<Musica> playlist = new ArrayList<Musica>(); 
		int indexAleatorio; 
		Musica aleatoria;

		for (int i = 0; i < nPreferencias; i++) { 
			do {
				indexAleatorio = ThreadLocalRandom.current().nextInt( musicasClassificadas.size() );
				aleatoria = musicasClassificadas.get(indexAleatorio);
			}while(!l.podeOuvir(aleatoria));
			playlist.add(musicasClassificadas.get(indexAleatorio)); 
		}
		return playlist;
	}

}