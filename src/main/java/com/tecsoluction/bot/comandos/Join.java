package com.tecsoluction.bot.comandos;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tecsoluction.bot.entidade.Role;
import com.tecsoluction.bot.entidade.Usuario;
import com.tecsoluction.bot.servico.RoleServicoImpl;
import com.tecsoluction.bot.servico.UsuarioServicoImpl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Controller
public class Join extends ListenerAdapter {
	
    private static final Logger logger = LoggerFactory.getLogger(Join.class);


	@Autowired
private  final	UsuarioServicoImpl usuarioservico  = new UsuarioServicoImpl();
	@Autowired
private  final	RoleServicoImpl roleservico  = new RoleServicoImpl();
	
	
	
	String[] messages = {
			"[member] Entrou. Voce Deve Nos Dar um Alow.",
			"Nunca nos Deixe pra baixo [member]. Nunca Deixaremos [member] Triste!",
			"Oi! Ouve isso! [member] se Juntou a nos!",
			"Ha! [member] Entrou!Voce foi Ativado na Armadilha de Travestis!",
			"Nós Estavamos te Esperando, [member].",
			"é Perigoso ficar Sozinho, chama [member]!",
			"Swoooosh. [member] just landed.",
			"Abrace seu destino. [member] é só chegar.",
			"Um Selvagem [member] apareceu."
	};
	
	
	public String user;
	
	public Usuario usu = new Usuario();
	
//	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
//		Random rand = new Random();
//		int number = rand.nextInt(messages.length);
//		
//		EmbedBuilder join = new EmbedBuilder();
//		join.setColor(0x66d8ff);
//		join.setDescription(messages[number].replace("[member]", event.getMember().getAsMention()));
//	
//		event.getGuild().getDefaultChannel().sendMessage(join.build()).queue();
//		
//		// Add role
//		event.getMember().getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoles().get(0)).complete();
//	}
	


@Override
public void onGuildMemberJoin(GuildMemberJoinEvent event) {

	Random rand = new Random();
	int number = rand.nextInt(messages.length);
	
	user = event.getMember().getAsMention();
	
	EmbedBuilder join = new EmbedBuilder();
	join.setColor(0x66d8ff);
	join.setDescription(messages[number].replace("[member]", user));

	
	
	
	
	event.getGuild().getDefaultChannel().sendMessage(join.build()).queue();
	
	
	String s = event.getMember().getUser().getId();
	
	
	
	System.out.println("user mention id :" + user);
	System.out.println("memebro id :" + s);
	
	System.out.println("user id :" + event.getUser().getId());
	System.out.println("nickname EFETIV id :" + event.getMember().getEffectiveName());

	
		
	
	//Usuario usuario  = usuarioservico.findByIdUser(s);
	
	 this.usu = SalvarUsuario(event);
	
	System.out.println("usu :" + usu);

	
	//suarioservico.findByIdUser(s);
	
//	 UUID idf = UUID.fromString("cd1b2ecd-f7be-41ef-b637-70dc570ca82c");
	
	//Role  role = roleservico.findOne(idf);
	
//	Role  rolem = new Role();
	
//	rolem.setAtivo(true);
//	rolem.setName("ROLE_PADRÃO");
	//rolem.setId(idf);
	
	//Role  role = roleservico.save(rolem);
	
//	System.out.println("rolem id :" + rolem);
//	
//	System.out.println("role id :" + role);
	
//	if(usuario.equals(usu)) {
		
		//usuarioservico
		

		
		
		
		
//	}else {
		
		// não é nulo, existe o usuario no banco
		
		//System.out.println("salvou :" + usuario.toString());
		
		
//	}
	
	// Add role
//	event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoles().get(0)).complete();	
	
	
}


private  Usuario SalvarUsuario(GuildMemberJoinEvent event) {
	
	UUID idf = UUID.fromString("cd1b2ecd-f7be-41ef-b637-70dc570ca54b");
	
	Set<Role> roles = new HashSet<Role>();
	
	Role role = new Role();
	
	Usuario usuario  = new Usuario();
	usuario.setAtivo(true);
	usuario.setDatacadastro(new Date());
	usuario.setDataultimoAcesso(new Date());
	usuario.setFoto(event.getMember().getUser().getAvatarUrl());
	usuario.setGrana("0.00");
	usuario.setIduser(event.getUser().getId());
	usuario.setNome(event.getMember().getUser().getName());
	usuario.setOnline(true);
	usuario.setSenha("123456");
	usuario.setId(idf);
	//usuario.addRoles(PegarRole());
	role = PegarRole();
	
	System.out.println("salvou role :" + role.toString());

	
	roles.add(role);
	
	usuario.setRoles(roles);
	
	usuarioservico.save(usuario);
	
	System.out.println("salvou usuario :" + usuario.toString());

	
	return usuario;
}

private  Role PegarRole() {
	
	 UUID idf = UUID.fromString("6d3cfce8-8a93-4ce9-b197-7b509653aca4");
	 Role  rolem = new Role();
		rolem.setAtivo(true);
		rolem.setName("ROLE_PADRÃO");
		rolem.setId(idf);
		
		roleservico.save(rolem);
	
	return rolem;
}

}