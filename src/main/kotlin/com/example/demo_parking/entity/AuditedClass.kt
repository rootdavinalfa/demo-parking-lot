package com.example.demo_parking.entity

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.MappedSuperclass
import org.hibernate.envers.Audited
import org.hibernate.envers.RelationTargetAuditMode
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import java.time.LocalDateTime

@MappedSuperclass
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
open class AuditedClass {
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.[SSS]")
    var createdDate: LocalDateTime? = null

    @LastModifiedBy
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.[SSS]")
    var modifiedDate: LocalDateTime? = null

}