package kb.moto.controller

import kb.moto.controller.abstr.AbstractPartsController
import kb.moto.model.*
import kb.moto.service.abstr.AbstractPartsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("parts/brakes")
class BrakeSpecController @Autowired constructor(brakeSpecService: AbstractPartsService<BrakeSpec>): AbstractPartsController<BrakeSpec>(brakeSpecService)

@RestController
@RequestMapping("parts/chassis")
class ChassisSpecController @Autowired constructor(chassisSpecService: AbstractPartsService<ChassisSpec>): AbstractPartsController<ChassisSpec>(chassisSpecService)

@RestController
@RequestMapping("parts/driveTrain")
class DriveTrainSpecController @Autowired constructor(driveTrainSpecService: AbstractPartsService<DriveTrainSpec>): AbstractPartsController<DriveTrainSpec>(driveTrainSpecService)

@RestController
@RequestMapping("parts/electricalSystem")
class ElectricalSystemSpecController @Autowired constructor(electricalSystemSpecService: AbstractPartsService<ElectricalSystemSpec>): AbstractPartsController<ElectricalSystemSpec>(electricalSystemSpecService)

@RestController
@RequestMapping("parts/engine")
class EngineSpecController @Autowired constructor(engineSpecService: AbstractPartsService<EngineSpec>): AbstractPartsController<EngineSpec>(engineSpecService)

@RestController
@RequestMapping("parts/frontSuspension")
class FrontSuspensionSpecController @Autowired constructor(frontSuspensionSpecService: AbstractPartsService<FrontSuspensionSpec>): AbstractPartsController<FrontSuspensionSpec>(frontSuspensionSpecService)

@RestController
@RequestMapping("parts/fuelSystem")
class FuelSystemSpecController @Autowired constructor(fuelSystemSpecService: AbstractPartsService<FuelSystemSpec>): AbstractPartsController<FuelSystemSpec>(fuelSystemSpecService)

@RestController
@RequestMapping("/manufacturer")
class ManufacturerController @Autowired constructor(manufacturerService: AbstractPartsService<Manufacturer>): AbstractPartsController<Manufacturer>(manufacturerService)

@RestController
@RequestMapping("/model")
class MotoModelController @Autowired constructor(motoModelService: AbstractPartsService<MotoModel>): AbstractPartsController<MotoModel>(motoModelService)

@RestController
@RequestMapping("parts/rearSuspension")
class RearSuspensionSpecController @Autowired constructor(rearSuspensionSpecService: AbstractPartsService<RearSuspensionSpec>): AbstractPartsController<RearSuspensionSpec>(rearSuspensionSpecService)

