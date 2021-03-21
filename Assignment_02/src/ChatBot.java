//package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.stanford.nlp.simple.Document;

public class ChatBot {
    String name;
    String phrases = "";
    
    public ChatBot(String name){
        this.name = name;
    };
    public String sendPhrase(String phrase){
        phrase=dataClean(phrase);
        String ans = "";
        coreference(phrase);
        String[] stringArray = phrase.split(" ");
        
        // Loop to find first keyword
        for(int i = 0; i< stringArray.length; i++){
            // If the first keyword is found call search() to find second keyword
            if(stringArray[i].equals("experience")){
                ans=search("experience", stringArray);
                break;
            };
            if(stringArray[i].equals("travel")){
                ans=search("travel", stringArray);
                break;
            };
            if(stringArray[i].equals("goal")){
                ans=search("goal", stringArray);
                break;
            };
            if(stringArray[i].equals("hobby")){
                ans=search("hobby", stringArray);
                break;
            };
            if(stringArray[i].equals("school")){
                ans=search("school", stringArray);
                break;
            };
            if(stringArray[i].equals("volunteer")){
                ans=search("volunteer", stringArray);
                break;
            };
            if(stringArray[i].equals("salary")){
                ans=search("salary", stringArray);
                break;
            };
            if(stringArray[i].equals("skills")){
                ans=search("skills", stringArray);
                break;
            };
            if(stringArray[i].equals("training")){
                ans=search("training", stringArray);
                break;
            };
            if(stringArray[i].equals("certifications")){
                ans=search("certifications", stringArray);
                break;
            };
 
        };

        //If none of the keywords were found look in the miscellaneous csv for generic questions
        if(ans.length()==0){
            ans=search("miscellaneous", stringArray);
        }
        //If the ansswer is still empty no keywords were found
        return ans.length()!=0?ans:"Can you please rephrase the question?";    
    };

    public String search(String keyword, String[] stringArray){
        //String csvPath="C:\\Users\\Brandon\\Desktop\\csvs\\" + keyword + ".csv";
        String csvPath="C:\\Users\\joels\\Documents\\School\\3rdYear2ndSem\\COSC310\\Assignment_03\\Assignment_02\\csvs\\" + keyword + ".csv";
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
        return ans;
    }

    public String dataClean(String phrase){
        String cleanedPhrase=phrase.toLowerCase();
        cleanedPhrase=cleanedPhrase.replace("?","").replace(".","").replace(",","").replace("!","");
        return cleanedPhrase;
    };
    
    // coreference function
    private String coreference(String phrase) {
    	phrases = phrases + ". " + phrase;
    	System.out.println(phrases);
 	   	Document doc = new Document(phrases);
 	   	System.out.println(doc.coref());
    	return phrase;
    }
}
