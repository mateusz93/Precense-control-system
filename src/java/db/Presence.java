/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mateusz
 */
@Entity
@Table(name = "Presence")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Presence.findAll", query = "SELECT p FROM Presence p"),
    @NamedQuery(name = "Presence.findById", query = "SELECT p FROM Presence p WHERE p.id = :id"),
    @NamedQuery(name = "Presence.findByStatus", query = "SELECT p FROM Presence p WHERE p.status = :status")})
public class Presence implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Status")
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "presence")
    private Collection<PresenceUsers> presenceUsersCollection;
    @OneToMany(mappedBy = "presenceID")
    private Collection<PresenceCourseDates> presenceCourseDatesCollection;

    public Presence() {
    }

    public Presence(Integer id) {
        this.id = id;
    }

    public Presence(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<PresenceUsers> getPresenceUsersCollection() {
        return presenceUsersCollection;
    }

    public void setPresenceUsersCollection(Collection<PresenceUsers> presenceUsersCollection) {
        this.presenceUsersCollection = presenceUsersCollection;
    }

    @XmlTransient
    public Collection<PresenceCourseDates> getPresenceCourseDatesCollection() {
        return presenceCourseDatesCollection;
    }

    public void setPresenceCourseDatesCollection(Collection<PresenceCourseDates> presenceCourseDatesCollection) {
        this.presenceCourseDatesCollection = presenceCourseDatesCollection;
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
        if (!(object instanceof Presence)) {
            return false;
        }
        Presence other = (Presence) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Presence[ id=" + id + " ]";
    }
    
}
