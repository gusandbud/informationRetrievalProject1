package Analysis;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mysql.jdbc.Connection;

import farming.storeToDB;
import twitter4j.Status;

public class analyze {
	static String[] Syr;
	static String[] ND;
	
	public static void main(String[] args){
		Connection conn;
		conn = DBfunctions.connect();
		List<Analysis.Status> l = new ArrayList<Analysis.Status>();
		List<Analysis.Status> lND = new ArrayList<Analysis.Status>();
		List<Analysis.Status> lSyr = new ArrayList<Analysis.Status>();
		File fSyr, fND;
		
		fSyr = new File("SearchSyriza.txt");
		fND = new File("SearchND.txt");
		
		Syr = farming.retrieve.parseFile(fSyr);
		ND = farming.retrieve.parseFile(fND);
		
		String[] positiveEmotes = {":)", ":-)", ":D", ":-D", "^_^", "^.^", ":*", ":-*"};
		String[] negativeEmotes = {":(", ":-(", ":'("};
		List<Analysis.Status> SyrPos,NDPos,SyrNeg,NDNeg;
		
		
		try {
			l = DBfunctions.read(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(l.size());
		lSyr = l.parallelStream().filter(s -> s.hashtag.equals("Syriza")).collect(Collectors.toList());
		lSyr = filterMultiple(lSyr, ND);
		lND = filterMultiple(lND, Syr);
		System.out.println(lSyr.size());
		lND = l.parallelStream().filter(s -> s.hashtag.equals("ND")).collect(Collectors.toList());
		System.out.println(lND.size());
		
		SyrPos = count(lSyr,positiveEmotes);
		NDPos = count(lND,positiveEmotes);
		SyrNeg = count(lSyr,negativeEmotes);
		NDNeg = count(lND,negativeEmotes);
		System.out.println("SyrPos :" + SyrPos.size());
		System.out.println(SyrPos);
		System.out.println("SyrNeg :" + SyrNeg.size());
		System.out.println(SyrNeg);
		System.out.println("NDPos :" + NDPos.size());
		System.out.println(NDPos);
		System.out.println("NDNeg :" + NDNeg.size());
		System.out.println(NDNeg);
	}
	
	private static List<Analysis.Status> count(List<Analysis.Status> l, String[] s){
		List<Analysis.Status> c = new ArrayList<Analysis.Status>();
		for (Analysis.Status t : l){
			for (int i = 0; i < s.length; i++){
				if (t.text.contains(s[i])){
					c.add(t);
				}
			}
		}
		return c;
	}
	
	
	private static List<Analysis.Status> filterMultiple(List<Analysis.Status> l, String[] hash){
		List<Analysis.Status> ret = new ArrayList<Analysis.Status>();
		for (Analysis.Status t : l){
			Boolean a = false;
			for (int i = 0; i < hash.length; i++){
				a = (t.text.contains(hash[i]));
				if (a) break;
			}
			if (!a) ret.add(t); 
		}
		return ret;
	}
}
