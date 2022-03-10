
package ru.demskv.webapplicationproject.assignment;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import ru.demskv.webapplicationproject.employee.Employee;


@Entity
@Table(name = "assignment")
@NamedQueries({
    @NamedQuery(name = "Assignment.countAll", query = "SELECT count(a) FROM Assignment a"),
    @NamedQuery(name = "Assignment.deleteById", query = "DELETE FROM Assignment WHERE id=:id")})
public class Assignment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "topic")
    private String topic;   
    @Column(name = "executeby")
    @Temporal(TemporalType.TIMESTAMP)
    private Date executeby;
    @Column(name = "controlattr")
    private Integer controlattr;
    @Column(name = "executeattr")
    private Integer executeattr;
    @Lob
    @Column(name = "text")
    private String text;
    /*
    @JoinTable(name = "assignment_employee_executor", joinColumns = {
        @JoinColumn(name = "assignment_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "employee_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Employee> executors;
    @JoinColumn(name = "author_id", referencedColumnName = "id",  insertable=false, updatable=false)
    @ManyToOne(optional = false)
    private Employee author;
    */
    @NotNull
    @Column(name = "author_id")
    private Integer author_id;

    public Assignment() {
    }

    public Assignment(Integer id) {
        this.id = id;
    }

    public Assignment(Integer id, String topic) {
        this.id = id;
        this.topic = topic;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getExecuteby() {
        return executeby;
    }

    public void setExecuteby(Date executeby) {
        this.executeby = executeby;
    }

    public Integer getControlattr() {
        return controlattr;
    }

    public void setControlattr(Integer controlattr) {
        this.controlattr = controlattr;
    }

    public Integer getExecuteattr() {
        return executeattr;
    }

    public void setExecuteattr(Integer executeattr) {
        this.executeattr = executeattr;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
/*
    public Collection<Employee> getExecutors() {
        return executors;
    }

    public void setExecutors(Collection<Employee> employeeCollection) {
        this.executors = employeeCollection;
    }

    public Employee getAuthor() {
        return author;
    }

    public void setAuthor(Employee author) {
        this.author = author;
    }
*/
    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
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
        if (!(object instanceof Assignment)) {
            return false;
        }
        Assignment other = (Assignment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.demskv.webapplicationproject.assignment.Assignment[ id=" + id + " ]";
    }

}
