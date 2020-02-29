package cat.copernic.erick.projectm07;

public class Usuarios {
    private String nombre;
    private String edad;
    private String usuario;

    public Usuarios() {
    }

    public Usuarios(String nombre, String edad, String usuario) {
        this.nombre = nombre;
        this.edad = edad;
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}

