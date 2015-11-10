/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author mateusz
 */
@Embeddable
public class PresenceUsersPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID")
    private int id;
    @Basic(optional = false)
    @Column(name = "studentID")
    private int studentID;
    @Basic(optional = false)
    @Column(name = "presenceID")
    private int presenceID;

    public PresenceUsersPK() {
    }

    public PresenceUsersPK(int id, int studentID, int presenceID) {
        this.id = id;
        this.studentID = studentID;
        this.presenceID = presenceID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getPresenceID() {
        return presenceID;
    }

    public void setPresenceID(int presenceID) {
        this.presenceID = presenceID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) studentID;
        hash += (int) presenceID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresenceUsersPK)) {
            return false;
        }
        PresenceUsersPK other = (PresenceUsersPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.studentID != other.studentID) {
            return false;
        }
        if (this.presenceID != other.presenceID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.PresenceUsersPK[ id=" + id + ", studentID=" + studentID + ", presenceID=" + presenceID + " ]";
    }
    
}
