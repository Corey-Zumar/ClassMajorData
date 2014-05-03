package majors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class MajorsParser {
	
	public MajorsParser() {
		
	}
	
	public Map<String, String> parseMajors() {
		String majorsText = parseMajorsFile();
		majorsText = majorsText.replaceAll("(\\\\r)|(\\\\t)", "");
		Map<String, String> majorsMap = new HashMap<String, String>();
		try {
			JSONObject obj = new JSONObject(majorsText);
			Iterator<?> objIterator = obj.keys();
			while(objIterator.hasNext()) {
				String key = (String) objIterator.next();
				String major = (String) obj.get(key);
				majorsMap.put(key, major);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return majorsMap;
	}
	
	private String parseMajorsFile() {
		File file = new File("major_info.txt");
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
		MajorsParser parser = new MajorsParser();
		Map<String, String> majorsMap = parser.parseMajors();
	}

}
