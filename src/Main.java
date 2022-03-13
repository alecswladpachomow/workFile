import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        StringBuilder log = new StringBuilder("лог:" + '\n');
        List<String> folder = Arrays.asList("src", "res", "savegames", "temp");
        String route = "F://project/project/Game/";
        log.append(createFolder(route, folder));
        folder = Arrays.asList("main", "test");
        route = "F://project/project/Game/src/";
        log.append(createFolder(route, folder));
        String name = "main.java";
        log.append(createFile(route + "main/", name));
        name = " Utils.java";
        log.append(createFile(route + "main/", name));
        route = "F://project/project/Game/temp/";
        name = "temp.txt";
        log.append(createFile(route, name));
        folder = Arrays.asList("drawables", "vectors", "icons");
        route = "F://project/project/Game/res/";
        log.append(createFolder(route, folder));
        try (FileWriter writer = new FileWriter(
                "F://project/project/Game/temp/temp.txt", true)) {
            writer.write(log.toString());
            writer.append("лог сохранён в файл temp.txt ");
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage(
            ));
        }
        route = "F://project/project/Game/savegames/";
        saveGame(route + "save.dat",
                new GameProgress(84, 15, 2, 246.12));
        saveGame(route + "save1.dat",
                new GameProgress(90, 19, 8, 463.57));
        saveGame(route + "save2.dat",
                new GameProgress(56, 25, 5, 146.56));

        List<String> folder1 = Arrays.asList("save.dat","save1.dat","save2.dat");
        zipFiles(route, folder1);
    }

    private static void saveGame(String router, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(router);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage(
            ));
        }
    }

    private static void zipFiles(String route, List<String> folder) throws IOException {
        try (ZipOutputStream out = new ZipOutputStream
                (new FileOutputStream(route + "GameProgress.zip"));) {
            for (int i = 0; i < folder.size(); i++) {
                out.putNextEntry(new ZipEntry(folder.get(i)));
                try (InputStream in = new FileInputStream(route + folder.get(i))) {
                    byte[] buffer = new byte[in.available()];
                    in.read(buffer);
                    out.write(buffer);
                    out.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        for (int i = 0; i <folder.size() ; i++) {
            File file = new File(route + folder.get(i));
            file.delete();
        }
    }


    private static String createFile(String route, String name) {
        String report = "";
        File myFile = new File(route + name);
        try {
            if (myFile.createNewFile())
                report = "Файл  " + name + " был создан" + '\n';
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            report = "Файл  " + name + " не создан" + '\n';
        }
        return report;
    }

    private static String createFolder(String route, List<String> folder) {
        String s = "";
        String pathname;
        String report = "";
        for (int i = 0; i < folder.size(); i++) {
            s = folder.get(i);
            pathname = route + s;
            File dir = new File(pathname);
            if (dir.mkdir())
                report = report + "папка " + folder.get(i) + "   создана" + '\n';

        }
        return report;
    }
}
