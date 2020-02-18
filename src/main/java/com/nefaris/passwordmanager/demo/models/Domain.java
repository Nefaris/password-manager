package com.nefaris.passwordmanager.demo.models;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Domain {

    @Id
    String id;
    String domainAddress;
    String username;
    String password;

    public Domain() {
    }

    public Domain(String domainAddress, String username, String password) {
        this.domainAddress = domainAddress;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDomainAddress() {
        return domainAddress;
    }

    public void setDomainAddress(String domainAddress) {
        this.domainAddress = domainAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Domain{" +
                "id='" + id + '\'' +
                ", domainAddress='" + domainAddress + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Domain)) return false;
        Domain domain = (Domain) o;
        return Objects.equals(id, domain.id) &&
                Objects.equals(domainAddress, domain.domainAddress) &&
                Objects.equals(username, domain.username) &&
                Objects.equals(password, domain.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, domainAddress, username, password);
    }
}
