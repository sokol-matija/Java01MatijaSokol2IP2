package hr.algebra.view.model;

import hr.algebra.model.Genre;
import hr.algebra.model.Movie;
import hr.algebra.model.Person;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

public class MovieTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {"ID", "Title", "Published Date", "Description", "Original Title", "Director", "Actors", "Duration", "Year", "Genres", "Rating", "Type"};

    private List<Movie> movies;

    public MovieTableModel(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return new ArrayList<>(movies);
    }

    public void setMovies(List<Movie> movies) {
        this.movies = new ArrayList<>(movies);
        this.movies.add(null);  // Add an empty row
        fireTableDataChanged();
    }

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
        if (movies.get(rowIndex) == null) {
            return "";
        }
        Movie movie = movies.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return movie.getId();
            case 1:
                return movie.getTitle();
            case 2:
                return movie.getPublishedDate().format(DateTimeFormatter.RFC_1123_DATE_TIME);
            case 3:
                return movie.getDescription();
            case 4:
                return movie.getOriginalTitle();
            case 5:
                return movie.getDirector().getName();
            case 6:
                return movie.getActors().stream().map(Person::getName).collect(Collectors.joining(", "));
            case 7:
                return movie.getDuration();
            case 8:
                return movie.getYear();
            case 9:
                return movie.getGenres().stream().map(Genre::toString).collect(Collectors.joining(", "));
            case 10:
                return movie.getRating();
            case 11:
                return movie.getType();
            default:
                throw new IndexOutOfBoundsException("Invalid column index");
        }
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0 || columnIndex == 7 || columnIndex == 8 || columnIndex == 10) {
            return Integer.class;
        }
        return String.class;
    }

    public Movie getMovieAt(int row) {
        if (row >= 0 && row < movies.size()) {
            return movies.get(row);
        }
        return null;
    }

    public void addEmptyRow() {
        movies.add(null);  // Add a null object to represent an empty row
        fireTableRowsInserted(movies.size() - 1, movies.size() - 1);
    }

    public void removeRow(int row) {
        movies.remove(row);
        fireTableRowsDeleted(row, row);
    }
}
