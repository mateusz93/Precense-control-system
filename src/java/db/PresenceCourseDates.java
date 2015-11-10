/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "Presence_CourseDates")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PresenceCourseDates.findAll", query = "SELECT p FROM PresenceCourseDates p"),
    @NamedQuery(name = "PresenceCourseDates.findById", query = "SELECT p FROM PresenceCourseDates p WHERE p.id = :id")})
public class PresenceCourseDates implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "presenceID", referencedColumnName = "ID")
    @ManyToOne
    private Presence presenceID;
    @JoinColumn(name = "courseDatesID", referencedColumnName = "ID")
    @ManyToOne
    private CourseDates courseDatesID;

    public PresenceCourseDates() {
    }

    public PresenceCourseDates(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Presence getPresenceID() {
        return presenceID;
    }

    public void setPresenceID(Presence presenceID) {
        this.presenceID = presenceID;
    }

    public CourseDates getCourseDatesID() {
        return courseDatesID;
    }

    public void setCourseDatesID(CourseDates courseDatesID) {
        this.courseDatesID = courseDatesID;
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
        if (!(object instanceof PresenceCourseDates)) {
            return false;
        }
        PresenceCourseDates other = (PresenceCourseDates) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.PresenceCourseDates[ id=" + id + " ]";
    }
    
}
