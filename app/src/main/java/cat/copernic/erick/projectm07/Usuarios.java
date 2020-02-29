package cat.copernic.erick.projectm07;

public class Usuarios {
    private String userId;
    private String user;
    private String nombreUsuario;
    private int edad;
    private String direccion;

    public Usuarios() {
    }

    public Usuarios(String userId, String user, String nombreUsuario, int edad, String direccion) {
        this.userId = userId;
        this.user = user;
        this.nombreUsuario = nombreUsuario;
        this.edad = edad;
        this.direccion = direccion;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}

