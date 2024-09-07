package com.example.demo_parking.entity

import jakarta.persistence.*
import org.hibernate.envers.Audited
import org.hibernate.envers.RelationTargetAuditMode
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal

@Entity
@Table(name = "PARKING_CONFIG")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@EntityListeners(AuditingEntityListener::class)
class ParkingConf : AuditedClass() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 0

    @Column(name = "FIRST_HOUR_RATE")
    var firstHourRate: BigDecimal = BigDecimal.ZERO

    @Column(name = "INCREMENTAL_HOUR_RATE")
    var incrementalHourRate: BigDecimal = BigDecimal.ZERO

    @Column(name = "CAPACITY")
    var capacity: BigDecimal = BigDecimal.ZERO

}