import java.util.Map;

import majors.MajorsParser;

public class Classes {
	
	public String myMajor;
	public Majors otherMajors;
	public String[] unrelatedMajors;
	
	public Classes(String major) {
		myMajor = major;
		otherMajors = new Majors();
		unrelatedMajors = new String[5];
	}
	
	public void addDepths() {
		MajorsParser parser = new MajorsParser();
		Map<String, String[]> map = parser.parseMajors();
		otherMajors.addRelated(map);
		unrelatedMajors = otherMajors.relatedMajors.farthest(myMajor);
	}
	
	public Course[][] coursesForMajor(String[] majors, Map<String, Course> map) {
		Course[][] courses = new Course[majors.length][5];
		for (int i =0; i < majors.length; i++) {
			for (int j = 0; j < 5; j++) {
				courses[i][j] = map.get(majors[i])[j];
			}
		}
	}
	
	public static void main(String[] args) {
		Map<String, Course> map = parser.parseCourses();
		Classes myClasses = new Classes();
		myClasses.addDepths();
		Course[][] unrelatedCourses = coursesForMajor(myClasses.unrelatedMajors, map);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.println(unrelatedCourses[i][j]);
			}
		}
	}
	
}
