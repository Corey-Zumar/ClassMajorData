import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BreadthParser {

	public static Map<String, String[]> parseBreadths() {
		Map<String, String[]> breadthsMap = new HashMap<String, String[]>();
		String breadthsText = parseBreadthsFile();
		breadthsText = breadthsText.replaceAll("[^a-zA-Z0-9\\s]", "");
		String[] items = breadthsText.split("\\s");
		StringBuilder builder = null;
		String courseName = null;
		List<String> courseNums = null;
		boolean prevWasNumber = false;
		for (String item : items) {
			if (!item.replaceAll("[a-zA-Z0-9]", "").equals(item)) {
				if (item.replaceAll("[0-9]", "").equals(item)) {
					if(prevWasNumber) {
						if (courseName != null && courseNums != null) {
							String[] newArray = new String[courseNums.size()];
							for(int i = 0; i < courseNums.size(); i++) {
								newArray[i] = courseNums.get(i);
							}
							breadthsMap.put(courseName, newArray);
							courseName = null;
							courseNums = null;
						}
					}
					if (builder == null) {
						builder = new StringBuilder(item);
					} else {
						builder.append(" " + item);
					}
					prevWasNumber = false;
				} else {
					if(courseName == null) {
						courseName = builder.toString();
						builder = null;
					}
					if(courseNums == null) {
						courseNums = new ArrayList<String>();
					}
					courseNums.add(courseName + " " + item);
					prevWasNumber = true;
				}
			} else {

			}
		}
		return breadthsMap;
	}

	private static String parseBreadthsFile() {
		File file = new File("breadth_info.txt");
		StringBuilder builder = new StringBuilder();
		try {
			String line = null;
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while ((line = reader.readLine()) != null) {
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
		Map<String, String[]> map = BreadthParser.parseBreadths();
		for(String str : map.keySet()) {
			System.out.println(str.toUpperCase());
			for(String blah : map.get(str)) {
				System.out.println(blah);
			}
		}
	}

}
