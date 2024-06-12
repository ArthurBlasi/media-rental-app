package dados;

import java.util.ArrayList;

public class Video extends Midia {

	private int qualidade;
	public Video(int codigo, String titulo, int ano, Categoria categoria, int qualidade) {
        super(codigo, titulo, ano, categoria);
        this.qualidade = qualidade;
    }

    @Override
    public double calculaLocacao() {
        if(getAno() == 2024) {
            return 20;
        } else if(getAno() >= 2000 && getAno() <= 2023) {
            return 15;
        } else if(getAno() < 2000) {
            return 10;
        } else {
            return 0;
        }
    }

    public int getQualidade() {
        return qualidade;
    }

    /*

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void adicionaVideo(Video video) {
        videos.add(video);
    }

     */

    @Override
    public String toString() {
        return getCodigo() + "," + getTitulo() + "," + getAno() + "," + getCategoria().getNome() + "," + qualidade;
    }
}
