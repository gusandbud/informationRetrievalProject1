package informationRetrievalProj1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.conf.ConfigurationBuilder;

public class retrieve {	
	private static int[] hashtagCount;
	static String[] Syr;
	static String[] ND;
	static int currCount = 0;
	
	public static void main(String[] args) throws TwitterException{
		
		//MineFromTwitter m = new MineFromTwitter();
		List<Status> lND = new ArrayList<Status>();
		List<Status> lSyr = new ArrayList<Status>();
		List<Status> lPM = new ArrayList<Status>();
		File fSyr, fND;
		String db = "twitter", table;
		storeToDB stDB = new storeToDB();
		
		fSyr = new File("SearchSyriza.txt");
		fND = new File("SearchND.txt");
		
		Syr = parseFile(fSyr);
		ND = parseFile(fND);
		
		hashtagCount = new int[Syr.length + ND.length];
		
		lND = startSearch(ND);
		stDB.store(lND, "ND", "hashtagBased");
		
		lSyr = startSearch(Syr);
		stDB.store(lSyr, "Syriza", "hashtagBased");
		
		lPM = startSearch("from:PrimeministerGR");
		stDB.store(lPM, "@PrimeministerGR", "timelineBased");
		
		
		
		
		for (Status tweet : lND) {
			System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
			System.out.println(tweet.getCreatedAt());
		}

		for (Status tweet : lSyr) {
			System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
			System.out.println(tweet.getCreatedAt());
		}
		
		for (Status tweet : lPM) {
            System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
            System.out.println(tweet.getCreatedAt());
		}
		
		System.out.println("@PrimeministerGR has sent " + lPM.size() + " tweets");
		System.out.println("Retrieved " + lND.size() + " tweets for ND");
		System.out.println("Retrieved " + lSyr.size() + " tweets for Syriza");
		for (int i = 0; i<hashtagCount.length; i++){
			if(i < ND.length) System.out.println("Hash " + ND[i] + ":" + hashtagCount[i]);
			else System.out.println("Hash " + Syr[i-ND.length] + ":" + hashtagCount[i]);
		}
	}
	
	private static List<Status> startSearch(String[] ar){
		MineFromTwitter m = new MineFromTwitter();
		List<Status> templ = new ArrayList<Status>();
		List<Status> l = new ArrayList<Status>();
		String query = null;
		for(int i=0; i< ar.length; i++){
			query = ar[i];
			try {
				templ = m.getRes(query);
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			hashtagCount[currCount] = templ.size();
			currCount += 1;
			l.addAll(templ);
		}
		return l;
	}
	
	public static String[] parseFile(File f){
		String[] hashArray;
		List<String> l = new ArrayList<String>();
		Scanner sc = null;
		try {
			sc = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(sc.hasNextLine()){
			l.add(sc.nextLine());
		}
		hashArray = (String[]) l.toArray(new String[l.size()]);
		return hashArray;
	}
	
	private static List<Status> startSearch(String q){
		MineFromTwitter m = new MineFromTwitter();
		List<Status> l = new ArrayList<Status>();
		try {
			l = m.getRes(q);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		return l;
	}
}
