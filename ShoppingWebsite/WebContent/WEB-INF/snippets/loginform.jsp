<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<form method=post>
<table class="center">
	<tr><td colspan=2><span style="color:red;">${param.msg}</span></td></tr>
	<tr><td colspan=2><h2>Login Form</h2></td></tr>
	<tr>
		<td align=left>Name: </td>
		<td><input type=text name=lname></td>
	</tr>
	<tr>
		<td align=left>Password: </td>
		<td><input type=password name=lpass></td>
	</tr>
	<tr><td colspan=2><input type=submit value=Login></td></tr>
</table>
</form>