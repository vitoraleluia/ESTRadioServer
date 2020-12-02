package estradio.player;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import estradio.server.Artista;
import estradio.server.ESTRadioServer;
import estradio.server.Genero;
import estradio.server.Musica;

/** Apresenta a lista das músicas que irão ser reproduzidas de seguida.
 * @author F. Sergio Barbosa e alunos
 */
public class ListaPlayList extends JDialog {
	/** Nº de Série  */
	private static final long serialVersionUID = 1L;

	/** variáveis para os elementos gráficos, NÃO ALTERAR!*/
	private TableModel tableModel;

	private ArrayList<Musica> playlistAListar = new ArrayList<Musica>();
	private Musica anuncio = new Musica("Anúncio", new Artista("---"), new Genero("---"), 1);
	/** Cria a lista de músicas programadas. Pode ser necessário alterar este construtor!!
	 * @param owner janela que deu origem a esta.
	 * @param titulo título a aparecer nesta janela de diálogo
	 * @param sv 
	 */
	public ListaPlayList(JFrame owner, String titulo, ESTRadioServer sv, ESTPlayer player) {
		super( owner, titulo );

		ArrayList<Musica> playlist = player.getPlaylist();
		for (Musica m : playlist) {
			//TODO if(!m.getTitulo().equals(anuncio.getTitulo()))
			playlistAListar.add(m);
		}

		initialize( playlistAListar.size() ); // TODO colocar aqui o número de músicas na playlist
		setDadosTabela( );
		setModalityType( Dialog.ModalityType.DOCUMENT_MODAL );
	}

	/** Método que vai colocar os valores a serem apresentados na tabela
	 * Os parâmetros poderão ser alterados. 
	 */
	private void setDadosTabela( ) {
		// TODO para todas as músicas na playlist
		for( int i=0; i < playlistAListar.size(); i++ ) {
			// TODO colocar os dados nas variáveis
			Musica atual = playlistAListar.get(i);

			String titulo = atual.getTitulo();
			String artista = atual.getArtista().getNome();
			String genero = atual.getGenero().getNome();
			int nivel = atual.getNivel();

			// NÃO ALTERAR ESTE CÓDIGO
			tableModel.setValueAt( titulo, i, 0);
			tableModel.setValueAt( artista, i, 1);
			tableModel.setValueAt( genero, i, 2);
			tableModel.setValueAt( nivel, i, 3);
		}
	}

	// A PARTIR DAQUI NÃO É NECESSÁRIO ALTERAR QUALQUER CÓDIGO! 
	// A PARTIR DAQUI NÃO É NECESSÁRIO ALTERAR QUALQUER CÓDIGO!
	// A PARTIR DAQUI NÃO É NECESSÁRIO ALTERAR QUALQUER CÓDIGO! 
	// A PARTIR DAQUI NÃO É NECESSÁRIO ALTERAR QUALQUER CÓDIGO! 
	/** inicializa a janela, adicionando a tabela */
	private void initialize( int nLinhas ) {
		JPanel p = new JPanel( new BorderLayout() );
		p.add( setupListaMusicas( nLinhas ), BorderLayout.CENTER );
		setContentPane( p );
		pack();
	}

	/** prepara a tabela das músicas
	 * @param nLinhas quanto linhas tem a tabela 
	 * @return a tabela preenchida
	 */
	private Component setupListaMusicas( int nLinhas ) {
		String colunas[] = { "Título", "Artista", "Género", "#" };
		tableModel = new DefaultTableModel( colunas, nLinhas ) {
			/** Nº de Série  */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable lista = new JTable( tableModel );
		TableColumn col = lista.getColumnModel().getColumn(3);
		col.setMaxWidth( 30 );
		col.setMinWidth( 30 );
		lista.setEnabled( false );
		JScrollPane scroll = new JScrollPane( lista );
		return scroll;
	}
}
