package phonebook.TPDB;

import java.util.Date;

public class User {
	private int _userId = -1;
	private String _phoneNo = "";
	private String _firstName = "";
	private String _lastName = "";
	private String _address = "";
	private int _status = -1;
	private int _groupId = -1;
	private Date _versionId = null;

	public int get_userId() {
		return _userId;
	}
	public void set_userId(int _userId) {
		this._userId = _userId;
	}
	public String get_phoneNo() {
		return _phoneNo;
	}
	public void set_phoneNo(String _phoneNo) {
		this._phoneNo = _phoneNo;
	}
	public String get_firstName() {
		return _firstName;
	}
	public void set_firstName(String _firstName) {
		this._firstName = _firstName;
	}
	public String get_lastName() {
		return _lastName;
	}
	public void set_lastName(String _lastName) {
		this._lastName = _lastName;
	}
	public String get_address() {
		return _address;
	}
	public void set_address(String _address) {
		this._address = _address;
	}
	public int get_groupId() {
		return _groupId;
	}
	public void set_groupId(int _groupId) {
		this._groupId = _groupId;
	}
	public Date get_versionId() {
		return _versionId;
	}
	public void set_versionId(Date _versionId) {
		this._versionId = _versionId;
	}
	public int get_status() {
		return _status;
	}
	public void set_status(int _status) {
		this._status = _status;
	}
}

