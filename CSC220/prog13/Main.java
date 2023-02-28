package prog13;

import java.io.IOException;
import java.util.*;
import prog02.GUI;
import prog12.Browser;
import prog12.BetterBrowser;
import prog12.SearchEngine;

// import prog12.Noogle;

// import javax.xml.stream.events.StartDocument;

public class Main {
  public static void main(String[] args) throws IOException {
		
    Browser browser = new BetterBrowser();
    SearchEngine noogle = new Noogle();

    List<String> startingURLs = new ArrayList<String>();
    //startingURLs.add("http://www.cs.miami.edu");
    //startingURLs.add("http://www.cs.miami.edu/home/vjm/csc220/google2/1.html");
    //startingURLs.add("http://web.cs.miami.edu/home/jgmaster/google/");
		
    for (int i = 99; i >= 1; i -= 2)
      startingURLs.add("http://www.cs.miami.edu/home/vjm/csc220/google2/" + i + ".html");

    List<String> temp = new ArrayList<String>();

    for (int i = 0; i < startingURLs.size(); i++) {
      temp.add(BetterBrowser.reversePathURL(startingURLs.get(i)));
    }

    startingURLs = temp;

    noogle.collect(browser, startingURLs);

    List<String> keyWords = new ArrayList<String>();
    if (false) {
      keyWords.add("mary");
      keyWords.add("jack");
      keyWords.add("jill");

    } else {
      //keyWords.add("Victor");
      //keyWords.add("Milenkovic");
      GUI gui = new GUI("Noogle");
      while (true) {
        String input = gui.getInfo("Enter search words.");
        if (input == null)
          return;
        String[] words = input.split("\\s", 0);
        keyWords.clear();
        for (String word : words)
          keyWords.add(word);
        String[] urls = noogle.search(keyWords, 5);
        String res = "Found " + keyWords + " on";
        for (int i = 0; i < urls.length; i++)
          res = res + "\n" + BetterBrowser.inverseReversePathURL(urls[i]);
        gui.sendMessage(res);
      }
    }

    String[] urls = noogle.search(keyWords, 5);

    System.out.println("Found " + keyWords + " on");
    for (int i = 0; i < urls.length; i++)
      System.out.println(BetterBrowser.inverseReversePathURL(urls[i]));
		
  }
}
