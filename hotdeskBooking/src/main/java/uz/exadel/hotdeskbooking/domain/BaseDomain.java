package uz.exadel.hotdeskbooking.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public abstract class BaseDomain implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @JsonIgnore
    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @JsonIgnore
    @UpdateTimestamp
    private Timestamp updatedAt;

    @JsonIgnore
    @CreatedBy
    @Column(updatable = false)
    private String createdById;

    @JsonIgnore
    @LastModifiedBy
    private String updateById;
}