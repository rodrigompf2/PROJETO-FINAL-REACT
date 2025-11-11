package br.com.serratec.entity;

import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "tb_produto")
@SQLDelete(sql = "UPDATE tb_produto SET deletado = true WHERE id = ?")
@Where(clause = "deletado = false")
public class Produto {

	@Id
	@GeneratedValue (strategy = GenerationType.UUID)
	private UUID id;
	
	private String nome;
	private Double valor;
	
	@Column(length = 2000)
	private String descricao;
	
	@Column(length = 500)
	private String imagemUrl;
    
    private Boolean deletado = false;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_categoria")
	@JsonBackReference
	private Categoria categoria;
    
    @OneToMany(mappedBy = "produto") 
    @JsonManagedReference 
    private List<Avaliacao> avaliacoes; 
	
	public Produto() {
		
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getImagemUrl() {
		return imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}
    
    public Boolean getDeletado() {
        return deletado;
    }

    public void setDeletado(Boolean deletado) {
        this.deletado = deletado;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }
}