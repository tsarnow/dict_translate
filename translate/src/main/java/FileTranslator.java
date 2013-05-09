import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;


public class FileTranslator {
	
	private static Logger log = Logger.getLogger(FileTranslator.class.getName());

	public static void performTranslation(File source, File target, boolean skipFirstLine) throws IOException, InterruptedException {
		LineIterator li = FileUtils.lineIterator(source, "UTF-8");
		StringBuffer sb = new StringBuffer();
		
		if (skipFirstLine) li.nextLine();
		log.info(new Date().toString());
		
		while (li.hasNext()) {
			String line = li.nextLine();
			List<String> translations = tryToRead(line, 1000);
			sb.append(line).append(';');
			sb.append(StringUtils.join(translations, ';'));
			sb.append(System.lineSeparator());
			log.info(sb.toString());
			
			Thread.sleep(100);
		}
		log.info(new Date().toString());
		FileUtils.writeStringToFile(target, sb.toString(), "UTF-8");
		
		li.close();
	}
	
	private static List<String> tryToRead(String line, int maxRead) throws IOException, InterruptedException {
		List<String> translations=null;
		for (int i=0; i<maxRead; i++) {
			try {
				translations = DictAdapter.translateEnglishWord(line, 3);
			} catch (SocketTimeoutException e) {
				log.severe(e.getMessage());
				Thread.sleep(1000);
				continue;
			}
			return translations;
		}
		return translations;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		File source = null;
		File target = null;
		
		if (args != null && args.length == 2) {
			source = new File(args[0]);
			target = new File(args[1]);
		} else {
			source = new File("./src/test/resources/translate.txt");
			target = new File("./src/test/resources/out.txt");
		}
		
		performTranslation(source, target, true);
	}
}
