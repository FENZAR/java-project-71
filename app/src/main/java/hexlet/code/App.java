package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "1.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {

    private static final Integer ERROR_EXIT_CODE = 0;
    private static final Integer SUCCESS_EXIT_CODE = 1;

    @Parameters(paramLabel = "filepath1",
            index = "0",
            description = "path to first file")
    private String filePath1;

    @Parameters(paramLabel = "filepath2",
            index = "1",
            description = "path to second file")
    private String filePath2;

    @Option(names = {"-f", "--format"},
            description = "output format [default: stylish]",
            defaultValue = "stylish")
    private String format = "stylish";

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        try {
            String formattedDiff = Differ.generate(filePath1, filePath2);
            System.out.println(formattedDiff);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ERROR_EXIT_CODE;
        }

        return SUCCESS_EXIT_CODE;
    }
}
