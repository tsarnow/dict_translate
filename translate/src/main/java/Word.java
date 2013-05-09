public class Word implements Comparable<Word> {

	private int wordCount;
	private String word;

	public Word(String word, int wordCount) {
		this.wordCount = wordCount;
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getWordCount() {
		return wordCount;
	}

	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}

	public int compareTo(Word word) {
		int result = -1;
		if (word != null) {
			result = Integer.compare(word.getWordCount(), this.getWordCount());
		} 
		
		return result;
	}
	
	@Override
	public String toString() {
		return wordCount + " " + word;
	}
}
