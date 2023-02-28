package prog12;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public class Noogle implements SearchEngine{
	
	Disk<List<Long>> wordDisk = new Disk<List<Long>>();
	RAMTable wordIndex = new RAMTable();
	
	Disk<PageFile> pageDisk = new Disk<PageFile>();
	DiskTrie urlIndex = new DiskTrie();
	
	@Override
	public void collect(Browser browser, List<String> startingURLs) {
		
		if (true) {
	        urlIndex.read(pageDisk);
	        wordIndex.read(wordDisk);
	        System.out.println(urlIndex);
	        System.out.println(pageDisk);
	        System.out.println(wordIndex);
	        System.out.println(wordDisk);
	        return;
	      }
		ArrayDeque<Long> upcomingFiles = new ArrayDeque<Long>();
		
		System.out.println("starting pages " + startingURLs.toString());
		
		for (String url : startingURLs) {
			if (!urlIndex.containsKey(url)) {
				long index = indexPage(url);
				upcomingFiles.add(index);
			}
			
			while(upcomingFiles.peek() != null) {
				
				System.out.println("queue " + upcomingFiles.toString());
				
				long currentPage = upcomingFiles.pop();
				System.out.println("dequeued " + pageDisk.get(currentPage));
				boolean didLoad = browser.loadPage(pageDisk.get(currentPage).url);
				
				if(didLoad) {
					List<String> linkedURLs = browser.getURLs();
					List<String> wordsOnPage = browser.getWords();
					System.out.println("urls " + linkedURLs.toString());
					Set<Long> pageIndicies = new HashSet<Long>();
					
					for (String linkedURL : linkedURLs) {
						if (!urlIndex.containsKey(linkedURL)) {
							long index = indexPage(linkedURL);
							upcomingFiles.add(index);
						}
						pageIndicies.add(urlIndex.get(linkedURL));
					}
					
					
					
					for(Long pageIndex : pageIndicies) {
						pageDisk.get(pageIndex).incRefCount();
						System.out.println("ref++ " + pageDisk.get(pageIndex));
					}
					
					System.out.println("words " + wordsOnPage.toString());
					
					for (String word : wordsOnPage) {
						
						if(!wordIndex.containsKey(word)) {
							indexWord(word);
						}
						long index = wordIndex.get(word);
						
						if (wordDisk.get(index).size() > 0 && wordDisk.get(index).get( wordDisk.get(index).size()-1 ) == currentPage) {
							continue;
						}
						
						else {
							wordDisk.get(index).add(currentPage);
							System.out.println("added page index " + index + "(" + word + ")" + wordDisk.get(index).toString());
						}
					}	
				}
			}
			
			System.out.println("map from URL to page index");
			System.out.println(urlIndex.toString());
			System.out.println("map from page index to URL");
			System.out.println(pageDisk.toString());
			System.out.println("map from word to word index");
			System.out.println(wordIndex.toString());
			System.out.println("map from word index to word file");
			System.out.println(wordDisk.toString());
		}
	}

	@Override
	public String[] search(List<String> searchWords, int numResults) {
		return new String[0];
	}
	
	public long indexPage(String url) {
		long index = pageDisk.newFile();
		PageFile addedPage = new PageFile(index, url);
		pageDisk.put(index, addedPage);
		urlIndex.put(url, index);
		System.out.println("indexing page " + addedPage);
		return index;
	}
	
	public long indexWord(String word) {
		long index = wordDisk.newFile();
		List<Long> onSites = new ArrayList<Long>();
		wordDisk.put(index, onSites);
		wordIndex.put(word, index);
		System.out.println("indexing word " + index + "(" + word + ")" + wordDisk.get(index));
		return index;
	}
}