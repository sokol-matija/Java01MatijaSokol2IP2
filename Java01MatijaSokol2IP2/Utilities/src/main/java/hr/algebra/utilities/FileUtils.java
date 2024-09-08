package hr.algebra.utilities;

import hr.algebra.factory.UrlConnectionFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Optional;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class FileUtils {

    private FileUtils() {
    }

    private static final String UPLOAD = "Upload";
    private static final String SAVE = "Save";
    private static final String TEXT_DOCUMENTS = "Text document (*.txt)";
    private static final String TXT = "txt";

    //TODO: File uploadfile - refactor to use opitonal
    public static File uploadFile(String description, String... extensions) {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setFileFilter(new FileNameExtensionFilter(description, extensions));
        chooser.setDialogTitle(UPLOAD);
        chooser.setApproveButtonText(UPLOAD);
        chooser.setApproveButtonToolTipText(UPLOAD);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);
            return selectedFile.exists() && Arrays.asList(extensions).contains(extension.toLowerCase()) ? selectedFile : null;
        }
        return null;
    }

    public static void copyFromUrl(String source, String destination) throws MalformedURLException, IOException {
        createDirHierarchy(destination);
        HttpURLConnection con = UrlConnectionFactory.getHttpURLConnection(source);
        try (InputStream is = con.getInputStream()) {
            Files.copy(is, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } catch (SocketTimeoutException e) {
            System.out.println("Timeout while downloading image: " + source);
            throw e;
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

    public static Optional<File> saveTextInFile(String text, Optional<File> optFile) throws IOException {
        if (!optFile.isPresent()) {
            JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            chooser.setFileFilter(new FileNameExtensionFilter(TEXT_DOCUMENTS, TXT));
            chooser.setDialogTitle(SAVE);
            chooser.setApproveButtonText(SAVE);
            chooser.setApproveButtonToolTipText(SAVE);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                if (!selectedFile.toString().endsWith(TXT)) {
                    selectedFile = new File(selectedFile.toString().concat(".").concat(TXT));
                }
                Files.write(selectedFile.toPath(), text.getBytes());
                optFile = Optional.of(selectedFile);
            }
        } else {
            Files.write(optFile.get().toPath(), text.getBytes());
        }
        return optFile;
    }

    public static Optional<String> loadTextFromFile() throws IOException {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() || file.toString().endsWith(TXT);
            }

            @Override
            public String getDescription() {
                return TEXT_DOCUMENTS;
            }
        });
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return Optional.of(new String(Files.readAllBytes(chooser.getSelectedFile().toPath())));
        }
        return Optional.empty();
    }
}
