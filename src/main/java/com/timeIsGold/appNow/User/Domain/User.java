package com.timeIsGold.appNow.User.Domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.Data;
@Data
public class User implements UserDetails{
	private String phoneNum;
	private String password;
	private String name;
	private String nickname;
	private String gender;
	private String detailOccupationCode;
	private	int pointJG = 0;
	private int cumulativeCount = 0;
	private Level level;
	private String userServiceAgrment = "0";
	private String userInfoAgrment = "0";
	
	
	public User() {
	}
	
	public User(String phoneNum, String password, String name, String nickname, String gender,
			String detailOccupationCode, int cumulativeCount , String userServiceAgrment, String userInfoAgrment ) {
		this.phoneNum = phoneNum;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.gender = gender;
		this.detailOccupationCode = detailOccupationCode;
		this.cumulativeCount = cumulativeCount;
		this.userServiceAgrment = userServiceAgrment;
		this.userInfoAgrment = userInfoAgrment;
	}
	
	
	public User(String phoneNum, String password, String name, String nickname, String gender,
			String detailOccupationCode, int cumulativeCount, Level level , String userServiceAgrment, String userInfoAgrment) {
		this.phoneNum = phoneNum;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.gender = gender;
		this.detailOccupationCode = detailOccupationCode;
		this.cumulativeCount = cumulativeCount;
		this.level = level;
		this.userServiceAgrment = userServiceAgrment;
		this.userInfoAgrment = userInfoAgrment;
	}
	
	public void upgradeLevel() {
		Level next = this.level.nextLevel();
		if(next == null) {
			throw new IllegalStateException(this.level + " 은 업그레이드가 불가능합니다. ");
		}else {
			this.level = next;
			System.out.println("userTest :" + name + level );
		}
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
		return list;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
