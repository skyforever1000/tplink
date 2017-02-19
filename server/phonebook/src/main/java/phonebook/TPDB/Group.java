package phonebook.TPDB;

import java.util.Date;

public class Group {
	private int _groupId = -1;
	private String _groupName = "";
	private int _status = -1;
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
	public int get_status() {
		return _status;
	}
	public void set_status(int _status) {
		this._status = _status;
	}
	public Date get_versionId() {
		return _versionId;
	}
	public void set_versionId(Date _versionId) {
		this._versionId = _versionId;
	}	
}

