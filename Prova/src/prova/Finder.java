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
	Thread atual = Thread.currentThread();

	@Override
	public void run() {
		try {
			findFile(this.type, this.name, this.size, this.path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Finder(String type, String name, int size, String path) throws IOException {
		this.type = type;
		this.name = name;
		this.size = size;
		this.path = path;

		start();

		try {
			join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	void findFile(String type, String name, int size, String path) throws IOException {

		File folder = new File(path);

		for (File file : folder.listFiles()) {
			System.out.println();
			if (file.isDirectory()) {
				String newPath = path + "/" + file.getName();
				Finder teste = new Finder(type, name, size, newPath);
			} else {
				if (file.getName().contains(type) && file.getName().contains(name) && file.length() == size) {
					System.out.println("Achei o arquivo " + file.getName() + " na " + atual.getName()
							+ " dentro da pasta /" + file.getParentFile().getName());
					System.exit(0);
				} else {
					System.out.println("Not a match on " + atual.getName());
				}
			}
		}
	}

	String readFile(File file) throws IOException {
		String result = Files.readString(Paths.get(file.getPath()));
		return result;
	}

	String getFileDate(File file) throws IOException {
		Path filePath = Paths.get(file.getPath());

		BasicFileAttributes fileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(fileAttributes.creationTime().to(TimeUnit.MILLISECONDS)));

		String finalDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		String finalMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String finalYear = String.valueOf(calendar.get(Calendar.YEAR));

		String finalDate = finalDay + "/" + finalMonth + "/" + finalYear;

		return finalDate;
	}
}
