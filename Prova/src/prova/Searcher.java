package prova;

import java.io.IOException;
import java.text.ParseException;

public class Searcher {

	public static void main(String[] args) throws ParseException, IOException {
		
		//Variáveis para busca
		String searchPath = "/home/lucas/Documents/Trabalho";
		String fileDate = "30/3/2022";
		String fileType = ".json";
		int fileSize = 200;

		Finder finder = new Finder(fileType, fileDate, fileSize, searchPath);
		// Argumentos necessários para busca e seus tipos 
		// String name, String type, int size, String content, String path
	}
}
