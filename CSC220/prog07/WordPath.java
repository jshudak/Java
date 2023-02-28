package prog07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import prog02.GUI;
import prog02.UserInterface;
import prog06.LinkedQueue;
import prog06.LinkedQueue.Entry;
import java.util.PriorityQueue;

public class WordPath {
	protected  static UserInterface ui;
	protected static List<Entry> wordEntries = new ArrayList<Entry>();
		
	public WordPath(UserInterface impUi) {
		ui = impUi;
	}

	public static void main(String [] args) throws FileNotFoundException  {
		
		WordPath game = new WordPath(new GUI("prog06 game"));
		String start, end;
		
		String fileIn = ui.getInfo("What word file should I reference");
		game.loadWords(fileIn);
		
		start  = ui.getInfo("Type starting word:");
		end  = ui.getInfo("Type ending word:");
		
		String[] commands = { "Human plays.", "Computer plays.", "Solve2 plays.", "Solve3 plays" };
		
		int c = ui.getCommand(commands);
		switch(c) {
		case 0:
			game.play(start, end);
			break;
		case 1:
			game.solve(start, end);
			break;
			
		case 2:
			game.solve2(start, end);
			break;
			
		case 3:
			game.solve3(start, end);
			break;
		}
	}
	
	public void play(String start, String end) {
		String cWord;
		while (true) {
			ui.sendMessage("Current word: " + start + "\nEnd word: " + end);
			cWord = ui.getInfo("What is your next word:");
			
			if (cWord == null) {
				break;
			}
			
			else if (oneL(start, cWord) == false) {
				ui.sendMessage(cWord + " is more than 1 letter off from " + start + "\n "
						    + "              Please try again.");
				continue;
			}
			
			else if (find(cWord) == null) {
				ui.sendMessage(cWord + " does not exist in the Dictionary.");
				continue;
			}
			
			start = cWord;
			
			if (start.equalsIgnoreCase(end)) {
				ui.sendMessage("You win!!");
				break;
			}
			
		}
	}
	
	public boolean oneL(String first, String cWord) {
		int offBy = 0;
		
		if (first.length() != cWord.length())
			return false;
		
		for (int i = 0; i < first.length(); i++) {
			if (first.charAt(i) != cWord.charAt(i))
				offBy++;
		}
		
		if (offBy > 1)
			return false;
		
		return true;
	}
	
	public void loadWords(String fileIn) throws FileNotFoundException {
		File dict = new File(fileIn);
		
		Scanner readD = new Scanner(dict);
		
		while(readD.hasNext() == true) {
		Entry newWord = new Entry(readD.next());
		wordEntries.add(newWord);
		}
		
	}
	
	public Entry find(String word) {
		for (Entry words : wordEntries) {
			if(words.word.equals(word)) {
				return words;
			}
		}
		return null;
	}
	
