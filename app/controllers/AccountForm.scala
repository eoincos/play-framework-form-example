package controllers

object AccountForm {
  import play.api.data.Forms._
  import play.api.data.Form

  /**
   * A form processing DTO that maps to the form below.
   *
   * Using a class specifically for form binding reduces the chances
   * of a parameter tampering attack and makes code clearer.
   */
  case class Data(
    id: Int,
    name: String,
    email: String,
    addressLine1: String,
    addressLine2: Option[String],
    townCity: String,
    postalCode: Option[String],
    country: String,
    telephone1: Option[String],
    telephone2: Option[String],
    telephone3: Option[String]
  )

  /**
   * The form definition for the "create an account" form.
   * It specifies the form fields and their types,
   * as well as how to convert from a Data to form data and vice versa.
   */
  val form = Form(
    mapping(
      "id" -> number(min = 0),
      "name" -> nonEmptyText,
      "email" -> email,
      "addressLine1" -> nonEmptyText,
      "addressLine2" -> optional(text),
      "townCity" -> nonEmptyText,
      "postalCode" -> optional(text),
      "country" -> nonEmptyText,
      "telephone1" -> optional(text),
      "telephone2" -> optional(text),
      "telephone3" -> optional(text)
    )(Data.apply)(Data.unapply)
  )
}
