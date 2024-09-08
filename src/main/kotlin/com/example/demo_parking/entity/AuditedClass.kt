package com.example.demo_parking.entity

import com.fasterxml.jackson.annotation.JsonFormat
import org.hibernate.envers.Audited
import org.hibernate.envers.RelationTargetAuditMode
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import java.time.LocalDateTime

@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
open class AuditedClass {
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createdDate: LocalDateTime? = null

    @LastModifiedBy
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var modifiedDate: LocalDateTime? = null
}