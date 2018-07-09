package com.example.ngz.pettrackapplication;

public class Database_Register {

    public String _username;
    public String _Email;
    public String _password;
    public String _Telephone;
    public String _Uid;
    public String _status;

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_Email() {
        return _Email;
    }

    public void set_Email(String _Email) {
        this._Email = _Email;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_Telephone() {
        return _Telephone;
    }

    public void set_Telephone(String _Telephone) {
        this._Telephone = _Telephone;
    }

    public String get_Uid() {
        return _Uid;
    }

    public void set_Uid(String _Uid) {
        this._Uid = _Uid;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public Database_Register(String _username, String _Email, String _password , String _Telephone, String _Uid,String _status){
        this._username = _username;
        this._Email = _Email;
        this._password = _password;
        this._Telephone = _Telephone;
        this._Uid = _Uid;
        this._status = _status;
    }

    public Database_Register(){}


}
