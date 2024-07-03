package hr.algebra.view.model;

import hr.algebra.model.Movie;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class MovieTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {"ID", "Title", "PublishedDate", "Description", "OriginalTitle", "Director", "Actor", "Durtion", "Year", "Genres", "ImageLink", "Rating", "Type"};

    private List<Movie> movies;

    public MovieTableModel(List<Movie> movies) {
        this.movies = movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    //
    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return movies.get(rowIndex).getId();
            case 1:
                return movies.get(rowIndex).getTitle();
            case 2:
                return 2;
            //movies.get(rowIndex).getPublishedDate().format(Movie.DATE_FORMATTER);
            case 3:
                return movies.get(rowIndex).getDescription();
            case 4:
                return movies.get(rowIndex).getOriginalTitle();
            case 5:
                return movies.get(rowIndex).getDirector();
            case 6:
                return movies.get(rowIndex).getActors();
            case 7:
                return movies.get(rowIndex).getDuration();
            case 8:
                return movies.get(rowIndex).getYear();
            case 9:
                return movies.get(rowIndex).getGenres();
            case 10:
                return movies.get(rowIndex).getImageLink();
            case 11:
                return movies.get(rowIndex).getRating();
            case 12:
                return movies.get(rowIndex).getType();
            default:
                throw new RuntimeException("No such column");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

}
