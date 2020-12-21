package com.tecsoluction.bot.servico;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecsoluction.bot.dao.IUsuarioDAO;
import com.tecsoluction.bot.entidade.Usuario;
import com.tecsoluction.bot.framework.AbstractEntityService;




/*  criar validacaoes para que o servico as chamem caso nao haja erros execute a acao  */


@Service("userService")
@Transactional
//@PersistenceContext
public class UsuarioServicoImpl extends AbstractEntityService<Usuario> {


    @Autowired
    private  IUsuarioDAO dao;
    
    

    public UsuarioServicoImpl() {

        super(Usuario.class, "usuario");


    }


    @Override
    public Usuario save(Usuario user) {

//		user.setRoles(new HashSet<>());
//		userRepository.save(user);
        dao.saveAndFlush(user);

        return user;

    }
    
    
    public static Usuario savestatis(Usuario user) {

//		user.setRoles(new HashSet<>());
//		userRepository.save(user);
  //      dao.saveAndFlush(user);

        return user;

    }


    public Usuario findByIduser(String username) {

        return dao.findByIduser(username);
    }


    @Override
    protected JpaRepository<Usuario, UUID> getDao() {

        return dao;
    }


    @Override
    protected void validateSave(Usuario post) {
        // TODO Auto-generated method stub

    }

    @Override
    protected String getIdEntity(Usuario usuario) {
        return usuario.getId().toString();
    }


    @Override
    protected void validateEdit(Usuario post) {
        // TODO Auto-generated method stub

    }


    @Override
    protected void validateDelete(UUID id) {
        // TODO Auto-generated method stub

    }


	@Override
	public List<Usuario> findAllNew() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}
	
	
    public boolean Exist(UUID uuid) {
    	
    	boolean exist = false;
    	
    	exist = dao.existsById(uuid);
    	
    	
    	return exist;
    }
    
    
    
    @Override
    public Usuario findOne(UUID id) {
    	// TODO Auto-generated method stub
    	return dao.getOne(id);
    }


}
