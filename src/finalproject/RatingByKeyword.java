package finalproject;

import java.util.ArrayList;
import java.util.HashMap;

public class RatingByKeyword extends DataAnalyzer {
	private MyHashTable<String, MyHashTable<String, Integer>> compilation;
	
    public RatingByKeyword(Parser p) {
        super(p);
    }

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		return compilation.get(keyword.toLowerCase().trim());
	}

	@Override
	public void extractInformation() {
		compilation = new MyHashTable<>();
		for (String[] array : parser.data) {
			Double studentScore = Double.valueOf(array[parser.fields.get("student_star")]);
			String comment = array[parser.fields.get("comments")].toLowerCase().trim().replaceAll("[^a-z']", " ");
			String[] words = comment.split("\\s+");
			ArrayList<String> tokens = new ArrayList<>();
			for (String word : words) {
				if(!tokens.contains(word)) {
					tokens.add(word);
				}
			}
			for (String item : tokens) {
				if (compilation.get(item) == null){
					MyHashTable<String, Integer> starTable = new MyHashTable<>();
					starTable.put("1", 0);
					starTable.put("2", 0);
					starTable.put("3", 0);
					starTable.put("4", 0);
					starTable.put("5", 0);
					compilation.put(item, starTable);
				}
				MyHashTable<String, Integer> starTable = compilation.get(item);
				String keyword = getRatingCategory(studentScore);
				int count = starTable.get(keyword);
				count++;
				starTable.put(keyword, count);
				compilation.put(item,starTable);
			}
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
