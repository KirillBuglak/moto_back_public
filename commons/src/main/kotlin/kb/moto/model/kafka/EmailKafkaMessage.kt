package kb.moto.model.kafka

data class EmailKafkaMessage(
    var to: String,
    var subject: String,
    var templateName: String,
    var templateValues: Map<String, Any>
)