package testpack;

import java.sql.*;
import java.util.ArrayList;

public class DB_Access {
	private String url = "jdbc:mysql://localhost:3306/test";
	private String driver = "com.mysql.jdbc.Driver";
	private String uname = "root";
	private String upass = "";

	private Connection c;
	private Statement st;
	private PreparedStatement pst;

	public DB_Access() {
		try {
			Class.forName(driver).newInstance();
			c = DriverManager.getConnection(url, uname, upass);
			st = c.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int validateLogin(String un, String up) {
		int uid = -1; // lets agree that -1 is for invalid user login

		String sql = "select uid from tuser02 where loginname = ? and loginpass = ?";
		try {
			pst = c.prepareStatement(sql);
			pst.setString(1, un);
			pst.setString(2, up);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				uid = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return uid;
	}

	public String getUserName(int uid) {
		String sql = "select name from tuser02 where uid = " + uid;
		String uname = "";
		try {
			ResultSet rs = st.executeQuery(sql);
			if (rs.next())
				uname = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return uname;
	}

	public ArrayList<Item> getAllUserItems(int uid) {
		ArrayList<Item> all = new ArrayList<Item>();

		String sql = "select iid, itemname, qty from titems02 where uid = " + uid;

		try {
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Item i = new Item(rs.getInt(1), rs.getString(2), rs.getInt(3));
				all.add(i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return all;
	}

	public int createUserAccount(User u) {
		
		// 0 means everything is OK, user is created
		// 1 means values are too long
		// 2 means unique constraint on the login name has been violated
		// 3 means that an empty form field was submitted
		// 4 means that the passwords are not the same
		int status = 0;
		try {
		if(u.getLoginName().trim().equals("") || 
				u.getName().trim().equals("") || 
				u.getLoginPass1().trim().equals("") ||
				u.getLoginPass2().trim().equals("")) return 3;
		if(u.getLoginName().trim().length() > 20 || 
				u.getName().trim().length() > 20 || 
				u.getLoginPass1().trim().length()> 20 ||
				u.getLoginPass2().trim().length()>20) return 1;
		
		if(!u.getLoginPass1().trim().equals(u.getLoginPass2().trim())) return 4;
		
		String sql = "insert into tuser02 (LoginName, Name, LoginPass) values (?, ?, ?)";
		
		
			pst = c.prepareStatement(sql);
			pst.setString(1, u.getLoginName());
			pst.setString(2, u.getName());
			pst.setString(3, u.getLoginPass1());
			pst.executeUpdate();
		} catch (SQLException e) {
			status=2;
		//	e.printStackTrace();
		}

		return status;
	}

	public int updateAccount(User u) { // user constructor class object u

		int res = 0;
		while (u.getName().trim().equals("") || u.getLoginPass1().trim().equals("")
				|| u.getLoginPass2().trim().equals(""))
			return 3;
		while ( u.getName().trim().length() > 20
				|| u.getLoginPass1().trim().length() > 20 || u.getLoginPass2().trim().length() > 20)
			return 1;

		while (!u.getLoginPass1().trim().equals(u.getLoginPass2().trim()))
			return 4;
		// 3 params. LoginName, Name, LoginPass where uid=??
		String sql = "update tuser02 set Name=?, LoginPass=? where uid=?";

		try {
			pst = c.prepareStatement(sql);
			pst.setString(1, u.getName());
			pst.setString(2, u.getLoginPass1());
			pst.setInt(3, u.getUid());
			pst.executeUpdate();
		} catch (SQLException e) {
            res=2;
			e.printStackTrace();
		}

		return res;
	}

	public int updateItem(Item i, Integer uid) { // reason for uid is that it is the unique
									// user id
		int res = 0;
		// String sql="update titems02 set itemname=?, qty=? where uid=?" +
		// i.getId() ;

		// String sql = "update titems02 set name='"+i.getName()+"' ,
		// qty="+i.getQty()+" where uid="+uid+" and itemid="+i.getId();
		// the above is without PST
		// set itemname equal to the iname param. qty equa to the iqty param
		String sql;
		sql = "update titems02 set itemname=?, qty=? where iid=?";// itemname is
														
		try {
			// 0 - OK - item was inserted
			// 1 - item name was not given
			// 2 - item qty was either not given or not a valid int
			/*
			 * if(i.getName() == null || i.getName().trim().equals("")) return
			 * 1; try { i.getQty(); }catch(Exception e) { return 2; }
			 */
			pst = c.prepareStatement(sql);
			pst.setString(1, i.getName());
			pst.setInt(2, i.getQty());// yes
			pst.setInt(3, i.getId()); // 3,iid
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// sql="update titems02 set ItemName='"+ iname+ "', Qty='" + iqty +
		// "'where uid in' ("+uid+") 'and iid=" + iid; //iname and iqty are
		// inside the string, reference the variable names and concatenate them
		// in the string"
		// item name is string 1

		return res;
	}

	public int deleteItem(int iid, Integer uid) { // logged in user can only log in and iid
									// passed as a parameter
		int res = 0;
		String sql = "delete from titems02 where iid=? and uid=?";
		try {
			pst = c.prepareStatement(sql);
			pst.setInt(1, iid); // delete the iid
			pst.setInt(2, uid);
			res=pst.executeUpdate();// because we need to execute the query string
		}

		catch (SQLException e) {			
			e.printStackTrace();
		}
	
		return res;
	}
	
	public Item getItemInfo(int iid, int uid) {
		Item item=new Item();
		String sql = "select ItemName, Qty from titems02 where iid = ? and uid = ?";
		try {
			PreparedStatement pst = c.prepareStatement(sql);
			pst.setInt(1, iid);
			pst.setInt(2, uid);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				item.setName(rs.getString(1));
				item.setQty(rs.getInt(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}
	
	public int changeItem(int iid, int uid, String itemName, String quantity) {
		int res=0;
		int qty=0;
		if (itemName == null || itemName.trim().equals("")) {
			return 1;
		}
		try {
			qty = Integer.parseInt(quantity);
		} catch (Exception e) {
			return 2;
		}
		String sql = "update titems02 set ItemName = ?, Qty = ? where iid = ? and uid = ?";
		try {
			PreparedStatement pst = c.prepareStatement(sql);
			pst.setString(1, itemName);
			pst.setInt(2, qty);
			pst.setInt(3, iid);
			pst.setInt(4, uid);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public int addItem(String iname, String iqty, Integer uid) {
		int res = 0;
		// 0 - OK - item was inserted
		// 1 - item name was not given
		// 2 - item qty was either not given or not a valid int
		int qty = 0;
		if (iname == null || iname.trim().equals(""))
			return 1;
		try {
			qty = Integer.parseInt(iqty);
		} catch (Exception e) {
			return 2;
		}

		String sql = "insert into titems02 (ItemName, Qty, uid) values (?, ?, ?)";
		try {
			pst = c.prepareStatement(sql);
			pst.setString(1, iname);
			pst.setInt(2, qty);
			pst.setInt(3, uid);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public static void main(String[] args) {
		DB_Access db = new DB_Access();
		System.out.println("uid: " + db.validateLogin("user01", "pass01"));

	}

	public int deleteAccount(User u) {
		int res = 0;
		String sql = "delete from tuser02 where uid=?";
		// int qty = 0;

		try {
			pst = c.prepareStatement(sql);
			pst.setInt(1, u.getUid());
			// pst.setInt(2, uid);
			pst.executeUpdate();// because we need to execute the query string
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return res;
	}

}
