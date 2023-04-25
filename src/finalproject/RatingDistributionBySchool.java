package finalproject;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class RatingDistributionBySchool extends DataAnalyzer {

	private MyHashTable<String, MyHashTable<String, Integer>> compilation;
	private MyHashTable<String, MyHashTable<String, Double>> scoresComp;
	private MyHashTable<String, MyHashTable<String, Double>> finalTable;

	public RatingDistributionBySchool(Parser p) {
		super(p);
	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {

		return compilation.get(keyword.toLowerCase().trim());

	}

	@Override
	public void extractInformation() {
		compilation = new MyHashTable<>();
		scoresComp = new MyHashTable<>();
		finalTable = new MyHashTable<>();

		for (String[] array : parser.data) {
			String schoolName = array[parser.fields.get("school_name")].toLowerCase().trim();
			String professorName = array[parser.fields.get("professor_name")].toLowerCase().trim();
			Double studentScore = Double.valueOf(array[parser.fields.get("student_star")]);
			//String keyword = professorName+"\n"+0.0;
			int count = 0;
			Double totalScore = 0.0;
			if (compilation.get(schoolName) == null) {
				MyHashTable<String, Integer> profTable = new MyHashTable<>();
				compilation.put(schoolName, profTable);
			}
			if(compilation.get(schoolName).get(professorName) == null){
				MyHashTable<String, Integer> profTable = compilation.get(schoolName);
				//keyword = professorName+"\n"+0.0;
				count = 0;
				profTable.put(professorName, count);
			}
			MyHashTable<String, Integer> profTable = compilation.get(schoolName);
			count = (int) profTable.get(professorName);
			count++;
			//totalScore+=studentScore;
			//Double avg = totalScore/(double) count;
			profTable.put(professorName,count);
			compilation.put(schoolName,profTable);
		}
		for (String[] array : parser.data) {
			String schoolName = array[parser.fields.get("school_name")].toLowerCase().trim();
			String professorName = array[parser.fields.get("professor_name")].toLowerCase().trim();
			Double studentScore = Double.valueOf(array[parser.fields.get("student_star")]);
			int count = 0;
			Double totalScore = 0.0;
			//String keyword = professorName+"\n"+0.0;
			if (scoresComp.get(schoolName) == null) {
				MyHashTable<String, Double> profTable = new MyHashTable<>();
				scoresComp.put(schoolName, profTable);
			}
			if(scoresComp.get(schoolName).get(professorName) == null){
				MyHashTable<String, Double> profTable = scoresComp.get(schoolName);
				totalScore = 0.0;
				profTable.put(professorName, totalScore);
			}
			MyHashTable<String, Double> profTable = scoresComp.get(schoolName);
			totalScore = (double) profTable.get(professorName);
			totalScore+=studentScore;
			profTable.put(professorName,totalScore);
			scoresComp.put(schoolName,profTable);
		}

		for(String key:compilation.getKeySet()){
			MyHashTable<String, Integer> profs = compilation.get(key);
			MyHashTable<String, Double> scores = scoresComp.get(key);
			for(String name: profs.getKeySet()){
				String oldKey = name;
				int numCount = profs.get(name);
				Double totalscores = scores.get(name);
				Double avg = totalscores/(double)numCount;
				String newKey = oldKey+"\n"+ avg;
				profs.remove(oldKey);
				profs.put(newKey,numCount);

			}
			compilation.put(key, profs);
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
