import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;


public class Generator {

	public static void main(String[] args) throws Exception {
		//javacc.main(new String[]{});
		javacc.main(new String[]{"-OUTPUT_DIRECTORY=src/main/java/net/sf/mt2/chatparser/generated/","src/main/generator/syntax.jj"});
	}

}
