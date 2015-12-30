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
@Table(name = "StudentPrecenses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentPrecenses.findAll", query = "SELECT s FROM StudentPrecenses s"),
    @NamedQuery(name = "StudentPrecenses.findById", query = "SELECT s FROM StudentPrecenses s WHERE s.id = :id"),
    @NamedQuery(name = "StudentPrecenses.findByStatus", query = "SELECT s FROM StudentPrecenses s WHERE s.status = :status")})
public class StudentPrecenses implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "studentID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Users studentID;
    @JoinColumn(name = "courseDateID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private CourseDates courseDateID;

    public StudentPrecenses() {
    }

    public StudentPrecenses(Integer id) {
        this.id = id;
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

    public Users getStudentID() {
        return studentID;
    }

    public void setStudentID(Users studentID) {
        this.studentID = studentID;
    }

    public CourseDates getCourseDateID() {
        return courseDateID;
    }

    public void setCourseDateID(CourseDates courseDateID) {
        this.courseDateID = courseDateID;
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
        if (!(object instanceof StudentPrecenses)) {
            return false;
        }
        StudentPrecenses other = (StudentPrecenses) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.StudentPrecenses[ id=" + id + " ]";
    }
    
}
