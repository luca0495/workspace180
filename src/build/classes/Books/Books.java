package Books;

public class Books {

	private String codice;
	private String nome_autore;
	private String cognome_autore;
	private String categoria;
	private String titolo;
	
	public Books(String codice,String nome_autore,String cognome_autore,String categoria,String titolo)
	{
		this.codice=codice;
		this.nome_autore=nome_autore;
		this.cognome_autore=cognome_autore;
		this.categoria=categoria;
		this.titolo=titolo;
		
	}

	public String getCodice() {
		return codice;
	}

	public String getNome_autore() {
		return nome_autore;
	}

	public String getCognome_autore() {
		return cognome_autore;
	}

	public String getCategoria() {
		return categoria;
	}

	public String getTitolo() {
		return titolo;
	}
	
}
