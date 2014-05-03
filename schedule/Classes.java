import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import majors.MajorsParser;

public class Classes {
	
	public String myMajor;
	public Majors otherMajors;
	public Object[] unrelatedMajors;
	public String[] majorsWithCourses;
	
	public Classes(String major) {
		myMajor = major;
		otherMajors = new Majors();
	}
	
	public void addDepths(int numMajors, double depth) {
		MajorsParser parser = new MajorsParser();
		Map<String, String[]> map = parser.parseMajors();
		otherMajors.addRelated(map);
		unrelatedMajors = otherMajors.relatedMajors.farVertices(myMajor, numMajors,depth);
	}
	
	public Course[][] coursesForMajor(Map<String, Course[]> map) {
		Course[][] courses = new Course[unrelatedMajors.length][700];
		majorsWithCourses = new String[unrelatedMajors.length];
		String title = "";
		for (int i =0; i < unrelatedMajors.length; i++) {
			if (map.get(unrelatedMajors[i]).length > 0) {
				majorsWithCourses[i] = (String) unrelatedMajors[i];
			} else {
				majorsWithCourses[i] = null;
			}
			for (int j = 0; j < map.get(unrelatedMajors[i]).length; j++) {
				if (!title.equals(map.get(unrelatedMajors[i])[j].toString())) {
					courses[i][j] = map.get(unrelatedMajors[i])[j];
				}
				title = map.get(unrelatedMajors[i])[j].toString();
			}
		}
		System.out.println("");
		return courses;
	}
	
	public String[][] breadthsForMajor(Map<String, String[]> map) {
		String[][] courses = new String[unrelatedMajors.length][700];
		majorsWithCourses = new String[unrelatedMajors.length];
		for (int i =0; i < unrelatedMajors.length; i++) {
			//System.out.println(unrelatedMajors[i]);
			if (map.get(unrelatedMajors[i]) != null &&
					map.get((String)unrelatedMajors[i]).length > 0) {
				majorsWithCourses[i] = (String) unrelatedMajors[i];
			} else {
				majorsWithCourses[i] = null;
			}
			if (map.get(unrelatedMajors[i]) != null) {
				for (int j = 0; j < map.get(unrelatedMajors[i]).length; j++) {
					courses[i][j] = map.get(unrelatedMajors[i])[j];
				}
			}
		}
		System.out.println("");
		return courses;
	}	
	
	public static void unrelatedCourses(String major, int numMajors, double depth) {
		System.out.println("General courses:");
		Map<String, Course[]> map = CoursesParser.parseCourses();
		Classes myClasses = new Classes(major);
		myClasses.addDepths(numMajors, depth);
		Course[][] unrelatedCourses = myClasses.coursesForMajor(map);
		for (int i = 0; i < numMajors; i++) {
			System.out.println(myClasses.unrelatedMajors[i] + ":");
			for (int j = 0; j < unrelatedCourses[i].length; j++) {
				if (unrelatedCourses[i][j] != null) {
					System.out.println(unrelatedCourses[i][j] + ": " + unrelatedCourses[i][j].getSection());
				}
			}
			if (unrelatedCourses[i][0] == null) {
				System.out.println("No Classes");
			}
			System.out.print("\n");
		}
	}
	
	public static void breadthCourses(String major, int numMajors, double depth) {
		System.out.println("Breadth courses:");
		Map<String, String[]> map = BreadthParser.parseBreadths();
		Classes myClasses = new Classes(major);
		myClasses.addDepths(numMajors, depth);
		String[][] unrelatedBreadths = myClasses.breadthsForMajor(map);
		for (int i = 0; i < numMajors; i++) {
			System.out.println(myClasses.unrelatedMajors[i] + ":");
			for (int j = 0; j < unrelatedBreadths[i].length; j++) {
				if (unrelatedBreadths[i][j] != null) {
					System.out.println(unrelatedBreadths[i][j]);
				}
			}
			if (unrelatedBreadths[i][0] == null) {
				System.out.println("No Classes");
			}
			System.out.print("\n");
		}
	}
	
	public static void main(String[] args) {
		String[] inputs = new String[4];
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please input the name of your major");
		try {
			String inputMajor = keyboard.readLine();
			inputs[0] = inputMajor;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("How many majors would you like to consider for classes?");
		try {
			String inputNumMajors = keyboard.readLine();
			inputs[1] = inputNumMajors;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("On a scale of 0 - 1, how relevant to your " +
				"major should your new courses be?");
		try {
			String relatability = keyboard.readLine();
			inputs[2] = relatability;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Consider breadths?");
		try {
			String breadths = keyboard.readLine();
			inputs[3] = breadths;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		run(inputs);
		
		/*if (args.length < 3 || args.length > 4) {
			System.out.println("Invalid number arguments");
			return;
		}
		if (args.length == 4 && !args[3].equals("Breadths")) {
			System.out.println("4th argument must be \"Breadths\"");
			return;
		}
		String major =  args[0];
		if ((new MajorsParser()).parseMajors().get(major) == null) {
			System.out.println("That major doesn't exist");
			return;
		}
		int numMajors;
		double unrelatedness;
		try {
			numMajors = Integer.parseInt(args[1]);
			unrelatedness = Double.parseDouble(args[2]);
			if (numMajors > 60 || numMajors < 1) {
				System.out.println("Invalid number of majors");
				return;
			}
			if (unrelatedness < 0 || unrelatedness > 1) {
				System.out.println("Unrelatedness of majors should be between 0 and 1");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("The 2nd argument must be an integer and the 3rd argument must be a double");
			return;
		}
		String option = "";
		if (args.length == 4) {
			option = args[3];
		}
		if (option.equals("Breadths")) {
			breadthCourses(major, numMajors, unrelatedness);
		} else {
			unrelatedCourses(major, numMajors, unrelatedness);
		}
		*/
	}
	
	private static void run(String[] args) {
		if (args.length < 3 || args.length > 4) {
			System.out.println("Invalid number arguments");
			//return;
		}
		
		String major =  args[0];
		if ((new MajorsParser()).parseMajors().get(major) == null) {
			System.out.println("That major doesn't exist");
			//return;
		}
		int numMajors;
		double unrelatedness;
		try {
			numMajors = Integer.parseInt(args[1]);
			unrelatedness = Double.parseDouble(args[2]);
			if (numMajors > 60 || numMajors < 1) {
				System.out.println("Invalid number of majors");
				//return;
			}
			if (unrelatedness < 0 || unrelatedness > 1) {
				System.out.println("Unrelatedness of majors should be between 0 and 1");
				//return;
			}
		} catch (NumberFormatException e) {
			System.out.println("The 2nd argument must be an integer and the 3rd argument must be a double");
			return;
		}
		String option = "";
		if (args.length == 4) {
			option = args[3];
		}
		if (option.equals("Breadths")) {
			breadthCourses(major, numMajors, unrelatedness);
		} else {
			unrelatedCourses(major, numMajors, unrelatedness);
		}
		System.out.println("------------------------------------");
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		try {
			if(keyboard.readLine().length() >= 0) {
				main(new String[0]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
