/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boskitakita.permadi.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hello Permadi
 */
@Entity
@Table(name = "userlogin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userlogin.findAll", query = "SELECT u FROM Userlogin u")
    , @NamedQuery(name = "Userlogin.findById", query = "SELECT u FROM Userlogin u WHERE u.id = :id")
    , @NamedQuery(name = "Userlogin.findByName", query = "SELECT u FROM Userlogin u WHERE u.name = :name")
    , @NamedQuery(name = "Userlogin.findByUsername", query = "SELECT u FROM Userlogin u WHERE u.username = :username")
    , @NamedQuery(name = "Userlogin.findByPassword", query = "SELECT u FROM Userlogin u WHERE u.password = :password")
    , @NamedQuery(name = "Userlogin.findByRole", query = "SELECT u FROM Userlogin u WHERE u.role = :role")
    , @NamedQuery(name = "Userlogin.findByOtherone", query = "SELECT u FROM Userlogin u WHERE u.otherone = :otherone")
    , @NamedQuery(name = "Userlogin.findByOthertwo", query = "SELECT u FROM Userlogin u WHERE u.othertwo = :othertwo")
    , @NamedQuery(name = "Userlogin.findByOtherthree", query = "SELECT u FROM Userlogin u WHERE u.otherthree = :otherthree")
    , @NamedQuery(name = "Userlogin.findByCreatedAt", query = "SELECT u FROM Userlogin u WHERE u.createdAt = :createdAt")})
public class Userlogin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "role")
    private int role;
    @Basic(optional = false)
    @Column(name = "otherone")
    private String otherone;
    @Basic(optional = false)
    @Column(name = "othertwo")
    private String othertwo;
    @Basic(optional = false)
    @Column(name = "otherthree")
    private String otherthree;
    @Basic(optional = false)
    @Column(name = "created_at")
    private String createdAt;

    public Userlogin() {
    }

    public Userlogin(Integer id) {
        this.id = id;
    }

    public Userlogin(Integer id, int role, String otherone, String othertwo, String otherthree, String createdAt) {
        this.id = id;
        this.role = role;
        this.otherone = otherone;
        this.othertwo = othertwo;
        this.otherthree = otherthree;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getOtherone() {
        return otherone;
    }

    public void setOtherone(String otherone) {
        this.otherone = otherone;
    }

    public String getOthertwo() {
        return othertwo;
    }

    public void setOthertwo(String othertwo) {
        this.othertwo = othertwo;
    }

    public String getOtherthree() {
        return otherthree;
    }

    public void setOtherthree(String otherthree) {
        this.otherthree = otherthree;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userlogin)) {
            return false;
        }
        Userlogin other = (Userlogin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "boskitakita.permadi.entity.Userlogin[ id=" + id + " ]";
    }
    
}
