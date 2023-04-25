package finalproject;

import javafx.util.Pair;

public class RatingDistributionByProf extends DataAnalyzer {
	private MyHashTable<String, MyHashTable<String, Integer>> compilation;
	
    public RatingDistributionByProf(Parser p) {
        super(p);
    }

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		return compilation.get(keyword.toLowerCase().trim());
		//ADD YOUR CODE ABOVE THIS
	}

	@Override
	public void extractInformation() {
		compilation = new MyHashTable<>();
		for (String[] array : parser.data) {
			String professorName = array[parser.fields.get("professor_name")].toLowerCase().trim();
			Double studentScore = Double.valueOf(array[parser.fields.get("student_star")]);
			if(compilation.get(professorName) == null){
				MyHashTable<String, Integer> profTable = new MyHashTable<>();
				profTable.put("1", 0);
				profTable.put("2", 0);
				profTable.put("3", 0);
				profTable.put("4", 0);
				profTable.put("5", 0);
				compilation.put(professorName, profTable);
			}
			MyHashTable<String, Integer> ratingTable = compilation.get(professorName);
			String keyWord = getRatingCategory(studentScore);
			int value = (int) ratingTable.get(keyWord);
			value++;
			ratingTable.put(keyWord, value);
			compilation.put(professorName, ratingTable);
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
