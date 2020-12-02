package estradio.licenca;

/** Exce��o a usar quando n�o se pode realizar uma opera��o que a licen�a do utilizador n�o permite
 */
public class LicencaNaoSuportaOperacaoExcpetion extends RuntimeException {

	/** n� de �serie */
	private static final long serialVersionUID = 1L;

	public LicencaNaoSuportaOperacaoExcpetion() {
	}

	public LicencaNaoSuportaOperacaoExcpetion(String arg0) {
		super(arg0);
	}

	public LicencaNaoSuportaOperacaoExcpetion(Throwable arg0) {
		super(arg0);
	}

	public LicencaNaoSuportaOperacaoExcpetion(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public LicencaNaoSuportaOperacaoExcpetion(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
}
