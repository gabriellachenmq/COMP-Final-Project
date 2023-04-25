package finalproject;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class RatingDistributionBySchool extends DataAnalyzer {

	private MyHashTable<String, MyHashTable<String, Integer>> compilation;

	public RatingDistributionBySchool(Parser p) {
		super(p);
	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		// ADD YOUR CODE BELOW THIS
		return compilation.get(keyword.toLowerCase().trim());
		//ADD YOUR CODE ABOVE THIS
	}

	@Override
	public void extractInformation() {
		compilation = new MyHashTable<>();
		String keyword = "A";
		int count = 0;
		Double totalScore = 0.0;
		for (String[] array : parser.data) {
			String schoolName = array[parser.fields.get("school_name")].toLowerCase().trim();
			String professorName = array[parser.fields.get("professor_name")].toLowerCase().trim();
			Double studentScore = Double.valueOf(array[parser.fields.get("student_star")]);
			//String keyword = professorName+"\n"+0.0;
			if (compilation.get(schoolName) == null) {
				MyHashTable<String, Integer> profTable = new MyHashTable<>();
				compilation.put(schoolName, profTable);
			}
			if(compilation.get(schoolName).get(keyword) == null){
				MyHashTable<String, Integer> profTable = compilation.get(schoolName);
				//keyword = professorName+"\n"+0.0;
				profTable.put(keyword, count);
			}
			MyHashTable<String, Integer> profTable = compilation.get(schoolName);
			count = (int) profTable.get(keyword);
			count++;
			profTable.remove(keyword);
			totalScore+=studentScore;
			Double avg = totalScore/(double) count;
			keyword = professorName+"\n"+avg;
			profTable.put(keyword,count);
			compilation.put(schoolName,profTable);

		}

	}

	private String getRatingCategory(Double rating) {
		if (rating >= 1 && rating < 2) {
			return "1";
		} else if (rating >= 2 && rating < 3) {
			return "2";
		} else if (rating >= 3 && rating < 4) {
			return "3";
		} else if (rating >= 4 && rating < 5) {
			return "4";
		} else {
			return "5";
		}
	}
}
