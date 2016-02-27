import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by sissoko on 19/02/2016.
 */
public class ModelGenerator {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("ModelGenerator Model");
            System.exit(1);
        }
        try {
            generate(args[0], "${Model}", "src/models");
            generate(args[0], "${Model}View", "src/views");
            generate(args[0], "${Model}Form", "src/views/forms");
            generate(args[0], "${Model}Table", "src/data");
            generate(args[0], "${Model}Controller", "src/controllers");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param modelName
     * @param template
     * @param destinationDir
     */
    private static void generate(String modelName, String template, String destinationDir) throws IOException {
        File modelTemplate = new File("templates", template);
        File targetFile = new File(destinationDir, template.replaceAll("\\$\\{Model\\}", modelName) + ".java");
        if(targetFile.exists()) {
            System.out.println(targetFile.getAbsolutePath() + " exists");
            return;
        }
        PrintWriter pw = new PrintWriter(targetFile);
        Scanner bis = new Scanner(new FileInputStream(modelTemplate));
        String line;
        while (bis.hasNext()) {
            line = bis.nextLine();
            line = line
                    .replaceAll("\\$\\{Model\\}", modelName)
                    .replaceAll("\\$\\{model\\}", modelName.toLowerCase())
                    .replaceAll("\\$\\{MODEL\\}", modelName.toUpperCase())
                    .replaceAll("\\$\\{date\\}", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            pw.println(line);
        }
        pw.close();
    }
}
