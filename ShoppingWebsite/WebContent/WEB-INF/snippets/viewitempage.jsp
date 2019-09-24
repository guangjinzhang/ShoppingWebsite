<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<form method=post>
<table class="center">
	<tr><td colspan=2><span style="color:red;">${param.msg}</span></td></tr>
	<tr><td colspan=2><h2>View Item</h2></td><td align=right><a href="Home">Back</a></td></tr>
		<tr>
		<td align=left>Item Id: ${iid}</td>
	</tr>
	<tr>
		<td align=left>Item Name: ${iname}</td>
	</tr>
	<tr>
		<td align=left>Item Quantity: ${iqty}</td>		
	</tr>
</table>
</form>