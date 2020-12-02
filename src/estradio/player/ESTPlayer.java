package estradio.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.Timer;

import estradio.server.Artista;
import estradio.server.ESTRadioServer;
import estradio.server.Genero;
import estradio.server.Musica;
import estradio.server.Utilizador;

/** Esta classe apresenta a aplica��o que os clientes da ESTRadio
 * ter�o para poderem ouvir a m�sica disponibilizada pela ESTRadio.
 * Ter�o de alterar esta classe, EXCETO O C�DIGO INDICADO COMO N�O ALTER�VEL.  
 */
public class ESTPlayer extends JFrame {

	/** N� de S�rie  */
	private static final long serialVersionUID = 1L;

	/** �ndice da pr�xima m�sica a tocar */
	private int proxMusica = 0;

	/** Labels onde colcoar a informa��o da m�sica */
	private JLabel artistaLbl;
	private JLabel musicaLbl;
	private JLabel generoLbl;

	private ESTRadioServer server;
	private Utilizador utilizador;
	private ArrayList<Musica> playlist = new ArrayList<Musica>();
	private int indexMusicaAtual = 0;

	/** constroi a aplicacao cliente 
	 * @param server o servidor a que esta aplicacao este associada
	 * @param x coordenada x do ecran onde deve aparecer a janela
	 * @param y coordenada x do ecran onde deve aparecer a janela
	 */
	public ESTPlayer(ESTRadioServer sv, Utilizador u, int x, int y ) {
		// TODO colocar aqui a informacao correta
		server = sv;
		utilizador = u;

		setTitle( "ESTPlayer - " + u.getNome() );
		setLocation( x, y );
		initialize();
		mudarMusica();
	}

	/** método que é chamado quando se vai mudar de música
	 */
	private void mudarMusica() {
		proxMusica++;

		// TODO se já não houver músicas na playlist
		if(indexMusicaAtual == playlist.size()) {
			playlist = server.gerarPlaylist(utilizador);
			indexMusicaAtual = 0;
		}
		Musica atual = playlist.get(indexMusicaAtual);

		if( proxMusica >= 1  ) {
			// TODO ir buscar a pr�xima m�sica
			proxMusica = 0;
			atual = playlist.get(indexMusicaAtual++);
		}		

		// TODO Colocar a informa��o correta
		String nomeArtista = atual.getArtista().getNome();
		String tituloMusica = atual.getTitulo();
		String genero = atual.getGenero().getNome();
		int nivel = atual.getNivel();

		// NaO ALTERAR ESTE CoDIGO
		artistaLbl.setText( nomeArtista );
		musicaLbl.setText( tituloMusica );
		generoLbl.setText( genero + " (" + nivel +")");
	}

	/** m�todo chamado quando o bot�o de escolher g�nero � premido */
	protected void botaoGeneroPremido() {
		// TODO acabar este m�todo
		// pode ser preciso alterar os par�metros do construtor seguinte,
		// exceto os referentes ao elementos gr�ficos 
		ListaGeneros lg = new ListaGeneros( this, "Escolha os seus generos preferidos", server, utilizador);
		lg.setLocationRelativeTo( this );
		lg.setVisible( true );
	}	

	/** m�todo chamado quando o bot�o de escolher artista � premido */
	protected void botaoArtistaPremido() {
		// TODO acabar este m�todo
		// pode ser preciso alterar os par�metros do construtor seguinte,
		// exceto os referentes ao elementos gr�ficos 
		ListaArtistas la = new ListaArtistas( this, "Escolha os seus artistas preferidos", server, utilizador );
		la.setLocationRelativeTo( this );
		la.setVisible( true );
	}	

	/** m�todo chamado quando o bot�o de escolher m�sica � premido */
	protected void botaoMusicaPremido() {
		// TODO acabar este m�todo
		// pode ser preciso alterar os par�metros do construtor seguinte,
		// exceto os referentes ao elementos gr�ficos 
		ListaMusicas la = new ListaMusicas( this, "Escolha as suas musicas preferidas", server, utilizador );
		la.setLocationRelativeTo( this );
		la.setVisible( true );
	}	

	/** m�todo chamado quando o bot�o de escolher m�sica � premido */
	protected void botaoPlayListPremido() {
		// TODO acabar este m�todo
		// pode ser preciso alterar os par�metros do construtor seguinte,
		// exceto os referentes ao elementos gr�ficos 
		ListaPlayList la = new ListaPlayList( this, "Musicas a ouvir", server, this);
		la.setLocationRelativeTo( this );
		la.setVisible( true );
	}	

	/** m�todo chamado quando o bot�o de escolher m�sica � premido */
	protected void botaoSaltarPremido() {
		// TODO acabar este m�todo
		// se se puder saltar a m�sica atual, mkuda-se sen�o apresenta-se uma mensagem de erro
		if( true /* TODO saber se pode saltar m�sica */ )
			mudarMusica();
		else
			JOptionPane.showMessageDialog(this, "Queres Saltar uma música? Faz upgrade!!!","Operação não suportada!", JOptionPane.ERROR_MESSAGE );
	}	

	public ArrayList<Musica> getPlaylist() {
		return playlist;
	}

