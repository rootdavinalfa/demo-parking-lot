package com.example.demo_parking.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.envers.Audited
import org.hibernate.envers.RelationTargetAuditMode
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

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
    var id: Long = 0

    @Column(name = "REGISTRATION_NO")
    var registrationNo: String = ""

    @Column(name = "ARRIVAL")
    var arrival: LocalDateTime? = null

    @Column(name = "DEPARTURE")
    var departure: LocalDateTime? = null

    @Column(name = "CONF_ID")
    var confId: Long? = null

    @Transient
    var totalBill: BigDecimal = BigDecimal.ZERO

    @ManyToOne(cascade = [(CascadeType.ALL)])
    @JoinColumn(
        name = "CONF_ID",
        insertable = false,
        updatable = false,
        referencedColumnName = "ID"
    )
    @JsonIgnore
    var parkingConf: ParkingConf? = null

    @PostLoad
    fun postLoad() {
        val now = LocalDateTime.now()
        val firstHourRate = parkingConf?.firstHourRate ?: BigDecimal.ZERO
        val incrementalHourRate = parkingConf?.incrementalHourRate ?: BigDecimal.ZERO
        val arrival = this.arrival ?: now
        val departure = this.departure ?: now

        val elapsedParkingHours = arrival.until(departure, ChronoUnit.HOURS)

        if (elapsedParkingHours == 0L || elapsedParkingHours == 1L) {
            this.totalBill = firstHourRate.multiply(elapsedParkingHours.toBigDecimal())
        } else if (elapsedParkingHours > 1L) {
            this.totalBill = firstHourRate.add(
                incrementalHourRate
            ).multiply(
                elapsedParkingHours.toBigDecimal()
            )
        }

    }

}