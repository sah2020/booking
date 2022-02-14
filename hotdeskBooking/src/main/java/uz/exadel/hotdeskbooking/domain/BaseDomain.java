package uz.exadel.hotdeskbooking.domain;


import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseDomain implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    @NotNull
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private Date createdDate;

    @NotNull
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private Date modifiedDate;

    @PreUpdate
    public void preUpdate() {
        this.setModifiedDate(new Date(System.currentTimeMillis()));
    }

    @PrePersist
    public void prePersist() {
        if (this.isNew() && this.getCreatedDate() == null) {
            this.setCreatedDate(new Date(System.currentTimeMillis()));
        }
        this.setModifiedDate(new Date(System.currentTimeMillis()));
    }

    public Date getCreatedDate() {
        return createdDate != null ? new Date(createdDate.getTime()) : null;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate != null ? new Date(createdDate.getTime()) : null;
    }

    public Date getModifiedDate() {
        return modifiedDate != null ? new Date(modifiedDate.getTime()) : null;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate != null ? new Date(modifiedDate.getTime()) : null;
    }

    public boolean isNew() {
        return this.id == null;
    }
}