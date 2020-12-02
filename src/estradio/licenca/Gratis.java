package estradio.licenca;

import estradio.server.Musica;

public class Gratis extends LicencaDefault {

	int anuncios[] = {3,4};

	public Gratis() {
		super("Grátis", 3, 0, 3, 4 , 15, 5, 0, 0, 0, 0, 21);
	}

	@Override
	public boolean podeDownload(Musica m) {
		return false;
	}

}