import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class HashSetTest {
	public static void main(String[] args) {
		ArrayList<String> vars = new ArrayList<String>();
		vars.add("1");
		vars.add("1");
		vars.add("2");
		Set<String> varsSet = new HashSet<String>(vars);
		System.out.println(varsSet);
		System.out.println(new ArrayList<String>(varsSet));

	}
}
