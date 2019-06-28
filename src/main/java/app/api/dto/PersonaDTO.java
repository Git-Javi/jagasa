package app.api.dto;

public class PersonaDTO {

	private Long id;
	private String nombre;
	private String tlf;
	
	
	public PersonaDTO(Long id, String nombre, String tlf) {
		this.id = id;
		this.nombre = nombre;
		this.tlf = tlf;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getTlf() {
		return tlf;
	}


	public void setTlf(String tlf) {
		this.tlf = tlf;
	}


	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombre=" + nombre + ", tlf=" + tlf + "]";
	}
	
	
	
	
}
