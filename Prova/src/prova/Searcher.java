package prova;

import java.io.IOException;
import java.text.ParseException;

public class Searcher {a

	public static void main(String[] args) throws ParseException, IOException {

		String folder = "/home/lucas/Documents/Trabalho/thingable-front";
		String file = "ring_buffer";
		String type = ".js";
		int size = 1671;

		Finder finder = new Finder(type, file, size, folder);

		// String name, String type, String date, int size, String content, String path
	}
}
