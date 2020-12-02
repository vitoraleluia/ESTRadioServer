package estradio.server;

import estradio.player.ESTPlayer;

/** Classe que inicializa o server e cria as janelas
 * com os players para os v�rios ouvintes.
 */
public class Startup {

	public static void main(String[] args) {
		ESTRadioServer server = new ESTRadioServer();

		for( int n=0;  n < server.getUtilizadores().size(); n++ ) {
			ESTPlayer play = new ESTPlayer( server, server.getUtilizadores().get(n), 50 + (n%3)*480, 50 + (n/3)*220 );
			play.setVisible( true );
		}
	}

}
