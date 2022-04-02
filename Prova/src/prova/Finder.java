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
	String name;
	String type;
	String date;
	int size;
	String content;
	String path;
	Thread atual = Thread.currentThread();

	public Finder(
			String name, 
			String type, 
			String date, 
			int size, 
			String content, 
			String path) throws IOException 
	{
		this.name = name;
		this.type = type;
		this.date = date;
		this.size = size;
		this.content = content;
		this.path = path;

		start();

		try {
			join();
			//findFile(this.name, this.type, this.date, this.size, this.content, this.path);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void findFile(
			String name, 
			String type, 
			String date, 
			int size, 
			String content, 
			String path)
			throws IOException
	{
		File folder = new File(path);
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {

				String fileContent = readFile(file);
				String fileTime = getFileDate(file);				

				if (
						file.getName().contains(type) && 
						file.getName().contains(name) && 
						file.length() == size && 
						fileContent.contains(content) &&
						fileTime.contains(date)
						) 
				{
					System.out.println("Arquivo encontrado: " + file.getName());
				} else {
					//System.out.println("Arquivo não encontrado");
				}
				
			} else {
				//System.out.println("Entrou na pasta: " + file.getName());
				String newPath = "/home/lucas/Documents/Prova/" + file.getName();
				new Finder(name, type, date, size, content, newPath);
			}
		}
	}

	public static String readFile(File file) throws IOException {
		String result = Files.readString(Paths.get(file.getPath()));
		return result;
	}
	
	public static String getFileDate(File file) throws IOException {
		Path filePath = Paths.get(file.getPath());
		
		BasicFileAttributes fileAttributes =
                Files.readAttributes(filePath, BasicFileAttributes.class);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(fileAttributes.creationTime().to(TimeUnit.MILLISECONDS)));
		
		String finalDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		String finalMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String finalYear = String.valueOf(calendar.get(Calendar.YEAR));
		
		String finalDate = finalDay + "/" + finalMonth + "/" + finalYear;
		
		return finalDate;
	}
	
	@Override
    public void run() {
        System.out.println("Nome da thread: " + atual.getName());
        try {
			findFile(this.name, this.type, this.date, this.size, this.content, this.path);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
