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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Courses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Courses.findAll", query = "SELECT c FROM Courses c"),
    @NamedQuery(name = "Courses.findById", query = "SELECT c FROM Courses c WHERE c.id = :id"),
    @NamedQuery(name = "Courses.findByType", query = "SELECT c FROM Courses c WHERE c.type = :type"),
    @NamedQuery(name = "Courses.findByCoursesQuantity", query = "SELECT c FROM Courses c WHERE c.coursesQuantity = :coursesQuantity"),
    @NamedQuery(name = "Courses.findByMinPresence", query = "SELECT c FROM Courses c WHERE c.minPresence = :minPresence")})
public class Courses implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "type")
    private String type;
    @Column(name = "coursesQuantity")
    private Integer coursesQuantity;
    @Column(name = "minPresence")
    private Integer minPresence;
    @OneToMany(mappedBy = "courseID")
    private Collection<UsersSubjects> usersSubjectsCollection;
    @JoinColumn(name = "subjectID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Subjects subjectID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseID")
    private Collection<CourseDates> courseDatesCollection;
    @OneToMany(mappedBy = "courseID")
    private Collection<UsersCourses> usersCoursesCollection;

    public Courses() {
    }

    public Courses(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @XmlTransient
    public Collection<UsersSubjects> getUsersSubjectsCollection() {
        return usersSubjectsCollection;
    }

    public void setUsersSubjectsCollection(Collection<UsersSubjects> usersSubjectsCollection) {
        this.usersSubjectsCollection = usersSubjectsCollection;
    }

    public Subjects getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Subjects subjectID) {
        this.subjectID = subjectID;
    }

    @XmlTransient
    public Collection<CourseDates> getCourseDatesCollection() {
        return courseDatesCollection;
    }

    public void setCourseDatesCollection(Collection<CourseDates> courseDatesCollection) {
        this.courseDatesCollection = courseDatesCollection;
    }

    @XmlTransient
    public Collection<UsersCourses> getUsersCoursesCollection() {
        return usersCoursesCollection;
    }

    public void setUsersCoursesCollection(Collection<UsersCourses> usersCoursesCollection) {
        this.usersCoursesCollection = usersCoursesCollection;
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
        if (!(object instanceof Courses)) {
            return false;
        }
        Courses other = (Courses) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Courses[ id=" + id + " ]";
    }
    
}
