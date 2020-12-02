package estradio.player;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.RowFilter;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import estradio.licenca.LicencaNaoSuportaOperacaoExcpetion;
import estradio.server.ESTRadioServer;
import estradio.server.Musica;
import estradio.server.Utilizador;

/** Apresenta uma listagem com os géneros presentes no sistema,
 * sendo que, se o utilizador lhes atribui uma classificação, aparece a classificação atribuída 
 * @author F. Sergio Barbosa e alunos
 */
public class ListaMusicas extends JDialog {
	/** Nº de Série  */
	private static final long serialVersionUID = 1L;

	/** variáveis para os elementos gráficos, NÃO ALTERAR!*/
	private TableRowSorter<TableModel> sorter;
	private TableModel tableModel;
	private MyTableModlListener tableListener;
	private ArrayList<RowFilter<TableModel,Integer>> filtros;
	private static final AllFilter aceitaTudoFilter = new AllFilter();

	/** Constantes para se poderem identificar as várias colunas da tabela de músicas
	 *  incluindo o nome das colunas e o seu índice.
	 */
	private static String colunas[] = { "Título", "Artista", "Género", "#", "Class.", "D" };
	private static final int COLUNA_TITULO = 0;
	private static final int COLUNA_ARTISTA = 1;
	private static final int COLUNA_GENERO = 2;
	private static final int COLUNA_NIVEL = 3;
	private static final int COLUNA_CLASSIF = 4;
	private static final int COLUNA_DOWN = 5;	

	private Utilizador utilizador;
	private ArrayList<Musica> musicasAListar = new ArrayList<Musica>();
	private HashMap<Musica,Integer> musicasClassificadas = new HashMap<Musica,Integer>();

	/** Cria uma lista de Músicas. Pode ser necessário alterar este construtor!!
	 * @param owner  janela que deu origem a esta.
	 * @param titulo título a aparecer nesta janela de diálogo
	 * @param server 
	 * @param u 
	 */
	public ListaMusicas( JFrame owner, String titulo, ESTRadioServer server, Utilizador u ) {
		super( owner, titulo );
		utilizador = u;

		//Vai buscar as musicas Classificadas
		musicasClassificadas = u.getMusicasClassificadas();
		for(Musica m: musicasClassificadas.keySet())
			musicasAListar.add(m);

		//Colocar as restante musicas
		ArrayList<Musica> musicas = server.getMusicas();
		for (Musica m : musicas) 
			if(!musicasAListar.contains(m))
				musicasAListar.add(m);

		initialize( musicasAListar.size() ); // TODO colocar aqui o número de músicas a listar
		setDadosTabela( );
		setTableListener();
		setModalityType( Dialog.ModalityType.DOCUMENT_MODAL );
	}

	/** Método que vai colocar os valores a serem apresentados na tabela
	 * Os parâmetros poderão ser alterados. 
	 */
	private void setDadosTabela( ) {
		for( int i=0; i < musicasAListar.size(); i++ ) {
			// colocar os dados nas variáveis
			Musica m = musicasAListar.get(i); 
			String titulo = m.getTitulo();
			String artista = m.getArtista().getNome();
			String genero = m.getGenero().getNome();
			int nivel = m.getNivel();
			int classif = musicasClassificadas.getOrDefault(m, 0);
			boolean down = utilizador.getMusicasDescarregadas().getOrDefault(m, false);

			// NÃO ALTERAR ESTE CÓDIGO
			tableModel.setValueAt( titulo, i, COLUNA_TITULO);
			tableModel.setValueAt( artista, i, COLUNA_ARTISTA);
			tableModel.setValueAt( genero, i, COLUNA_GENERO);
			tableModel.setValueAt( nivel, i, COLUNA_NIVEL);
			tableModel.setValueAt( classif, i, COLUNA_CLASSIF);
			tableModel.setValueAt( down, i, COLUNA_DOWN );
		}
	}

