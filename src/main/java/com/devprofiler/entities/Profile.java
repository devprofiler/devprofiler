package com.devprofiler.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 *
 * @author PRanjan3
 */
@Entity
@NamedQueries({ //    @NamedQuery(name="Updates.Dsc",query="SELECT p FROM Profile as p where p.id = :id order by p.updates.id desc")
})
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName, lastName, fullName, email, location;
    
    
    private String about;
    @Lob
    private String overview;
    @Lob
    private String technologies;
    @Lob
    private String education;
    @Lob
    private String employment;
    @Lob
    private String experience;
    @Lob
    private String personnelProjects;
    @OneToMany(cascade = CascadeType.PERSIST)    
    @OrderBy("id DESC")
    private List<Updates> updates;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Skill> skills;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        if (email != null && !email.isEmpty()) {
            return email.replace("@", " at ").replace(".", " dot ");
        };
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPersonnelProjects() {
        return personnelProjects;
    }

    public void setPersonnelProjects(String personnelProjects) {
        this.personnelProjects = personnelProjects;
    }

    public List<Updates> getUpdates() {
        return updates;
    }

    public void setUpdates(List<Updates> updates) {
        this.updates = updates;
    }

    public void add(Updates update) {
        if (this.updates == null) {
            this.updates = new ArrayList<Updates>();
        }
        this.updates.add(update);
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
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
        if (!(object instanceof Profile)) {
            return false;
        }
        Profile other = (Profile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Profile{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", overview=" + overview + ", technologies=" + technologies + ", education=" + education + ", employment=" + employment + ", experience=" + experience + ", personnelProjects=" + personnelProjects + ", updates=" + updates + '}';
    }

}
