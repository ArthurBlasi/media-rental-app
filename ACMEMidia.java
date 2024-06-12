package app;

import dados.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.jar.JarOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Locale;

public class ACMEMidia {

	private Midiateca midiateca;
	private Video video;
	private Musica musica;
	private Scanner entrada = null;                 // Atributo para entrada de dados
	private PrintStream saidaPadrao = System.out;   // Guarda a saida padrao - tela(console)

	public ACMEMidia() {
		try {
			BufferedReader streamEntrada = new BufferedReader(new FileReader("entrada.txt"));
			entrada = new Scanner(streamEntrada);   // Usa como entrada um arquivo
			PrintStream streamSaida = new PrintStream(new File("saida.txt"), Charset.forName("UTF-8"));
			System.setOut(streamSaida);             // Usa como saida um arquivo
		} catch (Exception e) {
			System.out.println(e);
		}
		Locale.setDefault(Locale.ENGLISH);   // Ajusta para ponto decimal
		entrada.useLocale(Locale.ENGLISH);

		midiateca = new Midiateca();
	}

	public void executa() {
		cadastrarVideo();					//1
		cadastrarMusica();					//2
		mostrarDadosPorCodigo();			//3
		mostrarDadosPorCategoria();			//4
		mostrarDadosPorQualidade();			//5
		dadosMaiorMusica();					//6
		removerMidia();						//7
		somatorioLocacoesMidias();			//8
		dadosMusicaLocacaoPertoMedia();		//9
		dadosMidiaMaisNova();				//10
	}

	public void cadastrarVideo() {									//1
		int codigo = 0;
		String titulo = "";
		int ano = 0;
		String categoria;
		int qualidade = 0;
		while(codigo != -1) {
			codigo = entrada.nextInt();
			//entrada.nextLine();
			if(codigo != -1) {
				entrada.nextLine();
				titulo = entrada.nextLine();
				ano = entrada.nextInt();
				entrada.nextLine();
				categoria = entrada.nextLine().toUpperCase();
				qualidade = entrada.nextInt();
				entrada.nextLine();
				if(midiateca.codigoRepetido(codigo)) {
					System.out.println("1:Erro-video com codigo repetido: " + codigo);
				} else {
				Video videoadicionado = new Video(codigo, titulo, ano, Categoria.valueOf(categoria), qualidade);
				if(midiateca.cadastraMidia(videoadicionado)) {
					//midiateca.cadastraMidia(videoadicionado);
					System.out.println("1:" + videoadicionado);
					}
				}
			}
		}
		entrada.nextLine();
	}

	public void cadastrarMusica() {									//2
		int codigo = 0;
		String titulo = "";
		int ano = 0;
		String categoria;
		double duracao = 0;
		while (codigo != -1) {
			//entrada.nextLine();
			codigo = entrada.nextInt();
			//entrada.nextLine();
			if (codigo != -1) {
				entrada.nextLine();
				titulo = entrada.nextLine();
				ano = entrada.nextInt();
				entrada.nextLine();
				categoria = entrada.nextLine().toUpperCase();
				duracao = entrada.nextDouble();
				entrada.nextLine();
				if (midiateca.codigoRepetido(codigo)) {
					System.out.println("2:Erro-musica com codigo repetido: " + codigo);
				} else {
					Musica musicaadicionada = new Musica(codigo, titulo, ano, Categoria.valueOf(categoria), duracao);
					if (midiateca.cadastraMidia(musicaadicionada)) {
					//midiateca.cadastraMidia(musicaadicionada);
					System.out.println("2:" + musicaadicionada);
					}
				}
			}
		}
	}

	public void mostrarDadosPorCodigo() {							//3
		int codigo = entrada.nextInt();
		entrada.nextLine();
		Midia midia = midiateca.consultaPorCodigo(codigo);
		if(midia != null) {
		System.out.println("3:" + midia + ", " + midia.calculaLocacao());
		} else {
			System.out.println("3:Codigo inexistente.");
		}
	}


	public void mostrarDadosPorCategoria() {						//4
		String categoria = entrada.nextLine().toUpperCase();
		if(!categoria.equals("ACA") && !categoria.equals("DRA") && !categoria.equals("FIC") && !categoria.equals("ROM")) {
			System.out.println("4:Nenhuma midia encontrada.");
		} else {
		ArrayList<Midia> midias = midiateca.consultaPorCategoria(Categoria.valueOf(categoria));
		if(midias.isEmpty()) {
			System.out.println("4:Nenhuma midia encontrada.");
		} else {
			for(Midia midia : midias) {
				System.out.println("4:" + midia + ", " + midia.calculaLocacao());
				}
			}
		}
	}




	public void mostrarDadosPorQualidade() {						//5
		int qualidade = entrada.nextInt();
		entrada.nextLine();
		ArrayList<Video> videosDaQualidade = new ArrayList<>();
		ArrayList<Video> videos = midiateca.separarVideos();
		for(Video video : videos) {
			if(video.getQualidade() == qualidade) {
				videosDaQualidade.add(video);
			}
		}
		if(videosDaQualidade.isEmpty()) {
			System.out.println("5:Qualidade inexistente.");
		} else {
			for(Video video : videosDaQualidade) {
				System.out.println("5:" + video + ", " + video.calculaLocacao());
			}
		}
	}


	public void dadosMaiorMusica() {								//6
		ArrayList<Musica> musicas = midiateca.separarMusicas();
		if(musicas.isEmpty()) {
			System.out.println("6:Nenhuma musica encontrada.");
		} else {
			Musica musicaDeMaiorDuracao = musicas.get(0);
			for(Musica musica : musicas) {
				if(musica.getDuracao() > musicaDeMaiorDuracao.getDuracao()) {
					musicaDeMaiorDuracao = musica;
				}
			}
			System.out.println("6:" + musicaDeMaiorDuracao.getTitulo() + "," + musicaDeMaiorDuracao.getDuracao());
		}
	}

	public void removerMidia() {									//7
		int codigo = entrada.nextInt();
		//entrada.nextLine();
		Midia midia = midiateca.consultaPorCodigo(codigo);
		if(midiateca.removeMidia(codigo)) {
			System.out.println("7:" + midia + ", " + midia.calculaLocacao());
			//midiateca.removeMidia(codigo);
			//
		} else {
			System.out.println("7:Codigo inexistente.");
		}
	}


	public void somatorioLocacoesMidias() {							//8
		double somatorio = midiateca.somatorioLocacoes();
		double somatorioArredondado = 0;
		if(somatorio == 0) {
			System.out.println("8:Nenhuma midia encontrada");
		} else {
			somatorioArredondado = Math.round(somatorio * 100.0) / 100.0;
			System.out.println("8:" + somatorioArredondado );
		}
	}


	public void dadosMusicaLocacaoPertoMedia() {					//9
		double media = midiateca.mediaLocacoesMusicas();
		Musica musica = midiateca.musicaPertoDaMedia();
		if(musica == null) {
			System.out.println("9:Nenhuma musica encontrada.");
		} else {
			System.out.println("9:" + media + ", " + musica + ", " + musica.calculaLocacao());
		}
		}


	public void dadosMidiaMaisNova() {
		Midia midiaMaisNova = midiateca.MidiaMaisNova();
		if(midiaMaisNova == null) {
			System.out.println("10:Nenhuma midia encontrada.");
		} else {
			System.out.println("10:" + midiaMaisNova.getCodigo() + "," + midiaMaisNova.getTitulo() + "," + midiaMaisNova.getAno());
		}
	}

















}
