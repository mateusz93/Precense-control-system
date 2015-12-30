/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mateusz
 */
@Entity
@Table(name = "StudentCourses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentCourses.findAll", query = "SELECT s FROM StudentCourses s"),
    @NamedQuery(name = "StudentCourses.findById", query = "SELECT s FROM StudentCourses s WHERE s.id = :id"),
    @NamedQuery(name = "StudentCourses.findByTeacherCourseID", query = "SELECT s FROM StudentCourses s WHERE s.teacherCourseID = :teacherCourseID"),
    @NamedQuery(name = "StudentCourses.findBySaveTime", query = "SELECT s FROM StudentCourses s WHERE s.saveTime = :saveTime")})
public class StudentCourses implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "teacherCourseID")
    private Integer teacherCourseID;
    @Column(name = "saveTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date saveTime;
    @JoinColumn(name = "studentID", referencedColumnName = "ID")
    @ManyToOne
    private Users studentID;

    public StudentCourses() {
    }

    public StudentCourses(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeacherCourseID() {
        return teacherCourseID;
    }

    public void setTeacherCourseID(Integer teacherCourseID) {
        this.teacherCourseID = teacherCourseID;
    }

    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }

    public Users getStudentID() {
        return studentID;
    }

    public void setStudentID(Users studentID) {
        this.studentID = studentID;
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
        if (!(object instanceof StudentCourses)) {
            return false;
        }
        StudentCourses other = (StudentCourses) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.StudentCourses[ id=" + id + " ]";
    }
    
}
