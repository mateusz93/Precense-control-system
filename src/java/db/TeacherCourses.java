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
@Table(name = "TeacherCourses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TeacherCourses.findAll", query = "SELECT t FROM TeacherCourses t"),
    @NamedQuery(name = "TeacherCourses.findById", query = "SELECT t FROM TeacherCourses t WHERE t.id = :id"),
    @NamedQuery(name = "TeacherCourses.findBySubjectID", query = "SELECT t FROM TeacherCourses t WHERE t.subjectID = :subjectID"),
    @NamedQuery(name = "TeacherCourses.findByTeacherID", query = "SELECT t FROM TeacherCourses t WHERE t.teacherID = :teacherID"),
    @NamedQuery(name = "TeacherCourses.findByType", query = "SELECT t FROM TeacherCourses t WHERE t.type = :type"),
    @NamedQuery(name = "TeacherCourses.findByCoursesQuantity", query = "SELECT t FROM TeacherCourses t WHERE t.coursesQuantity = :coursesQuantity"),
    @NamedQuery(name = "TeacherCourses.findByMinPresence", query = "SELECT t FROM TeacherCourses t WHERE t.minPresence = :minPresence"),
    @NamedQuery(name = "TeacherCourses.findByDescription", query = "SELECT t FROM TeacherCourses t WHERE t.description = :description")})
public class TeacherCourses implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "subjectID")
    private int subjectID;
    @Column(name = "teacherID")
    private Integer teacherID;
    @Column(name = "type")
    private String type;
    @Column(name = "coursesQuantity")
    private Integer coursesQuantity;
    @Column(name = "minPresence")
    private Integer minPresence;
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseID")
    private Collection<CourseDates> courseDatesCollection;

    public TeacherCourses() {
    }

    public TeacherCourses(Integer id) {
        this.id = id;
    }

    public TeacherCourses(Integer id, int subjectID) {
        this.id = id;
        this.subjectID = subjectID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public Integer getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(Integer teacherID) {
        this.teacherID = teacherID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCoursesQuantity() {
        return coursesQuantity;
    }

    public void setCoursesQuantity(Integer coursesQuantity) {
        this.coursesQuantity = coursesQuantity;
    }

    public Integer getMinPresence() {
        return minPresence;
    }

    public void setMinPresence(Integer minPresence) {
        this.minPresence = minPresence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<CourseDates> getCourseDatesCollection() {
        return courseDatesCollection;
    }

    public void setCourseDatesCollection(Collection<CourseDates> courseDatesCollection) {
        this.courseDatesCollection = courseDatesCollection;
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
        if (!(object instanceof TeacherCourses)) {
            return false;
        }
        TeacherCourses other = (TeacherCourses) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.TeacherCourses[ id=" + id + " ]";
    }
    
}
