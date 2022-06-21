<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    MemberRepository memberRepository = MemberRepository.getInstance();
    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));

    Member newMember=new Member();
    newMember.setUsername(username);
    newMember.setAge(age);

    memberRepository.save(newMember);
%>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
    성공
    <ul>
        <li>id=<%=newMember.getId()%></li>
        <li>username=<%=newMember.getUsername()%></li>
        <li>age=<%=newMember.getAge()%></li>
    </ul>
    <a href="/index.html">메인</a>
    </body>
</html>