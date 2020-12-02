package estradio.licenca;

/** Exceção a usar quando não se pode realizar uma operação que a licença do utilizador não permite
 */
public class LicencaNaoSuportaOperacaoExcpetion extends RuntimeException {

	/** nº de ´serie */
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
