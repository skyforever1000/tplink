package phonebook.TPDB;

import java.util.ArrayList;
import java.util.Date;

public class UserGroup {
	private int _groupId =-1;
	private String _groupName = "";
	private ArrayList<User> users = new ArrayList<User>();
	private Date _versionId = null;
	
	public int get_groupId() {
		return _groupId;
	}
	public void set_groupId(int _groupId) {
		this._groupId = _groupId;
	}
	public String get_groupName() {
		return _groupName;
	}
	public void set_groupName(String _groupName) {
		this._groupName = _groupName;
	}
	public ArrayList<User> getUsers() {
		return users;
	}
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	public Date get_versionId() {
		return _versionId;
	}
	public void set_versionId(Date _versionId) {
		this._versionId = _versionId;
	}
}