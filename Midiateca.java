package dados;

import java.util.ArrayList;

public class Midiateca implements Iterador {

	private int contador;
	private ArrayList<Midia> midias;

	public Midiateca() {
		this.contador = contador;
		midias = new ArrayList<>();
	}

	public boolean cadastraMidia(Midia midia) {
		return this.midias.add(midia);
	}

	public Midia consultaPorCodigo(int codigo) {
		for(Midia midia : midias) {
			if(midia.getCodigo() == codigo) {
				return midia;
			}
		}
		return null;
	}

	public ArrayList<Midia> consultaPorCategoria(Categoria categoria) {
		ArrayList<Midia> midiasporcategoria = new ArrayList<>();
		for(Midia midia : midias) {
			if(midia.getCategoria().equals(categoria)) {
				midiasporcategoria.add(midia);
			}
		}
		if(midiasporcategoria.isEmpty()) {
			return null;
		}
		return midiasporcategoria;
	}

	public boolean removeMidia(int codigo) {
		for(Midia midia : midias) {
			if(midia.getCodigo() == codigo) {
				midias.remove(midia);
				return true;
			}
		}
		return false;
	}

	public boolean codigoRepetido(int codigo) {
		for(Midia midia : midias) {
			if(midia.getCodigo() == codigo) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Video> separarVideos() {
		ArrayList<Video> videos = new ArrayList<>();
		for(Midia midia : midias) {
			if(midia instanceof Video) {
				videos.add((Video) midia);
			}
		}
		return videos;
	}

	public ArrayList<Musica> separarMusicas() {
		ArrayList<Musica> musicas = new ArrayList<>();
		for(Midia midia : midias) {
			if(midia instanceof Musica) {
				musicas.add((Musica) midia);
			}
		}
		return musicas;
	}

	public double mediaLocacoesMusicas() {
		double somatorio = 0;
		int numeroMusicas = 0;
		double media = 0;
		ArrayList<Musica> musicas = separarMusicas();
		//for(int i = 0; i < musicas.size(); i++) {
		//	somatorio += musicas.get(i).calculaLocacao();
		//}
		for(Musica musica : musicas) {
			somatorio += musica.calculaLocacao();
		}
		media = somatorio / musicas.size();
		return media;
	}

	public Musica musicaPertoDaMedia() {
		ArrayList<Musica> musicas = separarMusicas();
		if(musicas.isEmpty()) {
			return null;
		}
		Musica musicaPertoDaMedia = musicas.get(0);
		double media = mediaLocacoesMusicas();
		double maisPerto = Math.abs(musicaPertoDaMedia.calculaLocacao() - media);
		for(Musica musica : musicas) {
			double distancia = Math.abs(musica.calculaLocacao() - media);
			if(distancia < maisPerto) {
				musicaPertoDaMedia = musica;
				maisPerto = distancia;
			}
		}
		return musicaPertoDaMedia;
	}

	public double somatorioLocacoes() {
		double somatorio = 0;
		for(Midia midia : midias) {
			somatorio += midia.calculaLocacao();
		}
		return somatorio;
	}

	public Midia MidiaMaisNova() {
		Midia midiaMaisNova = midias.get(0);
		if(midias.isEmpty()) {
			return null;
		}
		for(Midia midia : midias) {
			if(midia.getAno() > midiaMaisNova.getAno()) {
				midiaMaisNova = midia;
			}
		}
		return midiaMaisNova;
	}

	/**
	 * @see dados.Iterador#reset()
	 */
	public void reset() {
		this.contador = 0;
	}


	/**
	 * @see dados.Iterador#hasNext()
	 */
	public boolean hasNext() {
		if(this.contador < this.midias.size()) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * @see dados.Iterador#next()
	 */
	public Object next() {
		if(hasNext()) {
			return this.midias.get(this.contador++);
		} else {
			return null;
		}
	}

}
