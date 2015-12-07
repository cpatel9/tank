<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	PLDS Roaster 
</h1>

<P>  The time on the server is ${serverTime}. </P>

    <table border="1">
        <tr>
            <th>Fname</th>
            <th>Cell</th>
            <th>Email</th>
            <th>Gender</th>
       	 <th>Empno</th>
        </tr>
        <c:forEach items="${contacts}" var="contacts">
            <tr>        
            <c:set var="gen" value="${contacts.gender}"/>
                	
           <c:if test="${gen =='M'}">
           	<td><img src="./resources/images/m.png" ></td>
           </c:if> 	
           <c:if test="${gen =='F'}">
           	<td><img src="./resources/images/f.png" ></td>
           </c:if> 	
                <td>${contacts.fname}</td>
                <td>${contacts.cell}</td>
                <td>${contacts.email}</td>
                <td>${contacts.gender}</td>
                <td>${contacts.empno}</td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>
