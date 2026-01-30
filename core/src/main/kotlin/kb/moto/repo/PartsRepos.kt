package kb.moto.repo

import kb.moto.model.*
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository("brakeSpecRepo")
interface BrakeSpecRepo: R2dbcRepository<BrakeSpec, Any>

@Repository("chassisSpecRepo")
interface ChassisSpecRepo: R2dbcRepository<ChassisSpec, Any>

@Repository("driveTrainSpecRepo")
interface DriveTrainSpecRepo: R2dbcRepository<DriveTrainSpec, Any>

@Repository("electricalSystemSpecRepo")
interface ElectricalSystemSpecRepo: R2dbcRepository<ElectricalSystemSpec, Any>

@Repository("engineSpecRepo")
interface EngineSpecRepo: R2dbcRepository<EngineSpec, Any>

@Repository("frontSuspensionSpecRepo")
interface FrontSuspensionSpecRepo: R2dbcRepository<FrontSuspensionSpec, Any>

@Repository("fuelSystemSpecRepo")
interface FuelSystemSpecRepo: R2dbcRepository<FuelSystemSpec, Any>

@Repository("manufacturerRepo")
interface ManufacturerRepo: R2dbcRepository<Manufacturer, Any>

@Repository("motoModelRepo")
interface MotoModelRepo: R2dbcRepository<MotoModel, Any>

@Repository("rearSuspensionSpecRepo")
interface RearSuspensionSpecRepo: R2dbcRepository<RearSuspensionSpec, Any>
