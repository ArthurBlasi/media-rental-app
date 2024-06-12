package dados;

import java.util.ArrayList;

public class Musica extends Midia {

	private double duracao;

	public Musica(int codigo, String titulo, int ano, Categoria categoria, double duracao) {
        super(codigo, titulo, ano, categoria);
        this.duracao = duracao;
    }

    @Override
    public double calculaLocacao() {
        double locacao = 0;
        if(getCategoria() != null) {
            switch(getCategoria()) {
                case ACA:
                    locacao = Math.round(0.90 * this.duracao * 100.0) / 100.0;
                    return locacao;
                case DRA:
                    locacao = Math.round(0.70 * this.duracao * 100.0) / 100.0;
                    return locacao;
                case FIC:
                    locacao = Math.round(0.50 * this.duracao * 100.0) / 100.0;
                    return locacao;
                case ROM:
                    locacao = Math.round(0.30 * this.duracao * 100.0) / 100.0;
                    return locacao;
                default:
                    return 0;
            }
        } else {
            return 0;
        }
    }

    public double getDuracao() {
        return duracao;
    }

    /*

    public ArrayList<Musica> getMusicas() {
        return musicas;
    }

    public void adicionaMusica(Musica musica) {
        musicas.add(musica);
    }

     */

    @Override
    public String toString() {
        return getCodigo() + "," + getTitulo() + "," + getAno() + "," + getCategoria().getNome() + "," + duracao;
    }
}
