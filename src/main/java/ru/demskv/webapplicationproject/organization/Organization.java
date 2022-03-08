
package ru.demskv.webapplicationproject.organization;

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
@Table(name = "organization")
@NamedQueries({
    @NamedQuery(name = "Organization.findAll", query = "SELECT o FROM Organization o"),
    @NamedQuery(name = "Organization.findById", query = "SELECT o FROM Organization o WHERE o.id = :id"),
    @NamedQuery(name = "Organization.findByName", query = "SELECT o FROM Organization o WHERE o.name = :name"),
    @NamedQuery(name = "Organization.findByPhysAddress", query = "SELECT o FROM Organization o WHERE o.physAddress = :physAddress"),
    @NamedQuery(name = "Organization.findByYurAddress", query = "SELECT o FROM Organization o WHERE o.yurAddress = :yurAddress")})
public class Organization implements Serializable {

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
    @Column(name = "phys_address")
    private String physAddress;
    @Basic(optional = false)
    @Column(name = "yur_address")
    private String yurAddress;
    @JoinColumn(name = "director_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Employee directorId;

    public Organization() {
    }

    public Organization(Integer id) {
        this.id = id;
    }

    public Organization(Integer id, String name, String physAddress, String yurAddress) {
        this.id = id;
        this.name = name;
        this.physAddress = physAddress;
        this.yurAddress = yurAddress;
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

    public String getPhysAddress() {
        return physAddress;
    }

    public void setPhysAddress(String physAddress) {
        this.physAddress = physAddress;
    }

    public String getYurAddress() {
        return yurAddress;
    }

    public void setYurAddress(String yurAddress) {
        this.yurAddress = yurAddress;
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
        if (!(object instanceof Organization)) {
            return false;
        }
        Organization other = (Organization) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.demskv.webapplicationproject.organization.Organization[ id=" + id + " ]";
    }

}
