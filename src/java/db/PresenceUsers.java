/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mateusz
 */
@Entity
@Table(name = "Presence_Users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PresenceUsers.findAll", query = "SELECT p FROM PresenceUsers p"),
    @NamedQuery(name = "PresenceUsers.findById", query = "SELECT p FROM PresenceUsers p WHERE p.presenceUsersPK.id = :id"),
    @NamedQuery(name = "PresenceUsers.findByStudentID", query = "SELECT p FROM PresenceUsers p WHERE p.presenceUsersPK.studentID = :studentID"),
    @NamedQuery(name = "PresenceUsers.findByPresenceID", query = "SELECT p FROM PresenceUsers p WHERE p.presenceUsersPK.presenceID = :presenceID")})
public class PresenceUsers implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PresenceUsersPK presenceUsersPK;
    @JoinColumn(name = "studentID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;
    @JoinColumn(name = "presenceID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Presence presence;

    public PresenceUsers() {
    }

    public PresenceUsers(PresenceUsersPK presenceUsersPK) {
        this.presenceUsersPK = presenceUsersPK;
    }

    public PresenceUsers(int id, int studentID, int presenceID) {
        this.presenceUsersPK = new PresenceUsersPK(id, studentID, presenceID);
    }

    public PresenceUsersPK getPresenceUsersPK() {
        return presenceUsersPK;
    }

    public void setPresenceUsersPK(PresenceUsersPK presenceUsersPK) {
        this.presenceUsersPK = presenceUsersPK;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Presence getPresence() {
        return presence;
    }

    public void setPresence(Presence presence) {
        this.presence = presence;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (presenceUsersPK != null ? presenceUsersPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresenceUsers)) {
            return false;
        }
        PresenceUsers other = (PresenceUsers) object;
        if ((this.presenceUsersPK == null && other.presenceUsersPK != null) || (this.presenceUsersPK != null && !this.presenceUsersPK.equals(other.presenceUsersPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.PresenceUsers[ presenceUsersPK=" + presenceUsersPK + " ]";
    }
    
}
