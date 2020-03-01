package cat.copernic.erick.projectm07;

import java.util.List;

public class Usuarios {
    private String nombre;
    private String edad;
    private String usuario;
    private String direccion;
    private String sexo;
    //private List<Rutas> rutas;

    public Usuarios() {
    }

    public Usuarios(String nombre, String edad, String usuario, String direccion, String sexo) {
        this.nombre = nombre;
        this.edad = edad;
        this.usuario = usuario;
        this.direccion = direccion;
        this.sexo = sexo;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}