	// A PARTIR DAQUI N�O � NECESS�RIO ALTERAR QUALQUER C�DIGO! 
	// A PARTIR DAQUI N�O � NECESS�RIO ALTERAR QUALQUER C�DIGO!
	// A PARTIR DAQUI N�O � NECESS�RIO ALTERAR QUALQUER C�DIGO! 
	// A PARTIR DAQUI N�O � NECESS�RIO ALTERAR QUALQUER C�DIGO! 
	/** inicializa a janela, adicionando os bot�es e os restantes elementos	 */
	private void initialize() {
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		Container container = getContentPane(); 
		container.add( setupBotoesSuperiores(), BorderLayout.NORTH );
		container.add( setupDisplay(), BorderLayout.CENTER);
		pack();
		setResizable( false );
		Timer t = new Timer(10000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mudarMusica();				
			}
		});
		t.start();
	}

	/** inicializa os bot�es de g�nero, artista e m�sica
	 * @return o painel criado com os bot�es 
	 */
	private JPanel setupBotoesSuperiores() {
		SpringLayout layout = new SpringLayout();
		JPanel btPane = new JPanel( layout );
		btPane.setPreferredSize( new Dimension(450, 30) );

		/** Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>
		 */
		JButton btGenero = new JButton( new ImageIcon("icons//genero.png") );
		layout.putConstraint( SpringLayout.WEST, btGenero, 5, SpringLayout.WEST, btPane);
		layout.putConstraint( SpringLayout.NORTH, btGenero, 5, SpringLayout.NORTH, btPane);
		btGenero.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				botaoGeneroPremido();				
			}
		});
		btPane.add( btGenero );

		/** Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>
		 */
		JButton btArtista = new JButton( new ImageIcon("icons//artista.png") );
		layout.putConstraint( SpringLayout.WEST, btArtista, 0, SpringLayout.EAST, btGenero);
		layout.putConstraint( SpringLayout.NORTH, btArtista, 5, SpringLayout.NORTH, btPane);
		btArtista.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				botaoArtistaPremido();				
			}
		});
		btPane.add( btArtista );

		/** Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>
		 */
		JButton btMusica = new JButton( new ImageIcon("icons//musica2.png") );
		layout.putConstraint( SpringLayout.WEST, btMusica, 0, SpringLayout.EAST, btArtista);
		layout.putConstraint( SpringLayout.NORTH, btMusica, 5, SpringLayout.NORTH, btPane);
		btMusica.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				botaoMusicaPremido();				
			}
		});
		btPane.add( btMusica );

		return btPane;
	}

	/** inicializa a zona onde aparece a informa��o da m�sica e osa bot�es de saltar e playlist
	 * @return o painel com os bot�es e display
	 */
	private Component setupDisplay() {
		SpringLayout layout = new SpringLayout();
		JPanel display = new JPanel( layout ) ;
		display.setBackground( Color.black );
		display.setPreferredSize( new Dimension(450, 130) );

		artistaLbl = new JLabel( "Artista" );
		artistaLbl.setFont( new Font("Arial", Font.BOLD, 20) );
		artistaLbl.setForeground( Color.white );
		layout.putConstraint( SpringLayout.WEST, artistaLbl, 10, SpringLayout.WEST, display );
		layout.putConstraint( SpringLayout.NORTH, artistaLbl, 10, SpringLayout.NORTH, display );
		display.add( artistaLbl );

		musicaLbl = new JLabel( "Titulo da musica" );
		musicaLbl.setFont( new Font("Arial", Font.BOLD, 25) );
		musicaLbl.setForeground( Color.YELLOW );
		layout.putConstraint( SpringLayout.WEST, musicaLbl, 10, SpringLayout.WEST, display );
		layout.putConstraint( SpringLayout.NORTH, musicaLbl, 5, SpringLayout.SOUTH, artistaLbl );
		display.add( musicaLbl );

		generoLbl = new JLabel( "Album da musica" );
		generoLbl.setFont( new Font("Arial", Font.BOLD, 15) );
		generoLbl.setForeground( Color.yellow );
		layout.putConstraint( SpringLayout.WEST, generoLbl, 10, SpringLayout.WEST, display );
		layout.putConstraint( SpringLayout.NORTH, generoLbl, 5, SpringLayout.SOUTH, musicaLbl );
		display.add( generoLbl );

		/** Icons made by <a href="https://www.flaticon.com/authors/smashicons" title="Smashicons">Smashicons</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>
		 */
		JButton btPlayList = new JButton( new ImageIcon("icons//playlist.png") );
		layout.putConstraint( SpringLayout.EAST, btPlayList, -2, SpringLayout.EAST, display);
		layout.putConstraint( SpringLayout.SOUTH, btPlayList, -2, SpringLayout.SOUTH, display);
		btPlayList.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				botaoPlayListPremido();
			}
		});
		display.add( btPlayList );

		/**Icons made by <a href="https://www.flaticon.com/authors/smashicons" title="Smashicons">Smashicons</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>
		 */
		JButton btSkip = new JButton( new ImageIcon("icons//saltar.png"));
		layout.putConstraint( SpringLayout.EAST, btSkip, 0, SpringLayout.WEST, btPlayList);
		layout.putConstraint( SpringLayout.SOUTH, btSkip, -2, SpringLayout.SOUTH, display);
		btSkip.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				botaoSaltarPremido();				
			}
		});
		display.add( btSkip );

		return display;
	}
}
