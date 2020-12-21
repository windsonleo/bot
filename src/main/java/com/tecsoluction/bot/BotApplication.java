package com.tecsoluction.bot;

import javax.security.auth.login.LoginException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

import com.tecsoluction.bot.comandos.Ajuda;
import com.tecsoluction.bot.comandos.Apagar;
import com.tecsoluction.bot.comandos.Avatar;
import com.tecsoluction.bot.comandos.Chutar;
import com.tecsoluction.bot.comandos.Convite;
import com.tecsoluction.bot.comandos.Join;
import com.tecsoluction.bot.comandos.Leave;
import com.tecsoluction.bot.comandos.Matar;
import com.tecsoluction.bot.comandos.Perfil;
import com.tecsoluction.bot.comandos.Ping;
import com.tecsoluction.bot.comandos.Server;
import com.tecsoluction.bot.comandos.Ship;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

@SpringBootApplication
@EntityScan(basePackages = "com.tecsoluction.bot")
@Import({DataSourceConf.class})
public class BotApplication {

	public static void main(String[] args) {
		
		JDABuilder builder =JDABuilder.createDefault("Nzg3NDA4NzUzNTkwMTQwOTU4.X9UhjA.0S56PXpvCxI86uzQNsvznyRniPI");
		JDA jda=null;
		builder.setActivity(Activity.watching("Membros do Grupo"));
		builder.setStatus(OnlineStatus.ONLINE);
		builder.enableIntents(GatewayIntent.GUILD_MEMBERS,GatewayIntent.GUILD_PRESENCES,GatewayIntent.GUILD_BANS);
		builder.enableCache(CacheFlag.CLIENT_STATUS,CacheFlag.MEMBER_OVERRIDES);
		
		Ping ping = new Ping();
		builder.addEventListeners(ping);
	
		Convite convite = new Convite();
		builder.addEventListeners(convite);
		
		Apagar apagar = new Apagar();
		builder.addEventListeners(apagar);
		
		
		Ship ship = new Ship();
		builder.addEventListeners(ship);
		
		Perfil perfil = new Perfil();
		builder.addEventListeners(perfil);
		
		Server server = new Server();
		builder.addEventListeners(server);


		Ajuda ajuda = new Ajuda();
		builder.addEventListeners(ajuda);
		
		Matar matar = new Matar();
		builder.addEventListeners(matar);
		
		Avatar avatar = new Avatar();
		builder.addEventListeners(avatar);
		
		//Join join = new Join();
		builder.addEventListeners(new Join());
		
		//Leave leave = new Leave();
		builder.addEventListeners(new Leave());
		
		
		
		builder.addEventListeners(new Chutar());
		
		
		//builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
//		builder.enableIntents(GatewayIntent.GUILD_MEMBERS,GatewayIntent.GUILD_PRESENCES);
//		builder.enableCache(CacheFlag.CLIENT_STATUS);
		try {
			
			jda = builder.build();
			
		} catch (LoginException e) {

			e.printStackTrace();
			builder.setStatus(OnlineStatus.OFFLINE);
			
		}
		
		
		
		try {
			
			jda.awaitReady();
			
		} catch (InterruptedException e) {

		
			e.printStackTrace();
			builder.setStatus(OnlineStatus.OFFLINE);
		
		}
		
		
		
		
		SpringApplication.run(BotApplication.class, args);
		
	
	
	
	
	
	}

}
