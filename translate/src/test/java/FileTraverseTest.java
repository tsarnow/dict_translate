import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.junit.Ignore;
import org.junit.Test;


public class FileTraverseTest {

	@Ignore
	@Test
	public void traverseFile() throws IOException {
		File f = new File("./src/test/resources/translate.txt");
		assertTrue(f.canRead());
		LineIterator li = FileUtils.lineIterator(f, "UTF-8");
		
		li.nextLine();
		while (li.hasNext()) {
			String line = li.nextLine();
			assertNotNull(line);
			System.out.println(line);
			assertEquals("about", line);
			break;
		}
		li.close();
	}
	
	@Test
	public void writeTranslatedFile() throws IOException {
		File f = new File("./src/test/resources/out.txt");
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<10; i++) {
			sb.append("about;");
			
			for (int j=0; j<3; j++) {
				sb.append("word").append(j).append(';');
			}
			sb.append(System.lineSeparator());
		}
		
		FileUtils.writeStringToFile(f, sb.toString(), "UTF-8");
	}
	
}
