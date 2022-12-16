package com.aleksandar.pollme;

public class User {
    private String ime;
    private String prezime;
    private Integer password;

    public User() {}

    public User(String ime, String prezime, String password) {
        this.ime = ime;
        this.prezime = prezime;
        this.password = password.hashCode();
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) { this.password = password; }
}
