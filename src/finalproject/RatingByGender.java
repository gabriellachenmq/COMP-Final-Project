package finalproject;

import java.util.ArrayList;

public class RatingByGender extends DataAnalyzer{
	private String type = null;
	private String gender = null;
	private MyHashTable<String, MyHashTable<String, MyHashTable<String, Integer>>> compilation;

	public RatingByGender(Parser p) {
		super(p);
	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		String[] temp = keyword.split(",");
		type = temp[1].strip().toLowerCase();
		gender = temp[0].strip().toUpperCase();
		return compilation.get(type).get(gender);
	}

	@Override
	public void extractInformation() {

		compilation = new MyHashTable<>();
		MyHashTable<String, MyHashTable<String, Integer>> genderDif = new MyHashTable<>();
		genderDif.put("F", getRatingTable());
		genderDif.put("M", getRatingTable());
		genderDif.put("X", getRatingTable());
		MyHashTable<String, MyHashTable<String, Integer>> genderQual = new MyHashTable<>();
		genderQual.put("F", getRatingTable());
		genderQual.put("M", getRatingTable());
		genderQual.put("X", getRatingTable());
		compilation.put("difficulty", genderDif);
		compilation.put("quality", genderQual);

		for (String[] array : parser.data) {
			Double qualScore = Double.valueOf(array[parser.fields.get("student_star")]);
			Double difScore = Double.valueOf(array[parser.fields.get("student_difficult")]);
			String gender = array[parser.fields.get("gender")];

			MyHashTable<String, Integer> ratingTableDif = compilation.get("difficulty").get(gender);
			MyHashTable<String, Integer> ratingTableQual = compilation.get("quality").get(gender);
			String qualCat = getRatingCategory(qualScore);
			String difCat = getRatingCategory(difScore);
			int qualCount = ratingTableQual.get(qualCat);
			qualCount++;
			ratingTableQual.put(qualCat,qualCount);
			int difCount = ratingTableDif.get(difCat);
			difCount++;
			ratingTableDif.put(difCat,difCount);
			genderDif.put(gender,ratingTableDif);
			genderQual.put(gender,ratingTableQual);
			compilation.put("difficulty", genderDif);
			compilation.put("quality", genderQual);
		}




	}

	private MyHashTable<String, Integer> getRatingTable(){
		MyHashTable<String, Integer> ratingTable = new MyHashTable<>();
		ratingTable.put("1", 0);
		ratingTable.put("2", 0);
		ratingTable.put("3", 0);
		ratingTable.put("4", 0);
		ratingTable.put("5", 0);
		return ratingTable;
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
