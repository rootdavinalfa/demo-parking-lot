package com.example.demo_parking.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
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
    @NotNull(message = "ID cannot be null")
    var id: Long = 0

    @Column(name = "NAME", length = 255, nullable = false)
    @Size(max = 20, message = "NAME only have maximum {max}")
    @NotNull(message = "NAME must not be null")
    var name: String? = null

    @Digits(integer = 8, fraction = 2, message = "FIRST_HOUR_RATE must {integer},{fraction}")
    @Column(name = "FIRST_HOUR_RATE", precision = 10, scale = 2)
    var firstHourRate: BigDecimal = BigDecimal.ZERO

    @Digits(integer = 8, fraction = 2, message = "INCREMENTAL_HOUR_RATE must {integer},{fraction}")
    @Column(name = "INCREMENTAL_HOUR_RATE", precision = 10, scale = 2)
    var incrementalHourRate: BigDecimal = BigDecimal.ZERO

    @Column(name = "CAPACITY")
    var capacity: Long = 0

}