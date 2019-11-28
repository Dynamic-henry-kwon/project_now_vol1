package com.timeIsGold.appNow.User.Service.Security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.timeIsGold.appNow.User.Dao.UserDao;
import com.timeIsGold.appNow.User.Dao.Security.SecurityMybatisUserDao;
import com.timeIsGold.appNow.User.Domain.User;

import lombok.Setter;
@Component
public class CustomAuthenticationProvider  implements AuthenticationProvider{

	@Setter(onMethod_= {@Autowired})
	SecurityUserService securityUserService;
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
		User userInfo = securityUserService.loadUserByUsername(authToken.getName());
		if( userInfo == null) throw new UsernameNotFoundException(authToken.getName());
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) userInfo.getAuthorities();
		return new UsernamePasswordAuthenticationToken(userInfo, null, authorities);
	}
	
	private boolean matchPassword(String password, Object credentials) {
		return password.equals(credentials);
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
