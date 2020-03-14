package cat.copernic.erick.projectm07;

public class Rutas {
    private String idRuta;
    private String usuarioRuta;
    private String nombreRuta;
    private String descripcionRuta;
    private String ruta;
    private String ciudadRuta;
    private String paisRuta;


    public Rutas() {
    }

    public Rutas(String idRuta, String usuarioRuta, String nombreRuta, String descripcionRuta, String ruta, String ciudadRuta, String paisRuta) {
        this.idRuta = idRuta;
        this.usuarioRuta = usuarioRuta;
        this.nombreRuta = nombreRuta;
        this.descripcionRuta = descripcionRuta;
        this.ruta = ruta;
        this.ciudadRuta = ciudadRuta;
        this.paisRuta = paisRuta;
    }

    public String getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(String idRuta) {
        this.idRuta = idRuta;
    }

    public String getUsuarioRuta() {
        return usuarioRuta;
    }

    public void setUsuarioRuta(String usuarioRuta) {
        this.usuarioRuta = usuarioRuta;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public String getDescripcionRuta() {
        return descripcionRuta;
    }

    public void setDescripcionRuta(String descripcionRuta) {
        this.descripcionRuta = descripcionRuta;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getCiudadRuta() {
        return ciudadRuta;
    }

    public void setCiudadRuta(String ciudadRuta) {
        this.ciudadRuta = ciudadRuta;
    }

    public String getPaisRuta() {
        return paisRuta;
    }

    public void setPaisRuta(String paisRuta) {
        this.paisRuta = paisRuta;
    }
}
