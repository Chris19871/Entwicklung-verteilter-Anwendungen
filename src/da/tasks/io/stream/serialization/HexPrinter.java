package da.tasks.io.stream.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

public class HexPrinter {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		try (final FileInputStream is = new FileInputStream(new File("out.serial"));
				final PrintStream out = new PrintStream(System.out)) {
			int i = 0;

			while (is.available() > 0) {
				StringBuilder sb = new StringBuilder();
				out.printf("%02X  ", i);
				for (int j = 0; j < 16; j++) {
					if (is.available() > 0) {
						int value = is.read(); // 1 Byte
						sb.append(String.format("%02X ", value));
					}
				}
				out.print(sb);
				out.println();
				i++;
			}
			is.close();
		}
	}
}