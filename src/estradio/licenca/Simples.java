package estradio.licenca;

import estradio.server.Musica;

public class Simples extends LicencaDefault {
	private int limiteMusicasNiveis4e5 = 15;

	public Simples() {
		super("Simples", 3, 5, 4, 6, 10, 10, 6, 10, 0, 0, 21);

	}

	public boolean podeOuvir(Musica m) {
		if(m.getNivel() >= 4 && limiteMusicasNiveis4e5 != 0) {
			limiteMusicasNiveis4e5--;
			return true;
		}

		if(super.podeOuvir(m)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean podeDownload(Musica m) {
		if(m.getNivel() <= super.getNivel())
			return true;

		return false;
	}
}
