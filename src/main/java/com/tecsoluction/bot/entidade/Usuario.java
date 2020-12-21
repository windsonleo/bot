package com.tecsoluction.bot.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tecsoluction.bot.framework.BaseEntity;

import lombok.Getter;
import lombok.Setter;


@Entity(name = "USUARIO")
@Table
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public class Usuario  extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	
	 @Column(name = "iduser", nullable = false,unique=true)
	private String iduser;
	
	 @Column(name = "nome", nullable = false)
//	 @NotNull(message="o nome do Usuario não pode ser nulo")
//	 @NotBlank(message="o nome do Usuario não pode ser branco")
	private String nome;
	
	 @Column(name = "senha", nullable = true)
//	 @Size(min=6,message="a senha deve possuir no minimo 6 caracteres")
//	 @NotNull(message="o senha do Usuario não pode ser nulo")
//	 @NotBlank(message="o senha do Usuario não pode ser branco")
	 private String senha;
	
	 @Column(name = "grana", nullable = true)
//	 @Email(message="digite um email valido")
//	 @NotNull(message="o email do Usuario não pode ser nulo")
//	 @NotBlank(message="o email do Usuario não pode ser branco")
	private String grana;
	
	 @Column(name = "foto", nullable = true)
    private String foto;
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date datacadastro;
		

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,targetEntity=Role.class)
    @JoinTable(name = "usuario_role",
            joinColumns = @JoinColumn(name = "idusuario"),
            inverseJoinColumns = @JoinColumn(name = "idrole"))
//    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
	 private Set<Role> roles;
    
    
//    @JsonIgnore
//    @ManyToMany(fetch=FetchType.EAGER,targetEntity=Produto.class)
//    @JoinTable(name = "usuario_indicacao",
//    joinColumns = @JoinColumn(name = "idusuario"),
//    inverseJoinColumns = @JoinColumn(name = "idproduto"))
//   // @Cascade(org.hibernate.annotations.CascadeType.DELETE)
//	 private Set<Produto> indicacoes;
    
    
//    
//		 @Column(name = "facebookid", nullable = true,unique=true)
//		 private String facebookid;
	 
	    @Column(name = "online", nullable = true)
	    private boolean online;
	    
	    @Temporal(TemporalType.DATE)
	    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
		private Date dataultimoAcesso;
	    

//	    @JsonIgnore
//	    @OneToMany(mappedBy = "usuario", cascade = {CascadeType.REMOVE},fetch=FetchType.EAGER)
////	    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
//		 private Set<Atividade> atividades;
//	    
//	    @JsonIgnore
//	    @ElementCollection(fetch=FetchType.EAGER,targetClass=Mensagem.class)
//	    @CollectionTable(name = "mensagem_usuario", joinColumns = @JoinColumn(name = "id"))
////	    @JsonManagedReference
//	    private List<Mensagem> mensagens;
//	    
//	    @JsonIgnore
//	    @OneToMany(mappedBy="usuario",fetch=FetchType.EAGER,targetEntity=Sorteio.class,orphanRemoval=true)
//	    private Set<Sorteio> sorteios;

    
//    @JsonIgnore
//    @OneToMany(mappedBy="usuario",fetch=FetchType.EAGER)
//    private Set<Atividade> atividades;
//    
//    
//    @JsonIgnore
//    @OneToMany(mappedBy="usuario",fetch=FetchType.EAGER)
//    private Set<Atendimento> atendimentos;
    
    
    
//    @OneToOne
//    @JoinColumn(name="perfil_id")
//    private Perfil perfil;

//    @OneToOne
//    @JoinColumn(name="telefone_id")
//    private Telefone telefone;
    
    
	public Usuario() {
		
//		indicacoes = new HashSet<Produto>();
		datacadastro = new Date();
//		atividades = new HashSet<Atividade>();
		roles = new HashSet<Role>();
		
	}
	
	
//    public void addIndicacao(Produto not){
//    	
//    	
//    	this.getIndicacoes().add(not);
//    	
//    }


//    public void removeIndicacao(Produto not){
//    	
//    	this.getIndicacoes().remove(not);
//    	
//    }
    
//    public void addAtividade(Atividade not){
//    	
//    	
//    	this.getAtividades().add(not);
//    	
//    }


//    public void removeAtividade(Atividade not){
//    	
//    	this.getAtividades().remove(not);
//    	
//    }
    
    
    
//    public void addMensagem(Mensagem item){
//    	
//    	
//    	this.getMensagens().add(item);
//    	
//    	
//    	
//    }
    
    
//    public void removeMensagem(int index){
//    	
//    	
//    	this.getMensagens().remove(index);
//	
//    	
//    }
    
    
  public void addRoles(Role item){
    	
    	
    	this.getRoles().add(item);
    	
    	
    	
    }
    
    
    public void removeRoles(int index){
    	
    	
    	this.getRoles().remove(index);
	
    	
    }
	
	    
	    
		@Override
		public String toString() {
			return nome+ativo+id+datacadastro+dataultimoAcesso+foto+grana+iduser+online+senha+roles;
		}
	
	
	
}
