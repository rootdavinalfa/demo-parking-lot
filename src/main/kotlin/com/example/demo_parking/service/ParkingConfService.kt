package com.example.demo_parking.service

import com.example.demo_parking.entity.ParkingConf
import com.example.demo_parking.error.ResourceExistsException
import com.example.demo_parking.error.ResourceNotFoundException
import com.example.demo_parking.repository.ParkingConfRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ParkingConfService(
    private var parkingConfRepository: ParkingConfRepository
) {

    @Transactional(rollbackFor = [ResourceExistsException::class])
    fun save(parkingConf: ParkingConf): ParkingConf {

        if (exists(parkingConf.id)) {
            throw ResourceExistsException("Data is exists.")
        }

        return parkingConfRepository.save(parkingConf)
    }

    @Transactional(rollbackFor = [ResourceNotFoundException::class])
    fun update(parkingConf: ParkingConf): ParkingConf {
        if (!exists(parkingConf.id)) {
            throw ResourceNotFoundException("Data not found.")
        }

        return parkingConfRepository.save(parkingConf)
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): ParkingConf {
        return parkingConfRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException("Data not found.")
        }
    }

    @Transactional(rollbackFor = [ResourceNotFoundException::class])
    fun deleteById(id: Long) {
        if (!exists(id)) {
            throw ResourceNotFoundException("Data not found.")
        }
        parkingConfRepository.deleteById(id)
    }

    @Transactional(readOnly = true)
    fun exists(id: Long): Boolean {
        return parkingConfRepository.existsById(id)
    }

}