	public void solve(String start, String end) {
		Queue<Entry> potWords = new LinkedQueue<Entry>();
		int counter = 0;
		String success = "\n";
		Entry startEnt = find(start);
		potWords.offer(startEnt);
		
		while(potWords.peek() != null) {
			Entry theEntry = potWords.poll();
			counter++;
			
			for (Entry words : wordEntries) {
				if (oneL(theEntry.word.toString(), words.word.toString()) == true) {
					if (words.word.equals(start)) { 
						continue;
						
					}
					else if (words.prev != null) { 
						continue;
					}
					
					Entry nextEntry = words;
					nextEntry.prev = theEntry;
					potWords.offer(nextEntry);
					
					// THIS is reading the queue backwards
					
					if(words.word.equals(end)) {
						Entry goBack = words;
						
						while(goBack.prev != null) {
							goBack = goBack.prev;
						if (goBack.prev == null) {
							success += goBack.word + "\n";
							goBack = words;
							
							while (goBack.prev.prev != null) {
								goBack = goBack.prev;
							}
							goBack.prev = null;
							goBack = words;
							}
						}
						
						if (goBack.prev == null) {
							success += goBack.word + "\n";
							goBack = words;
						}
						
						// ALLLL the way down to here.
						
						// This just prints out the success string
						ui.sendMessage("Result found:" + success + " in " + counter + " searches.");
						return;
					}
					
					
				}
			}
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void solve2(String start, String end) {
		
		Queue<Entry> potWords = new PriorityQueue<Entry>(new EntryComparator(end));
		int counter = 0;
		Entry nextEntry = null;
		String success = "\n";
		Entry startEnt = find(start);
		potWords.offer(startEnt);
		
		while(potWords.peek() != null) {
			Entry theEntry = potWords.poll();
			counter++;
			
			for (Entry words : wordEntries) {
				if (oneL(theEntry.word.toString(), words.word.toString()) == true) {
					if (words.word.equals(start)) { 
						continue;
						
					}
					else if (words.prev != null) { 
						continue;
					}
					
					nextEntry = words;
					nextEntry.prev = theEntry;
					potWords.offer(nextEntry);
					
					// THIS is reading the queue backwards
					
					if(words.word.equals(end)) {
						Entry goBack = words;
						
						while(goBack.prev != null) {
							goBack = goBack.prev;
						if (goBack.prev == null) {
							success += goBack.word + "\n";
							goBack = words;
							
							while (goBack.prev.prev != null) {
								goBack = goBack.prev;
							}
							goBack.prev = null;
							goBack = words;
							}
						}
						
						if (goBack.prev == null) {
							success += goBack.word + "\n";
							goBack = words;
						}
						
						// ALLLL the way down to here.
						
						// This just prints out the success string
						ui.sendMessage("Result found:" + success + " in " + counter + " searches.");
						return;
					}
					
					
				}
			}
		}
		
	}
	
@SuppressWarnings({ "unchecked", "rawtypes" })
public void solve3(String start, String end) {
		
		Queue<Entry> potWords = new PriorityQueue<Entry>(new EntryComparator(end));
		int counter = 0;
		String success = "\n";
		Entry startEnt = find(start);
		potWords.offer(startEnt);
		
		while(potWords.peek() != null) {
			Entry theEntry = potWords.poll();
			counter++;
			
			for (Entry nextEntry : wordEntries) {
				if (oneL(theEntry.word.toString(), nextEntry.word.toString()) == true) {
					if (nextEntry.word.equals(start)) { 
						continue;
						
					}
					else if(distanceToStart(nextEntry) > distanceToStart(theEntry)+1) {
						nextEntry.prev = theEntry;
						potWords.remove(nextEntry);
						potWords.offer(nextEntry);
						continue;
					}
					else if (nextEntry.prev != null) { 
						continue;
					}
					
					nextEntry.prev = theEntry;
					potWords.offer(nextEntry);
						
					
					
					// THIS is reading the queue backwards					
					if(nextEntry.word.equals(end)) {
						Entry goBack = nextEntry;
						
						while(goBack.prev != null) {
							goBack = goBack.prev;
						if (goBack.prev == null) {
							success += goBack.word + "\n";
							goBack = nextEntry;
							
							while (goBack.prev.prev != null) {
								goBack = goBack.prev;
							}
							goBack.prev = null;
							goBack = nextEntry;
							}
						}
						
						if (goBack.prev == null) {
							success += goBack.word + "\n";
							goBack = nextEntry;
						}
						
						// ALLLL the way down to here.
						
						// This just prints out the success string
						ui.sendMessage("Result found:" + success + "in " + counter + " searches.");
						return;
					}
					
					
				}
			}
		}
		
	}

	public int lettersDifferent(String word, String target) {
		int offBy = 0;
		
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) != target.charAt(i))
				offBy++;
		}
		
		return offBy;
	}
	
	public int distanceToStart(Entry entry) {
		int counter = 0;
		
		while(entry.prev != null) {
			counter++;
			entry = entry.prev;
		}
		return counter;
	}
	
	protected class EntryComparator implements Comparator<Entry> {
		String target;
		
		EntryComparator(String target) {
			this.target = target;
		}
		
		int priority(Entry entry) {
			return lettersDifferent(entry.word.toString(), target) + distanceToStart(entry);
		}

		public int compare(Entry o1, Entry o2) {
			return priority(o1) - priority(o2);
		}
	}
	
	/** A Entry is the building block for a singly-linked list. */
	  protected static class Entry<E> {
	    // Data Fields

	    /** The reference to the element. */
	    protected E word;
	    /** The reference to the next entry. */
	    protected Entry<E> prev;

	    // Constructors
	    /**
	     * Creates a new entry with a null next field.
	     * @param element The element stored
	     */
	    protected Entry (E element) {
	      word = element;
	    }

	    /**
	     * Creates a new entry that references another entry.
	     * @param element The element stored
	     * @param next The entry referenced by new entry
	     */
	    protected Entry (E element, Entry<E> prev) {
	      word = element;
	      this.prev = prev;
	    }
	  } //end class Entry
}
