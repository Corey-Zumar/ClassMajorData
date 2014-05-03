package majors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CoursesParser {
	
	public static Map<String, Course[]> parseCourses() {
		String coursesText = parseCoursesFile();
		coursesText = coursesText.replaceAll("(\\\\u00a0)", "");
		Map<String, Course[]> coursesMap = new HashMap<String, Course[]>();
		try {
			JSONObject obj = new JSONObject(coursesText);
			Iterator<?> iterator = obj.keys();
			while(iterator.hasNext()) {
				String major = iterator.next().toString();
				JSONArray array = obj.getJSONArray(major);
				Course[] majorCourses = new Course[array.length()];
				for(int i = 0; i < array.length(); i++) {
					JSONObject innerObj = array.getJSONObject(i);
					Iterator<?> innerIterator = innerObj.keys();
					while(innerIterator.hasNext()) {
						String courseSection = innerIterator.next().toString();
						String courseTitle = innerObj.getString(courseSection);
						majorCourses[i] = new Course(courseTitle, courseSection);
					}
				}
				coursesMap.put(major, majorCourses);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return coursesMap;
	}
	
	private static String parseCoursesFile() {
		File file = new File("courses_info.txt");
		StringBuilder builder = new StringBuilder();
		try {
			String line = null;
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while((line = reader.readLine()) != null) {
				builder.append(line + "\n");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	public static void main(String[] args) {
		Map<String, Course[]> map = CoursesParser.parseCourses();
		for(String major : map.keySet()) {
			System.out.println(major.toUpperCase());
			for(Course course : map.get(major)) {
				System.out.println(course.getTitle() + " " + course.getSection());
			}
		}
	}

}
