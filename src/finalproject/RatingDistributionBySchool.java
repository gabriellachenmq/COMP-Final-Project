package finalproject;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class RatingDistributionBySchool extends DataAnalyzer {

	private MyHashTable<String, MyHashTable<String, Integer>> compilation;
	private MyHashTable<String, MyHashTable<String, ArrayList<Double>>> allTable;

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
		allTable = new MyHashTable<>();

		for (String[] array : parser.data) {
			String schoolName = array[parser.fields.get("school_name")].toLowerCase().trim();
			String professorName = array[parser.fields.get("professor_name")].toLowerCase().trim();
			Double studentScore = Double.valueOf(array[parser.fields.get("student_star")]);
			MyHashTable<String, ArrayList<Double>> profs = new MyHashTable<>();
			ArrayList<Double> scores = new ArrayList<>();

			profs = allTable.get(schoolName);
			if(profs == null){
				allTable.put(schoolName, new MyHashTable<>());
				profs = allTable.get(schoolName);
			}

			scores = profs.get(professorName);
			if(scores == null){
				profs.put(professorName,new ArrayList<>());
				scores = profs.get(professorName);
			}
			scores.add(studentScore);
		}

		for(MyPair<String, MyHashTable<String, ArrayList<Double>>> schools : allTable){
			MyHashTable<String, ArrayList<Double>> prof = schools.getValue();
			MyHashTable<String, Integer> profTable = new MyHashTable<>();
			for(String name: prof.getKeySet()){
				ArrayList<Double> scoresA = prof.get(name);
				Double totalScores = 0.0;
				for(Double score : scoresA){
					totalScores += score;
				}
				int count = scoresA.size();
				Double avg = roundToTwoDecimalPlaces(totalScores/(double)count);
				String key = name+"\n"+ avg;
				profTable.put(key, count);
			}
			compilation.put(schools.getKey(), profTable);
		}

	}

	private  double roundToTwoDecimalPlaces(double value) {
		return Math.round(value * 100.0) / 100.0;
	}

}
