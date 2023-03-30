import org.junit.Assert;
import org.junit.Test;
import ru.kotikov.services.CsvReader;
import ru.kotikov.services.CsvReaderImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class CsvReaderImplTest {

    private static final CsvReader CSV_READER = new CsvReaderImpl();

    @Test
    public void testGetQuestions() {
        Map<String, Map<List<String>, String>> questions = CSV_READER.getQuestions("questions.csv");

        Assert.assertTrue(questions.containsKey("What is the latest version of Java?"));
        Assert.assertTrue(questions.containsKey("What is the maximum complexity of a boolean expression allowed?"));
        Assert.assertTrue(questions.containsKey("What is the style of writing a class name in Java?"));
        Assert.assertTrue(questions.containsKey("Select an example of a valid constant in Java."));
        Assert.assertTrue(questions.containsKey("If the value of the field does not change during the execution " +
                "of the program, then it should be?"));

        Assert.assertTrue(questions.get("What is the latest version of Java?")
                .containsKey(Arrays.asList("1) 17", "2) 23", "3) 99", "4) 20")));
        Assert.assertTrue(questions.get("What is the maximum complexity of a boolean expression allowed?")
                .containsKey(Arrays.asList("1) 2", "2) 4", "3) 64", "4) 8")));
        Assert.assertTrue(questions.get("What is the style of writing a class name in Java?")
                .containsKey(Arrays.asList("1) CamelCase", "2) lowerCamelCase", "3) snake_case", "4) dog@case")));
        Assert.assertTrue(questions.get("Select an example of a valid constant in Java.")
                .containsKey(Arrays.asList("1) initial Const const", "2) String CONST", "3) static final String const",
                        "4) static final String CONST")));
        Assert.assertTrue(questions.get("If the value of the field does not change during the execution " +
                        "of the program, then it should be?")
                .containsKey(Arrays.asList("1) public", "2) static", "3) final", "4) Destroyed! :)")));

        Assert.assertEquals(questions.get("What is the latest version of Java?")
                .get(Arrays.asList("1) 17", "2) 23", "3) 99", "4) 20")), "4");
        Assert.assertEquals(questions.get("What is the maximum complexity of a boolean expression allowed?")
                .get(Arrays.asList("1) 2", "2) 4", "3) 64", "4) 8")), "2");
        Assert.assertEquals(questions.get("What is the style of writing a class name in Java?")
                .get(Arrays.asList("1) CamelCase", "2) lowerCamelCase", "3) snake_case", "4) dog@case")), "1");
        Assert.assertEquals(questions.get("Select an example of a valid constant in Java.")
                .get(Arrays.asList("1) initial Const const", "2) String CONST", "3) static final String const",
                        "4) static final String CONST")), "4");
        Assert.assertEquals(questions.get("If the value of the field does not change during the execution " +
                        "of the program, then it should be?")
                .get(Arrays.asList("1) public", "2) static", "3) final", "4) Destroyed! :)")), "3");
    }
}
