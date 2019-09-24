<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<form method=post>
<table class="center">
	<tr><td colspan=2><span style="color:red;">${param.msg}</span></td></tr>
	<tr><td colspan=2><h2>Edit Item</h2></td><td align=right><a href="Home">Back</a></td></tr>
	<tr>
		<td align=left>Item Name: </td>
		<td><input type=text name=iname value=${iname}></td>
	</tr>
	<tr>
		<td align=left>Item Quantity: </td>
		<td><input type=text name=iqty value=${iqty}></td>
	</tr>
	<tr><td colspan=2><input type=submit value=OK></td></tr>
</table>
</form>