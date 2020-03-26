import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

class Main {

	public static void main(String[] args) {

		File file = new File("doc.txt");

		String content = null;
		try {
			try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.name())) {
				content = scanner.useDelimiter("\\A").next();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(content);
	}
}