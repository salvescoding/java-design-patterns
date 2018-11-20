import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Journal {

    private final List<String> entries = new ArrayList<>();
    private static int count = 0;

    public void addEntry(String text) {
        entries.add("" + (++count) + ": " + text);
    }

    public void removeEntry(int index) {
        entries.remove(index);
    }


    @Override
    public String toString() {
        return String.join(System.lineSeparator(), entries);
    }
}

class Persitence {
    public void saveToFile(Journal journal,
                           String fileName,
                           boolean overwrite) throws FileNotFoundException {
        if (overwrite || new File(fileName).exists()) {
            try(PrintStream out = new PrintStream(fileName)) {
                out.println(journal.toString());
            }
        }
    }
}

class Demo {
    public static void main(String[] args) throws Exception {
        Journal journal = new Journal();
        journal.addEntry("I did lunch today");
        journal.addEntry("I am watching the fire today");
        System.out.println(journal);

        Persitence persitence = new Persitence();
        String fileName = "/Users/sergioalves/code/java-design-patterns/journal.txt";
        persitence.saveToFile(journal, fileName, true);
    }
}
