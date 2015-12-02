<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title>Spring 3 MVC Series - Contact Manager</title>
</head>
<body>
<h2>Contact Manager</h2>
<form:form method="get" action="addContact.html">
 
    <table>
     <tr>
        <td><form:label path="empno">Emp No</form:label></td>
        <td><form:input path="empno" /></td> 
    </tr>
         <tr>
        <td><form:label path="gender">Gender</form:label></td>
        <td><form:input path="gender" /></td> 
    </tr>
    <tr>
        <td><form:label path="fname">First Name</form:label></td>
        <td><form:input path="firstname" /></td> 
    </tr>
    
    <tr>
        <td><form:label path="email">Email</form:label></td>
        <td><form:input path="email" /></td>
    </tr>
    <tr>
        <td><form:label path="cell">Telephone</form:label></td>
        <td><form:input path="cell" /></td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" value="Add Contact"/>
        </td>
    </tr>
</table>  
     
</form:form>
</body>
</html>
