package com.example.demo_parking.entity

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.envers.Audited
import org.hibernate.envers.RelationTargetAuditMode
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(
    name = "OCCUPIED_PARKING",
    indexes = [
        Index(columnList = "id", unique = true),
        Index(columnList = "REGISTRATION_NO,ARRIVAL", unique = true),
    ]
)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@EntityListeners(AuditingEntityListener::class)
class OccupiedParking : AuditedClass() {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull(message = "ID cannot be null")
    var id: Long = 0

    @Column(name = "REGISTRATION_NO", length = 255)
    @Size(max = 255, message = "REGISTRATION_NO only have maximum {max}")
    @NotNull(message = "REGISTRATION_NO is required")
    var registrationNo: String = ""

    @Column(name = "ARRIVAL")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var arrival: LocalDateTime? = null

    @Column(name = "LEAVE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var leave: LocalDateTime? = null

    @Column(name = "CONF_ID", nullable = false)
    @NotNull(message = "CONF_ID is required")
    var confId: Long? = null

    @Digits(integer = 8, fraction = 2, message = "FIRST_HOUR_RATE must {integer},{fraction}")
    @Column(name = "FIRST_HOUR_RATE", precision = 10, scale = 2)
    var firstHourRate: BigDecimal = BigDecimal.ZERO

    @Digits(integer = 8, fraction = 2, message = "INCREMENTAL_HOUR_RATE must {integer},{fraction}")
    @Column(name = "INCREMENTAL_HOUR_RATE", precision = 10, scale = 2)
    var incrementalHourRate: BigDecimal = BigDecimal.ZERO

    @Digits(integer = 8, fraction = 2, message = "TOTAL_BILL must {integer},{fraction}")
    @Column(name = "TOTAL_BILL", precision = 10, scale = 2)
    var totalBill: BigDecimal = BigDecimal.ZERO


}