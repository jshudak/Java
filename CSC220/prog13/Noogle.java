package prog13;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Comparator;

import prog12.Browser;
import prog12.BetterBrowser;
import prog12.SearchEngine;
import prog12.Disk;
import prog12.PageFile;
import prog12.DiskTrie;
import prog12.RAMTable;

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
		Iterator<Long>[] wordFileIterators = (Iterator<Long>[]) new Iterator[searchWords.size()];
		long[] currentPageIndexes = new long[searchWords.size()];
		PriorityQueue<Long> bestPageIndexes = new PriorityQueue<Long>(new PageComparator());
		
		for (int i = 0; i < searchWords.size(); i++) {
			wordFileIterators[i] = wordDisk.get(wordIndex.get(searchWords.get(i))).iterator();
		}
		
		while (getNextPageIndexes(currentPageIndexes, wordFileIterators) == true) {
			if (allEqual(currentPageIndexes)) {
				
				System.out.println(pageDisk.get(currentPageIndexes[0]).toString());
				
				if (numResults != bestPageIndexes.size()) {
					bestPageIndexes.offer(currentPageIndexes[0]);
				}
				else {
					if (pageDisk.get(currentPageIndexes[0]).getRefCount() > pageDisk.get(bestPageIndexes.peek()).getRefCount()) {
						bestPageIndexes.poll();
						bestPageIndexes.offer(currentPageIndexes[0]);
					}
				}
				
			}
		}
		
		if (bestPageIndexes.size() < numResults)
			numResults = bestPageIndexes.size();
		
		String[] results = new String[numResults];
		int i = numResults-1;
		while (bestPageIndexes.peek() != null) {
			results[i] = pageDisk.get(bestPageIndexes.poll()).url;
			i--;
		}
		
		return results;
	}
	
	/** Get the largest element of currentPageIndexes.  If all the
    elements are equal, increment it (largest++).

    For each element of currentPageIndexes that is not equal to
    largest, get the next page index for that word: call next() for
    wordFileIterator[i] and put the result into
    currentPageIndexes[i].  But if hasNext is false, return false
    instead.

    Return true.

    @param currentPageIndexes array of current page indexes
    @param wordFileIterators array of iterators with next page indexes
    @return true if all minimum page indexes updates, false otherwise
	*/
	private boolean getNextPageIndexes (long[] currentPageIndexes, Iterator<Long>[] wordFileIterator) {
		long largest = getLargest(currentPageIndexes);
		
		if (allEqual(currentPageIndexes))
			largest++;
		
		for (int i = 0; i < currentPageIndexes.length; i++) {
			
			if (wordFileIterator[i].hasNext() == false)
				return false;
				
			if (currentPageIndexes[i] != largest)
				currentPageIndexes[i] = wordFileIterator[i].next();
		}
		
		return true;
	}
	
	/** Check if all elements in an array of long are equal.
    @param array an array of numbers
    @return true if all are equal, false otherwise
	 */
	private boolean allEqual (long[] array) {
		int j = 0;
		for (int i = 1; i < array.length; i++) {
			if (array[j] == array[i]) {
				j++;
			}
			else {
				return false;
			}
		}
		return true;
	}
	
	/** Get the largest element of an array of long.
    @param array an array of numbers
    @return largest element
	*/
	private long getLargest (long[] array) {
		long largest= array[0];
		for (int i = 1; i < array.length; i++) {
			if (largest < array[i])
				largest = array[i];
		}
		return largest;
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
	
private class PageComparator implements Comparator<Long>{	
		public PageComparator() {
			
		}
	public int compare(Long index1, Long index2) {
			PageFile page1 = pageDisk.get(index1);
			PageFile page2 = pageDisk.get(index2);
			int refCount = page1.getRefCount() - page2.getRefCount();
			return refCount;
	}
	}
}