package kb.moto.exceptions

class PartNotFound(message: String): RuntimeException(message)

class UserNotFound(message: String): RuntimeException(message)
class UserContactsNotFound(message: String): RuntimeException(message)
class SameUserName(message: String): RuntimeException(message)
class WrongPassword(message: String): RuntimeException(message)