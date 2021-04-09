package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/*
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.simple.Document;
import java.util.Properties;

import edu.stanford.nlp.pipeline.*;



import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.ling.CoreAnnotations.MentionsAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import java.util.*;
*/

import com.google.cloud.translate.*;
import com.google.cloud.translate.Translate.TranslateOption;

public class ChatBot {
    String name;
    String phrases = "";
    static int counter = 0;
    
    public ChatBot(String name){
        this.name = name;
    };
    public String sendPhrase(String phrase){

        Translate translate = TranslateOptions.getDefaultInstance().getService();
        Translation translation = translate.translate(phrase);
        String translated = translation.getTranslatedText();
        System.out.println("Question: " + translated);
        String sourcelang = translation.getSourceLanguage();

        translated=dataClean(translated);
        String ans = "";
        String[] stringArray = translated.split(" ");
        
        /*
        phrase = coreference(phrase);
        // Send phrase to the POS tagger; Returns an ArrayList of possible keywords
        ArrayList<String> list = pos(phrase);
        String[] taggedData = new String[list.size()];
        taggedData = list.toArray(taggedData);
        */
        String[] taggedData = stringArray;

        // Loop to find first keyword
        for(int i = 0; i< taggedData.length; i++){
            // If the first keyword is found call search() to find second keyword
            if(taggedData[i].equals("experience")){
                ans=search("experience", taggedData, translate, sourcelang);
                break;
            };
            if(taggedData[i].equals("travel")){
                ans=search("travel", taggedData, translate, sourcelang);
                break;
            };
            if(taggedData[i].equals("goal")){
                ans=search("goal", taggedData, translate, sourcelang);
                break;
            };
            if(taggedData[i].equals("hobby")){
                ans=search("hobby", taggedData, translate, sourcelang);
                break;
            };
            if(taggedData[i].equals("school")){
                ans=search("school", taggedData, translate, sourcelang);
                break;
            };
            if(taggedData[i].equals("volunteer")){
                ans=search("volunteer", taggedData, translate, sourcelang);
                break;
            };
            if(taggedData[i].equals("salary")){
                ans=search("salary", taggedData, translate, sourcelang);
                break;
            };
            if(taggedData[i].equals("skills")){
                ans=search("skills", taggedData, translate, sourcelang);
                break;
            };
            if(taggedData[i].equals("training")){
                ans=search("training", taggedData, translate, sourcelang);
                break;
            };
            if(taggedData[i].equals("certifications")){
                ans=search("certifications", taggedData, translate, sourcelang);
                break;
            };
            // if no match found then check if a spelling error was made
            // only take error if high confidence we think it means
            // what we think it means
            
            /*
            for(int j = 0; j < fields.length; j++) {
            	
            	double ratio = handleSpelling(taggedData[i], fields[j]);
            	System.out.println(ratio);
            	if(ratio > 0.80) {
            		ans = search(fields[j], taggedData);
            	}
            	
            	break;
            	
            }
            */
        };
        

        

      

      //If none of the keywords were found look in the miscellaneous csv for generic questions
        if(ans.length()==0){
        	ans=search("miscellaneous", stringArray, translate, sourcelang);
        }
      //If the ansswer is still empty no keywords were found
        return ans.length()!=0?ans:"Can you please rephrase the question?";    
    };
  

  	public String search(String keyword, String[] stringArray, Translate translate, String sourcelang){
      String csvPath="C:\\Users\\Sara\\git\\310_Project\\csvs\\" + keyword + ".csv";
      ArrayList<String> data = new ArrayList<String>();
      String row = "";
      boolean breakOut = false;
      String ans = "";
      try(BufferedReader csvReader = new BufferedReader(new FileReader(csvPath))){
          while ((row = csvReader.readLine()) != null) {
              String[] rowData = row.split(",");
               data.add(rowData[0]);
               data.add(rowData[1]);
          };
      }catch(FileNotFoundException e){
          System.out.println(e);                        
      }catch(IOException e){
          System.out.println(e);
      }

      for(int j = 0; j<stringArray.length; j++){
          for(int k=0; k<data.size(); k+=2){
              if(stringArray[j].equals(data.get(k))){
                  ans = data.get(k+1).toString();
                  breakOut=true;
              }
          }
          if(breakOut==true)
              break;
          if(j==stringArray.length-1)
              ans = "Could you be a little more specific please?";
      }
      
      System.out.println("Answer: " + ans);
      TranslateOption base = Translate.TranslateOption.sourceLanguage("en");
      TranslateOption src = Translate.TranslateOption.targetLanguage(sourcelang);
      if (!(sourcelang.equals("en"))) {
          Translation translationback = translate.translate(ans, base, src); // (text, language of text, language we want to translate to)
          //System.out.printf("Translated Text:\n\t%s\n", translation.getTranslatedText());
          ans = translationback.getTranslatedText();
      }
      
      return ans;
  }

