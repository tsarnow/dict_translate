import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Ignore;
import org.junit.Test;


public class DictAdapterTest {
	
	@Test
	public void wordOrderTest() {
		Word w1 = new Word("test1", 10);
		Word w2 = new Word("test2", 20);
		Word w3 = new Word("test3", 1);
		Word w4 = new Word("test4", 50);
		
		List<Word> wordList = new ArrayList<Word>(4);
		wordList.add(w1);
		wordList.add(w2);
		wordList.add(w3);
		wordList.add(w4);
		
		Collections.sort(wordList);
		assertEquals(w4, wordList.get(0));
		assertEquals(w2, wordList.get(1));
		assertEquals(w1, wordList.get(2));
		assertEquals(w3, wordList.get(3));
	}

	@Ignore
	@Test
	public void callDictTranslateTest() throws IOException {
		Connection con = Jsoup.connect("http://www.dict.cc/");
		con = con.data("s", "after").timeout(500);
		con.header("User-Agent", "Safari/537.11");
		Document doc = con.get();
		String title = doc.title();
		assertNotNull(title);
		
		Element firstRow = doc.getElementById("tr1");
		assertNotNull(firstRow);
		
		Element englishCell = firstRow.child(1);
		Element germanCell = firstRow.child(2);
		assertNotNull(englishCell);
		assertNotNull(germanCell);
		
		String englishWord = englishCell.child(0).child(0).childNode(0).toString();
		assertNotNull(englishWord);
		assertEquals("after", englishWord);
		
		String germanWord = germanCell.child(1).childNode(0).toString();
		assertNotNull(germanWord);
		
		assertEquals("danach", StringEscapeUtils.unescapeHtml4(germanWord));
	}
	
	@Test
	public void getTheFirstBestThreeTranslationsTest() throws IOException {
		Connection con = Jsoup.connect("http://en-de.dict.cc/");
		con = con.data("s", "an").timeout(500);
		con.header("User-Agent", "Safari/537.11");
		Document doc = con.get();
		String title = doc.title();
		assertNotNull(title);
		
		//Element row = doc.getElementById("tr14");
		//assertNotNull(row);
		
		List<Word> wordList = new ArrayList<Word>();
		
		Elements s = doc.select("div[style*=color:#999]");
		for (Element e : s) {
			String count = e.childNode(0).toString().trim();
			String word = e.nextSibling().childNode(0).toString();
			System.out.println(count + " " + word);
			wordList.add(new Word(word, Integer.parseInt(count)));
		}
		
		Collections.sort(wordList);
		System.out.println(wordList);
	}
}
