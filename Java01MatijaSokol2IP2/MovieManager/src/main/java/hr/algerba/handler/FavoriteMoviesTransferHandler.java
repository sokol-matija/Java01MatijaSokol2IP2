package hr.algerba.handler;

import hr.algebra.dal.Repository;
import hr.algebra.model.Movie;
import hr.algebra.model.User;
import hr.algebra.utilities.MessageUtils;
import hr.algebra.view.model.MovieTableModel;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.List;
import javax.swing.*;

public class FavoriteMoviesTransferHandler extends TransferHandler {

    private final Repository repository;
    private final User currentUser;
    private final MovieTableModel allMoviesModel;
    private final MovieTableModel favoriteMoviesModel;
    private final JTable allMoviesTable;
    private final JTable favoriteMoviesTable;

    public FavoriteMoviesTransferHandler(Repository repository, User currentUser,
            MovieTableModel allMoviesModel, MovieTableModel favoriteMoviesModel,
            JTable allMoviesTable, JTable favoriteMoviesTable) {
        this.repository = repository;
        this.currentUser = currentUser;
        this.allMoviesModel = allMoviesModel;
        this.favoriteMoviesModel = favoriteMoviesModel;
        this.allMoviesTable = allMoviesTable;
        this.favoriteMoviesTable = favoriteMoviesTable;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        boolean can = support.isDrop() && support.isDataFlavorSupported(DataFlavor.stringFlavor);
        return can;
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        Transferable transferable = support.getTransferable();
        String data;
        try {
            data = (String) transferable.getTransferData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        int movieId = Integer.parseInt(data);
        Movie movieToMove = findMovieById(movieId);

        if (movieToMove != null) {
            try {
                repository.addFavoriteMovie(currentUser.getUsrename(), movieToMove.getId());
                loadMovies();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                MessageUtils.showErrorMessage("Error", "Failed to add favorite: " + ex.getMessage());
                return false;
            }
        }

        return false;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return COPY;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        JTable table = (JTable) c;
        int row = table.getSelectedRow();
        if (row != -1) {
            Movie movie = allMoviesModel.getMovieAt(row);
            return new StringSelection(String.valueOf(movie.getId()));
        }
        return null;
    }

    private void loadMovies() {
        try {
            List<Movie> allMovies = repository.selectMovies();
            List<Movie> favoriteMovies = repository.selectFavoriteMovies(currentUser.getUsrename());

            allMoviesModel.setMovies(allMovies);
            favoriteMoviesModel.setMovies(favoriteMovies);

            allMoviesTable.repaint();
            favoriteMoviesTable.repaint();
        } catch (Exception ex) {
            MessageUtils.showErrorMessage("Error", "Failed to reload movies: " + ex.getMessage());
        }
    }

    private Movie findMovieById(int movieId) {
        for (int i = 0; i < allMoviesModel.getRowCount(); i++) {
            Movie movie = allMoviesModel.getMovieAt(i);
            if (movie.getId() == movieId) {
                return movie;
            }
        }
        return null;
    }

}
