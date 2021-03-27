package application;

import java.util.ArrayList;

import javafx.application.Application;

public class Test {
	private static String[] testPhrases = new String [] {
			"Hello!",
			"What is your name?",
			"Where did you go to schol?",
			"What was your GPA in school?",
			"What courses did you take in school?",
			"What projects did you do in school?",
			"Have you done any volutneer work?",
			"What has been your best exprience?",
			"What has been your worst exprince?",
			"What has been your most challenging experience",
			"Why do you want this job?",
			"What are your skills in software?",
			"Do you have any other computer skills?",
			"What is a current goal you have?",
			"Where do you see yourself in ten years?",
			"What's your goal in ten years?",
			"What are your strengths?",
			"What are your weaknesses?",
			"Do you have a hobby?",
			"What's your favourite hoby?",
			"What's your favourite hobby?",
			"Do you have any pets?",
			"Do you have any allgeries?",
			"Do you have any children?",
			"If you could have any superpower, what would it be?",
			"Descirbe your leadership?",
			"Do you believe you're a good fit for this company?",
			"You seem like a cool guy, you're hired!",
			"Are you okay with an annual salary of $1",
			"Do you have any questions?",
			"See you on Monday!",
			"Goodbye!"
	};
	private static String[] testAnswers = new String[testPhrases.length];
	private static String[] dataCleanTest = new String[] {
		"", "",
		"This case has no punctuation marks", "this case has no punctuation marks",
		"Hello!", "hello",
		"How are you?", "how are you",
		"red, orange, yellow, green, blue, purple", "red orange yellow green blue purple",
		"a.b.c.d.e.f.","abcdef",
		".,?!",""
	};
	private static ArrayList<ArrayList<String>> posAnswers = new ArrayList<>();
	private static String[] corefTest= new String[] {
		"","",
		"hello","hello",
		"my cat was on the chair it fell off the chair", "my cat was on the chair my cat fell off the chair",
		"it is what it is", "my cat is what my cat is",
		"it was so cool", "my cat was so cool"
	};
	private static String[] spellTest = new String[] {
		"expreience","experience", 
		"trvel","travel", 
		"goal","goal", 
		"hoby","hobby", 
		"skool","school", 
		"volunter","volunteer", 
		"celery","salary", 
		"skilz","skills", 
		"training","training", 
		"crtfctns","certifications"
	};
	private static double[] spellAnswers = new double[] {
		(8.0/10.0),
		(5.0/6.0),
		1.0,
		(4.0/5.0),
		(4.0/6.0),
		(8.0/9.0),
		(3.0/6.0),
		(4.0/6.0),
		1.0,
		(8.0/14.0)
	};
	
	public static void main(String[] args){
		ChatBot cb = new ChatBot("TestBot");
		cb.sendPhrase("test");
		
		String phrase;
		String cleaned;
		double counter = 0.0;
		int testScore;
		System.out.println("ChatBot dataClean()");
		for(int i=0;i<dataCleanTest.length;i=i+2) {
			phrase = dataCleanTest[i];
			cleaned = cb.dataClean(phrase);
			System.out.println("\"" + phrase + "\" --> \"" + cleaned + "\"");
			if(cleaned.equals(dataCleanTest[i+1])) {++counter;}
		}
		testScore = (int) ((counter/((double) (dataCleanTest.length/2)))*100);
		System.out.println("Test score: " + testScore + "%");
		System.out.println();
		
		counter = 0.0;
		int index = 0;
		String ans;
		System.out.println("ChatBot sendPhrase()");
		for(String str: testPhrases) {
			phrase = str;
			testAnswers[(int)counter] = cb.sendPhrase(phrase);
			ans = testAnswers[(int)counter];
			if(ans != null && ans.length() > 0) {++counter;}
			++index;
		}
		testScore = (int) ((counter/((double) testPhrases.length))*100);
		System.out.println("Test score: " + testScore + "%");
		System.out.println();
		
		ArrayList<String> pos;
		counter = 0.0;
		System.out.println("ChatBot pos()");
		for(String str: testPhrases) {
			pos = cb.pos(str);
			posAnswers.add(pos);
			System.out.println("\"" + str + "\" --> " + pos.toString());
			if(pos.size() > 0) {++counter;}
		}
		testScore = (int) (((counter/(double) testPhrases.length))*100);
		System.out.println("Test score: " + testScore + "%");
		System.out.println();
		
		counter = 0.0;
		String coref;
		System.out.println("ChatBot coreference()");
		for(int i=0;i<corefTest.length;i=i+2) {
			phrase = corefTest[i];
			coref = cb.coreference(phrase);
			System.out.println("\"" + phrase + "\" --> \"" + coref + "\"");
			if(coref.equals(corefTest[i+1])) {++counter;}
		}
		testScore = (int) ((counter/((double) (corefTest.length/2)))*100);
		System.out.println("Test score: " + testScore + "%");
		System.out.println();
		
		counter = 0.0;
		index = 0;
		String ner;
		System.out.println("ChatBot NER()");
		for(String str: testPhrases) {
			try {
				phrase = str;
				ans = testAnswers[index];
				ner = cb.NER(phrase, ans);
				System.out.println("\"" + phrase + "\", \"" + ans + "\" --> \"" + ner + "\"");
				if(ner != null && ner.length() > 0) {++counter;}
				++index;
			}catch(Exception e) {
				continue;
			}
		}
		testScore = (int) ((counter/((double) testPhrases.length))*100);
		System.out.println("Test score: " + testScore + "%");
		System.out.println();
		
		counter = 0.0;
		index = 0;
		double ratio;
		String tagged;
		System.out.println("ChatBot handleSpelling()");
		for(int i=0;i<spellTest.length;i=i+2) {
			try {
				tagged = spellTest[i];
				phrase = spellTest[i+1];
				ratio = cb.handleSpelling(tagged, phrase);
				System.out.println(phrase + ", " + tagged + ": " + ratio);
				if(ratio == spellAnswers[index]) {++counter;}
				++index;
			}catch(Exception e) {
				continue;
			}
		}
		testScore = (int) ((counter/((double) (spellTest.length/2)))*100);
		System.out.println("Test score: " + testScore + "%");
	}
}