  	public String dataClean(String phrase){
      String cleanedPhrase=phrase.toLowerCase();
      cleanedPhrase=cleanedPhrase.replace("?","").replace(".","").replace(",","").replace("!","");
      return cleanedPhrase;
  	};

    
  	/*

    public ArrayList<String> pos(String text){

        ArrayList<String> possibleKeywords = new ArrayList<>();
        Properties property = new Properties();
        property.setProperty("annotators", "tokenize,ssplit,pos");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(property);
        CoreDocument document = pipeline.processToCoreDocument(text);
        for (CoreLabel token : document.tokens()) {
            //System.out.println(token.word() + "   " + token.tag());
            if (token.tag().equals("NN") || token.tag().equals("NNS") || token.tag().equals("JJ") || token.tag().equals("JJS") || token.tag().equals("CD") || token.tag().equals("VB") || token.tag().equals("UH") || token.tag().equals("WRB") || token.tag().equals("RB") || token.tag().equals("DT")){
                possibleKeywords.add(token.word());
            }
        }
        return possibleKeywords;
    }

   // coreference function
    public String coreference(String phrase) {
    	if(counter>=3) {
    		phrases = phrases.substring(phrases.indexOf(".",2));
    	};
    	counter++;
    	phrases = phrases + ". " + phrase;
 	   	Document doc = new Document(phrases);
 	   	Map<Integer, CorefChain> ar = doc.coref();
        for (Entry<Integer, CorefChain> me : ar.entrySet()) {
        	
            Pattern regex = Pattern.compile("\"([^\"]*)\"");
            ArrayList<String> allMatches = new ArrayList<String>();
            Matcher matcher = regex.matcher(me.getValue().toString());
            while(matcher.find()){
            	allMatches.add(matcher.group(1));
            };
            for (int i = 0; i < allMatches.size(); i++) {
				if(allMatches.get(i).toString().equals("it")) {
					phrase = phrase.replace("it", allMatches.get(0).toString());
				}
			}
        }
    	return phrase;
    }

    public String NER(String phrase, String ans) {

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
        
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation annotationPhrase = new Annotation(phrase);
        Annotation annotationAns = new Annotation(ans);
        pipeline.annotate(annotationPhrase);
        pipeline.annotate(annotationAns);
        List<CoreMap> multiWordsExpPhrase = annotationPhrase.get(MentionsAnnotation.class);
        List<CoreMap> multiWordsExpAns = annotationAns.get(MentionsAnnotation.class);
        for(CoreMap multiWordPhrase: multiWordsExpPhrase) {
        	String custNERClassPhrase = multiWordPhrase.get(NamedEntityTagAnnotation.class);
        	System.out.println(multiWordPhrase +" : " +custNERClassPhrase);
        	for(CoreMap multiWordAns: multiWordsExpAns) {
        		String custNERClassAns = multiWordAns.get(NamedEntityTagAnnotation.class);
        		System.out.println(multiWordAns +" : " +custNERClassAns);
        		if(custNERClassAns == custNERClassPhrase) {
        			ans = ans.replaceAll(multiWordAns.toString(), multiWordPhrase.toString());
        			
        		}
        	}
        }
      return ans;
    }
    
    public static double handleSpelling(String taggedWord, String target) {
		
		String big, small;
		double bigCount;
		
		if(taggedWord.length() < target.length()) {
			big = target;
			small = taggedWord;
		}
		
		else {
			big = taggedWord;
			small = target;
		}
		
		bigCount = big.length();
		
		if(bigCount == 0) {
			return 1.0;
		}
		
		return (bigCount - dist(big, small)) / bigCount;
		
	}
	
	public static int dist(String str1, String str2) {
		
		int[] cost = new int[str2.length() + 1];
		
		for(int i = 0; i <= str1.length(); i ++) {
			
			int temp0 = i;
			
			for(int j = 0; j <= str2.length(); j ++) {
				
				if(i == 0) {
					cost[j] = j;	
				}
				
				else {
					
					if(j > 0) {
						
						int temp1 = cost[j - 1];
						
						if(str1.charAt(i - 1) != str2.charAt(j - 1)) {
							temp1 = Math.min(Math.min(temp1, temp0), cost[j]) + 1;
						}
						
						cost[j - 1] = temp0;
						temp0 = temp1;
							
					}
					
				}
				
			}
			
			if(i > 0) {
				cost[str2.length()] = temp0;
			}
			
		}
		
		return cost[str2.length()];
		
	}
	
	private String[] fields = {
			
			"experience", "travel", "goal", "hobby", "school", "volunteer",
			"salary", "skills", "training", "certifications"
			
	};
	*/
}