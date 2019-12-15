<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<header id="header" class="hide">
			<h1>
				<a href="/"> <img src="">
				</a>
			</h1>
			<div class="gnb">
				<ul id="nav">
					<sec:authorize access="isAnonymous()">
							<li> <a href="/common/login" id="signInBtn">로그인</a></li>
					</sec:authorize>
					<sec:authorize access="isAuthenticated()">
						<!--  <li> <a id="signOutBtn">로그아웃</a></li>-->
					</sec:authorize>
					<li><a href="/"
						onclick="window.open(this.href, '', ''); return false;">이번탭</a></li>
					<li><a href="/"
						onclick="window.open(this.href, '', ''); return false;">삼번탭</a></li>
				</ul>
			</div>
			<div class="m_menu">
				<ul>
					<li class="search"></li>
					<li class="login"></li>
				</ul>
			</div>
</header>
