package prova;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Finder extends Thread {
	String type;
	String name;
	int size;
	String path;
	Thread thread = Thread.currentThread();

	// Método onde faço a thread rodar a função de busca
	@Override
	public void run() {
		try {
			// Aqui rodo a função de busca com os parâmetros recebidos no construtor
			findFile(this.type, this.name, this.size, this.path);
		} catch (IOException e) {
			// Print de possiveis erros
			e.printStackTrace();
		}
	}

	public Finder(String type, String name, int size, String path) throws IOException {
		// Atribuição de valores recebidos no construtor
		this.type = type;
		this.name = name;
		this.size = size;
		this.path = path;

		// Inicio da execução da thread
		start();
		
		try {
			join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	void findFile(String type, String name, int size, String path) throws IOException {

		// Criação do da pasta onde vão ser executadas as buscas
		File folder = new File(path);

		// Loop que irá iterar sobre todos os arquivos da pasta instanciada
		for (File file : folder.listFiles()) {
			// Verificação se o arquivo na vez é um diretório
			if (file.isDirectory()) {

				// Criação de caminho novo com adição do nome da pasta
				String newPath = path + "/" + file.getName();

				// Criação de nova thread utilizando os argumentos
				// recebidos junto do caminho criado
				Finder newFinder = new Finder(type, name, size, newPath);

			} else {
				// Caso arquivo não seja um diretório, veficasse se os seus parâmetros
				// condizem com os parâmetros recebidos
				if (file.getName().contains(name + type) && file.length() == size) {

					// Print do nome do arquivo, junto do nome da thread onde foi achado e do nome
					// do diretório onde foi achado
					System.out.println("Achei o arquivo " + file.getName() + " na " + thread.getName()
							+ " dentro da pasta /" + file.getParentFile().getName());
					// Programa para de executar
					System.exit(0);
				} else {
					// Print caso não seja o arquivo que estamos procurando
					System.out.println("Not a match on " + thread.getName());
				}
			}
		}
	}
}
