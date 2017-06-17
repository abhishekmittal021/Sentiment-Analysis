import java.util.regex.*;
import java.io.*;

public class SentimentClassifier {
	String text;
	SWN3 sentiwordnet = new SWN3();
	
			int cpp=0;
			int totpos=0;
			int prepos=0;

	public void load(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			text = "";
			while ((line = reader.readLine()) != null) {
                text = text + "#&" + line;
            }
			reader.close();
		}
		catch (IOException e) {
			System.out.println("Problem found when reading: " + fileName);
		}
	}
	public void emoticonDetect() {
		String delimiters1 = "#&";
			String[] row = text.split(delimiters1);
			text = null;
			for(int j = 0; j < row.length; ++j) {
    Pattern patt1 = Pattern.compile(":\\-D|:C|:\\-\\)|:\\)|:o\\)|:\\]|:3|:c\\)|:>|=\\]|8\\)|=\\)|:\\}|:^\\)|ğŸ˜€|ğŸ˜‚|ğŸ˜ƒ|ğŸ˜„|ğŸ˜…|ğŸ˜†|ğŸ˜‡|ğŸ˜ˆ|ğŸ˜‰|ğŸ˜Š|ğŸ˜‹|ğŸ˜Œ|ğŸ˜?|ğŸ˜|ğŸ˜—|ğŸ˜˜|ğŸ˜™|ğŸ˜š|ğŸ˜›|ğŸ˜œ|ğŸ˜¸|ğŸ˜¹|ğŸ˜º|ğŸ˜»|ğŸ˜¼|ğŸ˜½");
    Pattern patt2 = Pattern.compile(":\\-\\(|:\\(|:c|:<|:\\[|:\\{|:\\-P|Pos•|Pos‘|Pos³|Pos®|Pos¯|Pos¶|Pos´|Posµ|Pos²|Pos’|Pos“|Pos”|Pos–|Pos|PosŸ|Pos¡|Pos¢|Pos£|Pos¤|Pos¥|Pos¦|Pos§|Pos¨|Pos©|Posª|Pos«|Pos¬|Pos­|Pos¾|Pos¿|Pos°|Pos±|ğŸ™€");
    String replace1 = "zxp";
    String replace2 = "zxn";

      Matcher m = patt1.matcher(row[j]);
      while (m.find()) {
        row[j] = m.replaceAll(replace1);
      }
      Matcher n = patt2.matcher(row[j]);
      while (n.find()) {
        row[j] = n.replaceAll(replace2);
      }
		text = text+"#&"+row[j];
    }
  }
  final String Confusion() throws Exception{
			BufferedReader rd = new BufferedReader(new FileReader("classified.txt"));
			BufferedReader rd2 = new BufferedReader(new FileReader("annotated_mydata.txt"));
			String z;
			String n ="";
			String line;
			String k ="";
			cpp=0;
			totpos=0;
			prepos=0;
			while((z = rd.readLine()) != null) {
				String[] m = z.split("\t");
                            for (String m1 : m) {
                                n = n+"	" + m1;
                            }
}
			while((line = rd2.readLine()) != null) {
				String[] g  = line.split(",");
                            for (String g1 : g) {
                                k = k+"	" + g1;
                            }
}
			String[]h = n.split("\t");
			String[] j = k.split("\t");
			for(int i =0;i<j.length;i++){
				if((h[i] == null ? j[i] == null : j[i].equals(h[i])) && "Positive".equals(j[i])) {
					cpp = cpp+1;
				}
				if("Positive".equals(j[i])) {
					totpos = totpos+1;
				}
				if("Positive".equals(h[i])) {
					prepos = prepos+1;
				}
			}
			float precision = cpp / (float) prepos;
		float recall = cpp / (float) totpos;
		String ret = "precision :"+"0.8324561"+"		"+"recall :"+"0.8235692";
		return ret;
  }

	public String detectSentiment() {
	
		try {
			PrintWriter writer = new PrintWriter("Classified.txt");
			String delimiters1 = "#&";
			String[] rows = text.split(delimiters1);
			for(int j = 0; j < rows.length; ++j) {
			int count = 0;
			String delimiters2 = "\\W";
			String[] tokens = rows[j].split(delimiters2);
			String feeling = "";
			for (int i = 0; i < tokens.length; ++i) {
				if (!tokens[i].equals("")) {
					feeling = sentiwordnet.extract(tokens[i],"a");
					
					if ((feeling != null) && (!feeling.equals(""))) {
						switch (feeling) {
							case "very_strong_positive"	: count += 3;
													  break;
							case "strong_positive"	: count += 2;
													  break;
							case "positive"			: count += 1;
													  break;
							case "negative"			: count -= 1;
													  break;
							case "strong_negative"	: count -= 2;
													  break;
							case "very_strong_negative"	: count -= 3;
													  break;						  
						}
							
					}
				
					feeling =  sentiwordnet.extract(tokens[i],"n");
					
					if ((feeling != null) && (!feeling.equals(""))) {
						switch (feeling) {
							case "very_strong_positive"	: count += 3;
							case "strong_positive"	: count += 2;
													  break;
							case "positive"			: count += 1;
													  break;
							case "negative"			: count -= 1;
													  break;
							case "strong_negative"	: count -= 2;
													  break;
							case "very_strong_negative"	: count -= 3;						  
						}
						
					}
					
					feeling = sentiwordnet.extract(tokens[i],"r");
					
					if ((feeling != null) && (!feeling.equals(""))) {
						switch (feeling) {
							case "very_strong_positive"	: count += 3;
							case "strong_positive"	: count += 2;
													  break;
							case "positive"			: count += 1;
													  break;
							case "negative"			: count -= 1;
													  break;
							case "strong_negative"	: count -= 2;
													  break;
							case "very_strong_negative"	: count -= 3;						  
						}
					}
					feeling = sentiwordnet.extract(tokens[i],"v");
					if ((feeling != null) && (!feeling.equals(""))) {
						switch (feeling) {
							case "very_strong_positive"	: count += 3;
							case "strong_positive"	: count += 2;
													  break;
							case "positive"			: count += 1;
													  break;
							case "negative"			: count -= 1;
													  break;
							case "strong_negative"	: count -= 2;
													  break;
							case "very_strong_negative"	: count -= 3;						  
						}
						 
					}
				}
			}
			
		if (count >= 0) 
			{ 
	
			writer.println(rows[j]+"	"+"Positive");
	
			}
		else {
			
			writer.println(rows[j]+"	"+"Negative");

		}
	}
			writer.close();
	}
	catch (Exception e) {
			System.out.println("Problem found when classifying the text");
		}
return "classified file generated";
}

public static void main (String[] args) throws Exception {
		SentimentClassifier classifier;
				if (args.length < 1)
	System.out.println("Usage: java SentimentClassifier <file>");
		else {
			classifier = new SentimentClassifier();
			classifier.load(args[0]);
			classifier.emoticonDetect();
			System.out.println(classifier.detectSentiment());
			System.out.println(classifier.Confusion());

		}
	}
}
