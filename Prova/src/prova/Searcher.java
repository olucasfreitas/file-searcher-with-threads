package prova;

import java.io.IOException;
import java.text.ParseException;

public class Searcher {

	public static void main(String[] args) throws ParseException, IOException {
		
		//Variáveis para busca
		String searchPath = "/home/lucas/Documents/Trabalho";
		String fileName = "long-stack-trace-zone.umd.min";
		String fileType = ".js";
		int fileSize = 2193;

		Finder finder = new Finder(fileType, fileName, fileSize, searchPath);
		// Argumentos necessários para busca e seus tipos 
		// String name, String type, int size, String content, String path
	}
}
