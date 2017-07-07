package com.devprofiler.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Updates implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)    
    private Date createdOn = new Date();
    @Lob
    private String updateTitle, updateText;
    private int timesLiked, timesFavorited;
    
//    private List<Long> likedBy, favoritedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdateText() {
        return updateText;
    }

    public void setUpdateText(String updateText) {
        this.updateText = updateText;
    }

    public String getUpdateTitle() {
        return updateTitle;
    }

    public void setUpdateTitle(String updateTitle) {
        this.updateTitle = updateTitle;
    }

    public int getTimesLiked() {
        return timesLiked;
    }

    public void setTimesLiked(int timesLiked) {
        this.timesLiked = timesLiked;
    }

    public int getTimesFavorited() {
        return timesFavorited;
    }

    public void setTimesFavorited(int timesFavorited) {
        this.timesFavorited = timesFavorited;
    }

  
  
    public String getTimeAgo() {
        if (createdOn == null) {
            return "-";
        }
        Calendar postedTime = Calendar.getInstance();
        postedTime.setTime(createdOn);
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());

        long difference = today.getTimeInMillis() - postedTime.getTimeInMillis();
        int days = (int) Math.floor(difference / 1000 / 3600 / 24);
        int hours = (int) Math.floor(difference / 1000 / 3600);
        int mins = (int) Math.floor(difference / 1000 / 60 );
        if (days > 1) {
            return days + "d";
        } else if (hours > 1) {
            return hours + "h";
        } else if (mins > 1) {
            return mins + "m";
        }

        return difference / 1000 + "s";
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
        if (!(object instanceof Updates)) {
            return false;
        }
        Updates other = (Updates) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.devprofiler.entities.Updates[ id=" + id + " ]";
    }

}
