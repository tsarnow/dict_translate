import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class DictAdapter {

	public static List<String> translateEnglishWord(String word, int alternatives) throws IOException {
		if (alternatives <= 0) {
			alternatives = 1;
		}
		
		Connection con = Jsoup.connect("http://en-de.dict.cc/");
		con = con.data("s", word).timeout(500);
		con.header("User-Agent", "Safari/537.11");
		Document doc = con.get();
		
		Elements s = doc.select("div[style*=color:#999]");
		return getTopTranslations(s, alternatives);
	}
	
	public static List<String> getTopTranslations(Elements elements, int alternatives) {
		List<Word> wordList = new ArrayList<Word>();
		
		for (Element e : elements) {
			String count = e.childNode(0).toString().trim();
			String word = e.nextSibling().childNode(0).toString();
			//System.out.println(count + " " + word);
			wordList.add(new Word(StringEscapeUtils.unescapeHtml4(word), Integer.parseInt(count)));
		}
		
		Collections.sort(wordList);
		
		List<String> resultList = new ArrayList<String>();
		for (int i=0; i<alternatives && i<wordList.size(); i++) {
			resultList.add(wordList.get(i).getWord());
		}
		
		return resultList;
	}
}
