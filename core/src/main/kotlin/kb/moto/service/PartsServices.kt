package kb.moto.service

import kb.moto.model.ids.MotoId
import kb.moto.service.abstr.AbstractPartsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Service

@Service("brakeSpecService")
class BrakeSpecService<T: MotoId<Any>> @Autowired constructor(brakeSpecRepo: R2dbcRepository<T, Any>
): AbstractPartsService<T>(brakeSpecRepo)

@Service("chassisSpecService")
class ChassisSpecService<T: MotoId<Any>> @Autowired constructor(chassisSpecRepo: R2dbcRepository<T, Any>
): AbstractPartsService<T>(chassisSpecRepo)

@Service("driveTrainSpecService")
class DriveTrainSpecService<T: MotoId<Any>> @Autowired constructor(driveTrainSpecRepo: R2dbcRepository<T, Any>
): AbstractPartsService<T>(driveTrainSpecRepo)

@Service("electricalSystemSpecService")
class ElectricalSystemSpecService<T: MotoId<Any>> @Autowired constructor(electricalSystemSpecRepo: R2dbcRepository<T, Any>
): AbstractPartsService<T>(electricalSystemSpecRepo)

@Service("engineSpecService")
class EngineSpecService<T: MotoId<Any>> @Autowired constructor(engineSpecRepo: R2dbcRepository<T, Any>
): AbstractPartsService<T>(engineSpecRepo)

@Service("frontSuspensionSpecService")
class FrontSuspensionSpecService<T: MotoId<Any>> @Autowired constructor(frontSuspensionSpecRepo: R2dbcRepository<T, Any>
): AbstractPartsService<T>(frontSuspensionSpecRepo)

@Service("fuelSystemSpecService")
class FuelSystemSpecService<T: MotoId<Any>> @Autowired constructor(fuelSystemSpecRepo: R2dbcRepository<T, Any>
): AbstractPartsService<T>(fuelSystemSpecRepo)

@Service("manufacturerService")
class ManufacturerService<T: MotoId<Any>> @Autowired constructor(manufacturerRepo: R2dbcRepository<T, Any>
): AbstractPartsService<T>(manufacturerRepo)

@Service("motoModelService")
class MotoModelService<T: MotoId<Any>> @Autowired constructor(motoModelRepo: R2dbcRepository<T, Any>
): AbstractPartsService<T>(motoModelRepo)

@Service("rearSuspensionSpecService")
class RearSuspensionSpecService<T: MotoId<Any>> @Autowired constructor(rearSuspensionSpecRepo: R2dbcRepository<T, Any>,
): AbstractPartsService<T>(rearSuspensionSpecRepo)

