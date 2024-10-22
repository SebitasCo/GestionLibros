import java.util.ArrayList;
import java.util.List;

class Libro {
    private int id;
    private String titulo;
    private String autor;
    private int anioPublicacion;

    public Libro(int id, String titulo, String autor, int anioPublicacion) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    @Override public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", anioPublicacion=" + anioPublicacion +
                '}';
    }
}

interface LibroDAO {
    void crear(Libro libro);
    Libro leer(int id);
    List<Libro> leerTodos();
    void actualizar(Libro libro);
    void eliminar(int id);
}

class LibroDAOImpl implements LibroDAO {
    private List<Libro> libros = new ArrayList<>();
    private int idContador =1;

    @Override public void crear(Libro libro) {
        Libro nuevoLibro = new Libro(idContador++, libro.getTitulo(), libro.getAutor(), libro.getAnioPublicacion());
        libros.add(nuevoLibro);
    }

    @Override public Libro leer(int id) {
        return libros.stream().filter(libro -> libro.getId() == id).findFirst().orElse(null);
    }

    @Override public List<Libro> leerTodos() {
        return new ArrayList<>(libros);
    }

    @Override public void actualizar(Libro libro) {
        for (int i =0; i < libros.size(); i++) {
            if (libros.get(i).getId() == libro.getId()) {
                libros.set(i, libro);
                return;
            }
        }
    }

    @Override public void eliminar(int id) {
        libros.removeIf(libro -> libro.getId() == id);
    }
}

public class Main {
    public static void main(String[] args) {
        LibroDAO libroDAO = new LibroDAOImpl();

        // Crear libros libroDAO.crear(new Libro(0, "El Quijote", "Miguel de Cervantes",1605));
        libroDAO.crear(new Libro(0, "Cien años de soledad", "Gabriel García Márquez",1967));
        libroDAO.crear(new Libro(0, "1984", "George Orwell",1949));

        // Leer todos los libros System.out.println("Libros en la biblioteca:");
        for (Libro libro : libroDAO.leerTodos()) {
            System.out.println(libro);
        }

        // Leer un libro por ID System.out.println("\nLeyendo libro con ID1:");
        System.out.println(libroDAO.leer(1));

        // Actualizar un libro libroDAO.actualizar(new Libro(1, "El Quijote", "Miguel de Cervantes",1615));
        System.out.println("\nLibros después de actualizar:");
        for (Libro libro : libroDAO.leerTodos()) {
            System.out.println(libro);
        }

        // Eliminar un libro libroDAO.eliminar(2);
        System.out.println("\nLibros después de eliminar el libro con ID2:");
        for (Libro libro : libroDAO.leerTodos()) {
            System.out.println(libro);
        }
    }
}