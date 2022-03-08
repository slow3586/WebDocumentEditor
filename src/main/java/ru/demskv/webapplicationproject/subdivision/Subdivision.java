
package ru.demskv.webapplicationproject.subdivision;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import ru.demskv.webapplicationproject.employee.Employee;


@Entity
@Table(name = "subdivision")
@NamedQueries({
    @NamedQuery(name = "Subdivision.findAll", query = "SELECT s FROM Subdivision s"),
    @NamedQuery(name = "Subdivision.findById", query = "SELECT s FROM Subdivision s WHERE s.id = :id"),
    @NamedQuery(name = "Subdivision.findByName", query = "SELECT s FROM Subdivision s WHERE s.name = :name"),
    @NamedQuery(name = "Subdivision.findByAddress", query = "SELECT s FROM Subdivision s WHERE s.address = :address")})
public class Subdivision implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "address")
    private String address;
    @JoinColumn(name = "director_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Employee directorId;

    public Subdivision() {
    }

    public Subdivision(Integer id) {
        this.id = id;
    }

    public Subdivision(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Employee getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Employee directorId) {
        this.directorId = directorId;
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
        if (!(object instanceof Subdivision)) {
            return false;
        }
        Subdivision other = (Subdivision) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.demskv.webapplicationproject.subdivision.Subdivision[ id=" + id + " ]";
    }

}
