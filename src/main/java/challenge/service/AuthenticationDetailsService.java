package challenge.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationDetailsService {
	public String getUserName(){
		String name;
		try{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			name = auth.getName();
		} catch(Exception e){
			return null;
		}
		return name;
	}
}
