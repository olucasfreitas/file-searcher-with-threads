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
import java.util.ArrayList;
import java.util.List;

public class Finder extends Thread {
	String type;
	String date;
	int size;
	String path;
	Thread thread = Thread.currentThread();

	// Método onde faço a thread rodar a função de busca
	@Override
	public void run() {
		try {
			// Aqui rodo a função de busca com os parâmetros recebidos no construtor
			findFile(this.type, this.date, this.size, this.path);
		} catch (IOException e) {
			// Print de possiveis erros
			e.printStackTrace();
		}
	}

	public Finder(String type, String date, int size, String path) throws IOException {
		// Atribuição de valores recebidos no construtor
		this.type = type;
		this.date = date;
		this.size = size;
		this.path = path;

		// Inicio da execução da thread
		start();
	}

	void findFile(String type, String date, int size, String path) throws IOException {
		
		// Lista de arquivos que vai ser printada no final
		List<FileInfo> fileArray = new ArrayList<FileInfo>();

		// Criação do da pasta onde vão ser executadas as buscas
		File folder = new File(path);

		for (File file : folder.listFiles()) {
			// Verificação se o arquivo na vez é um diretório
			if (file.isDirectory()) {

				// Criação de caminho novo com adição do nome da pasta
				String newPath = path + "/" + file.getName();

				// Criação de nova thread utilizando os argumentos
				// recebidos junto do caminho criado
				Finder newFinder = new Finder(type, date, size, newPath);

			} else {

				String fileTime = getFileDate(file);
				
				// Caso arquivo não seja um diretório, verifica-se se algum dos parâmetros
				// condizem com os parâmetros recebidos na função e se esse arquivo 
				// já não está no array
				if ((file.length() == size || 
						fileTime.contains(date)|| 
						file.getName().contains(type)) &&
						!fileArray.contains(file)) {
					
					// Criação do objeto que vai conter as informações do arquvivo
					FileInfo newFile = new FileInfo(file.getName(), 
							thread.getName(), 
							file.getParentFile().getName());

					// Adição do objeto novo ao array de arquivos
					fileArray.add(newFile);
				}
			}
		}

		// Print dos arquivos que atendem as condições
		for (FileInfo file : fileArray) {
			System.out.println("Achei o arquivo " + file.fileName + " na " + file.threadName + " dentro da pasta /"
					+ file.parentName);
		}
	}

	// Função para formatação da data
	String getFileDate(File file) throws IOException {
		Path filePath = Paths.get(file.getPath());

		BasicFileAttributes fileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(fileAttributes.creationTime().to(TimeUnit.MILLISECONDS)));

		String finalDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		String finalMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String finalYear = String.valueOf(calendar.get(Calendar.YEAR));

		// Criação da data em formato D/M/YYYY
		String finalDate = finalDay + "/" + finalMonth + "/" + finalYear;

		return finalDate;
	}
}
