package finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class GenderByKeyword extends DataAnalyzer {
	private String keyword = null;
	private MyHashTable<String, MyHashTable<String, Integer>> wordTable;
	
	public GenderByKeyword(Parser p) {
		super(p);
	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		return wordTable.get(keyword.toLowerCase().trim());
	}

	@Override
	public void extractInformation() {


		wordTable = new MyHashTable<>();

		for (String[] array : parser.data) {
			String comment = array[parser.fields.get("comments")].toLowerCase().trim().replaceAll("[^a-z']", " ");
			String gender = array[parser.fields.get("gender")];
			String[] words = comment.split("\\s+");
			ArrayList<String> tokens = new ArrayList<>();
			for (String word : words) {
				tokens.add(word);
			}
			for (String item : tokens) {
				if (wordTable.get(item) == null){
					MyHashTable<String, Integer> genderTable = new MyHashTable<>();
					genderTable.put("F",0);
					genderTable.put("M",0);
					genderTable.put("X",0);
					wordTable.put(item, genderTable);
				}
				MyHashTable<String, Integer> genderTable = wordTable.get(item);
				int count = genderTable.get(gender);
				count++;
				genderTable.put(gender, count);
				wordTable.put(item,genderTable);
			}
		}
	}
}
