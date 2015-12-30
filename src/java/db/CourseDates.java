/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mateusz
 */
@Entity
@Table(name = "CourseDates")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CourseDates.findAll", query = "SELECT c FROM CourseDates c"),
    @NamedQuery(name = "CourseDates.findById", query = "SELECT c FROM CourseDates c WHERE c.id = :id"),
    @NamedQuery(name = "CourseDates.findByStartTime", query = "SELECT c FROM CourseDates c WHERE c.startTime = :startTime"),
    @NamedQuery(name = "CourseDates.findByFinishTime", query = "SELECT c FROM CourseDates c WHERE c.finishTime = :finishTime"),
    @NamedQuery(name = "CourseDates.findByDate", query = "SELECT c FROM CourseDates c WHERE c.date = :date")})
public class CourseDates implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "startTime")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Column(name = "finishTime")
    @Temporal(TemporalType.TIME)
    private Date finishTime;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseDateID")
    private Collection<StudentPrecenses> studentPrecensesCollection;
    @JoinColumn(name = "courseID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TeacherCourses courseID;

    public CourseDates() {
    }

    public CourseDates(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @XmlTransient
    public Collection<StudentPrecenses> getStudentPrecensesCollection() {
        return studentPrecensesCollection;
    }

    public void setStudentPrecensesCollection(Collection<StudentPrecenses> studentPrecensesCollection) {
        this.studentPrecensesCollection = studentPrecensesCollection;
    }

    public TeacherCourses getCourseID() {
        return courseID;
    }

    public void setCourseID(TeacherCourses courseID) {
        this.courseID = courseID;
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
        if (!(object instanceof CourseDates)) {
            return false;
        }
        CourseDates other = (CourseDates) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.CourseDates[ id=" + id + " ]";
    }
    
}
