package fr.dawan.back.tools;

import java.util.HashMap;
import java.util.Map;

public class TokenSaver {
	
	public static Map<String, String> tokensByEmail;
	
	static {
		tokensByEmail = new HashMap<String, String>();
	}

}