	/** Método chamado quando o utilizador altera a classificação dada a uma música
	 * @param linha o índice da música que foi alterada
	 * @param novaClassif a nova classificação atríbuida
	 */
	private void alterouCLassifTabela( int linha, int novaClassif ) {
		System.out.println("Alterou o nível da música " + musicasAListar.get(linha).getTitulo() + " para " + novaClassif);
		try {
			// TODO alterar o nível da música para o valor atribuido pelo utilizador
			Musica classificada = musicasAListar.get(linha);

			if (utilizador.getMusicasClassificadas().size() >= utilizador.getLicenca().getPrefMusicas() ) {
				throw new LicencaNaoSuportaOperacaoExcpetion();
			}


			if(novaClassif > 0)
				utilizador.addMusicasClassificadas(classificada, novaClassif);
			else
				utilizador.removeMusicasClassificadas(classificada);

		} catch( LicencaNaoSuportaOperacaoExcpetion e ) {
			JOptionPane.showMessageDialog(this, "A sua licença não permite fazer essa operação! Faça upgrade!", "Operação não suportada", JOptionPane.ERROR_MESSAGE);
			reporClassifTabela( linha );
		}
	}

	/** método chamado quando se carrega para fazer download 
	 * @param linha linha da música a fazer download
	 */
	private void tentaFazerDownload(int linha) {	
		try {
			// TODO fazer o download da música 

			Musica musicaADescarregar = musicasAListar.get(linha);
			if(utilizador.getMusicasDescarregadas().size() >= utilizador.getLicenca().getNDownloads() || 
					!utilizador.getLicenca().podeDownload(musicaADescarregar))
				throw new LicencaNaoSuportaOperacaoExcpetion();

			utilizador.addMusicasDescarregadas(musicaADescarregar);

		} catch( LicencaNaoSuportaOperacaoExcpetion e ) {
			JOptionPane.showMessageDialog(this, "A sua licença não permite fazer essa operação! Faça upgrade!", "Operação não suportada", JOptionPane.ERROR_MESSAGE);
			reporDownload( linha );
		}
	}

	/** chamado para repor a classificação de uma música
	 * @param linha linha da música a ser reposta
	 */
	private void reporClassifTabela( int linha ) {
		tableModel.removeTableModelListener( tableListener );
		tableModel.setValueAt( 0, linha, COLUNA_CLASSIF);		
		tableModel.addTableModelListener( tableListener );
	}

	/** chamado para recolocar a indicação de download a false
	 * @param linha linha da música a ser reposta
	 */
	private void reporDownload( int linha ) {
		tableModel.removeTableModelListener( tableListener );
		tableModel.setValueAt( false, linha, COLUNA_DOWN );		
		tableModel.addTableModelListener( tableListener );
	}

	// A PARTIR DAQUI NÃO É NECESSÁRIO ALTERAR QUALQUER CÓDIGO! 
	// A PARTIR DAQUI NÃO É NECESSÁRIO ALTERAR QUALQUER CÓDIGO!
	// A PARTIR DAQUI NÃO É NECESSÁRIO ALTERAR QUALQUER CÓDIGO! 
	// A PARTIR DAQUI NÃO É NECESSÁRIO ALTERAR QUALQUER CÓDIGO! 
	/** inicializa a janela, adicionando os botões e a tabela */
	private void initialize( int nLinhas ) {
		JPanel p = new JPanel( new BorderLayout() );
		p.add( setupBotoesLetras(), BorderLayout.NORTH );
		p.add( setupListaMusicas( nLinhas ), BorderLayout.CENTER );
		setContentPane( p );
		pack();
	}

