package hr.algebra.utilities;

import javax.swing.JOptionPane;

public class MessageUtils {

    private MessageUtils() {
    }

    public static void showInformationMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErrorMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showConfirmationMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.YES_NO_OPTION);
    }
}
