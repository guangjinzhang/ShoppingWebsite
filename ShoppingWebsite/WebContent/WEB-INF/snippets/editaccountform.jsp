<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<form method=post>
<table class="center">
	<tr><td colspan=2><span style="color:red;">${param.msg}</span></td></tr>
	<tr><td colspan=2><h2>Edit Account</h2></td><td align=right><a href="Home">Back</a></td></tr>
	<tr>
		<td align=left>User Name: </td>
		<td><input type=text name=uname value=${name}></td>
	</tr>
	<tr>
		<td align=left>New Password: </td>
		<td><input type=password name=lpass></td>
	</tr>
	<tr>
		<td align=left>Confirm Password: </td>
		<td><input type=password name=cpass></td>
	</tr>
	<tr><td colspan=2><input type=submit value=OK></td></tr>
</table>
</form>