	/** prepara a tabela das músicas
	 * @param nLinhas quanto linhas tem a tabela 
	 * @return a tabela preenchida
	 */
	private Component setupListaMusicas( int nLinhas ) {
		tableModel = new DefaultTableModel( colunas, nLinhas ) {
			/** Nº de Série  */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == COLUNA_CLASSIF || (column == COLUNA_DOWN && (Boolean)getValueAt(row, COLUNA_DOWN) == false);
			}

			public Class getColumnClass(int column) {
				return column == COLUNA_DOWN? Boolean.class: (column== COLUNA_CLASSIF || column == COLUNA_NIVEL? Integer.class: String.class);
			}
		};
		JTable listaGeneros = new JTable( tableModel );

		TableColumn colNivel = listaGeneros.getColumnModel().getColumn( COLUNA_NIVEL );
		colNivel.setMaxWidth( 30 );
		colNivel.setMinWidth( 30 );

		TableColumn col = listaGeneros.getColumnModel().getColumn( COLUNA_CLASSIF );
		sorter = new TableRowSorter<TableModel>( listaGeneros.getModel() );
		listaGeneros.setRowSorter(sorter);
		col.setMaxWidth( 100 );
		col.setMinWidth( 100 );
		Slider5Niveis editCell = new Slider5Niveis(); 
		col.setCellEditor( editCell );
		col.setCellRenderer( editCell );

		TableColumn colDown = listaGeneros.getColumnModel().getColumn( COLUNA_DOWN );
		colDown.setMaxWidth( 30 );
		colDown.setMinWidth( 30 );

		JScrollPane scroll = new JScrollPane( listaGeneros );
		return scroll;
	}

	/** prepara os botões com as letras para filtrar as músicas, por género, artista ou título */
	private JPanel setupBotoesLetras() {
		JPanel filtrosPane = new JPanel( new GridLayout(0, 1) );
		String filtro[] = {"Título","Artista","Género"};
		filtros = new ArrayList<RowFilter<TableModel,Integer>>(3);
		for( int i=0; i < 3; i++ ){
			filtros.add( aceitaTudoFilter ); // inicialmente não tem filtros
			JPanel btPanel = new JPanel( new FlowLayout(FlowLayout.LEFT, 3, 0) );
			btPanel.add( new JLabel( filtro[i] ) );

			ButtonGroup btLetras = new ButtonGroup();

			JToggleButton btTodos = new JToggleButton("Todos");

			final int colIndex = i;
			btTodos.setBorder( null );
			btTodos.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filtros.set( colIndex,  aceitaTudoFilter );
					sorter.setRowFilter( RowFilter.andFilter( filtros ) );
				}			
			});
			btLetras.add( btTodos );
			btPanel.add( btTodos );

			ActionListener botaoHandler = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filtros.set( colIndex, RowFilter.regexFilter("^" + e.getActionCommand(), colIndex) );
					sorter.setRowFilter( RowFilter.andFilter( filtros ) );
				}
			};
			for( char ch = 'A'; ch <= 'Z'; ch++ ) {
				JToggleButton bt = new JToggleButton( " " + ch + " " );
				bt.setActionCommand( "" + ch );
				bt.addActionListener( botaoHandler );
				bt.setBorder( BorderFactory.createEmptyBorder(2, 2, 2, 2) );
				btLetras.add( bt );
				btPanel.add( bt );
			}		
			//return btPanel;
			filtrosPane.add( btPanel );
		}
		return filtrosPane;
	}

	private void setTableListener() {
		tableListener = new MyTableModlListener();
		tableModel.addTableModelListener( tableListener );
	}

	private class MyTableModlListener implements TableModelListener {
		@Override
		public void tableChanged(TableModelEvent e) {
			if( e.getColumn() == COLUNA_CLASSIF )
				alterouCLassifTabela( e.getFirstRow(), (Integer)tableModel.getValueAt(e.getFirstRow(), COLUNA_CLASSIF) );
			else if( e.getColumn() == COLUNA_DOWN )
				tentaFazerDownload( e.getFirstRow() );
		}
	}

	private static class AllFilter extends RowFilter<TableModel,Integer> {
		@Override
		public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
			return true;		}
	}
}
