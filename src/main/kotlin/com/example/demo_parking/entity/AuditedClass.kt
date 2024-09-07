package com.example.demo_parking.entity

import org.hibernate.envers.Audited
import org.hibernate.envers.RelationTargetAuditMode
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import java.time.LocalDateTime

@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
open class AuditedClass {
    @CreatedDate
    var createdDate : LocalDateTime = LocalDateTime.now()

    @LastModifiedBy
    var modifiedDate : LocalDateTime = LocalDateTime.now()
}