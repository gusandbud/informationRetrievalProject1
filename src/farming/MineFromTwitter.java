package farming;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MineFromTwitter {
	
	public List<Status> getRes(String query) throws TwitterException{
		List<Status> results = new ArrayList<Status>();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
			.setOAuthConsumerKey("3ntVpACDR1GqR4Z037SmsOGGo")
			.setOAuthConsumerSecret("S2j9qFdf7TJwVxnukP6OUAL3CcJ7QMpGv9tDUFcrGeHCD7PR51")
			.setOAuthAccessToken("86147214-HsSnu03oJB8YON09gf86Q5FWIUgb9l1oeRMZrPjuN")
			.setOAuthAccessTokenSecret("FYjzkQapfODiy2GFphyVBTnAzQjcJJv83mI0ZDziTQCDT");
		
		Twitter fact = new TwitterFactory(cb.build()).getInstance();
		System.out.println("We're in");
		
		Query q = new Query(query);
		System.out.println("Searching for " + query);
		
		QueryResult qres;
		q.setSince("2016-10-12");
		do {
			qres = fact.search(q);
			
			results.addAll(qres.getTweets());
			System.out.println(results.size());
		} while((q = qres.nextQuery()) != null);
		System.out.println("Searching..");
		
		
		//results = results.parallelStream()
		//		.filter(res -> !(res.isRetweet()))
		//		.filter(res -> !(res.getText().isEmpty() || res.getText() == null))
		//		.collect(Collectors.toList());
		
		System.out.println("Got " + results.size() + " tweets");
		
		return results;
	}
}
