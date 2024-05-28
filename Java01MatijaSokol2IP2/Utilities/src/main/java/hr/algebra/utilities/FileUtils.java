package hr.algebra.utilities;

import hr.algebra.factory.UrlConnectionFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class FileUtils {

    private static final String UPLOAD = "Upload";
    private static final String SAVE = "Save";
    private static final String TEXT_DOCUMENT = "Text document (*.txt)";
    private static final String TXT = "txt";

    public static Optional<File> uploadFile(String description, String... extensions) {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setFileFilter(new FileNameExtensionFilter(description, extensions));
        chooser.setDialogTitle(UPLOAD);
        chooser.setApproveButtonText(UPLOAD);
        chooser.setApproveButtonToolTipText(UPLOAD);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);
            return Arrays.asList(extensions).contains(extension.toLowerCase()) ? Optional.of(selectedFile) : Optional.empty();
        }
        return Optional.empty();
    }

    public static void copyFromUrl(String source, String destination) throws MalformedURLException, IOException {
        createDirHierarchy(destination);
        HttpURLConnection con = UrlConnectionFactory.getHttpURLConnection(source);
        try (InputStream is = con.getInputStream()) {
            Files.copy(is, Paths.get(destination));
        }
    }

    public static void copy(String source, String destination) throws FileNotFoundException, IOException {
        createDirHierarchy(destination);
        Files.copy(Paths.get(source), Paths.get(destination));
    }

    private static void createDirHierarchy(String destination) throws IOException {
        String dir = destination.substring(0, destination.lastIndexOf(File.separator));
        if (!Files.exists(Paths.get(dir))) {
            Files.createDirectories(Paths.get(dir));
        }
    }

    public static boolean filenameHasExtension(String filename, int length) {
        return filename.contains(".") && filename.substring(filename.lastIndexOf(".") + 1).length() == length;
    }
}
