package controllers

import javax.inject.Inject

import models.Account
import play.api.data._
import play.api.i18n._
import play.api.mvc._

/**
 * The AccountController
 */
class AccountController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  import AccountForm._

  private val accounts = scala.collection.mutable.ArrayBuffer(
    Account(1, "Eoin Costelloe", "eoincos@gmail.com", "16 Beechfield Road", None, "Walkinstown", Some("Dublin 12"), "Ireland", Some("+353 86 244 3605"), None, None),
    Account(2, "Digitary", "info@digitary.net", "Unit 3, Northwood House", Some("Northwood Business Campus"), "Santry", Some("Dublin 9"), "Ireland", None, None, None)
  )

  private val postUrl = routes.AccountController.createAccount()

  def listAccounts = Action { implicit request: MessagesRequest[AnyContent] =>
    // Pass an unpopulated form to the template
    Ok(views.html.listAccounts(accounts, form, postUrl))
  }

  // This will be the action that handles our form post
  def createAccount = Action { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[Data] =>
      // This is the bad case, where the form had validation errors.
      // Let's show the user the form again, with the errors highlighted.
      // Note how we pass the form with errors to the template.
      BadRequest(views.html.listAccounts(accounts, formWithErrors, postUrl))
    }

    val successFunction = { data: Data =>
      // This is the good case, where the form was successfully parsed as a Data.
      val account = Account(
        id = data.id,
        name = data.name,
        email = data.email,
        addressLine1 = data.addressLine1,
        addressLine2 = data.addressLine2,
        townCity = data.townCity,
        postalCode = data.postalCode,
        country = data.country,
        telephone1 = data.telephone1,
        telephone2 = data.telephone2,
        telephone3 = data.telephone3
        )
      accounts.append(account)
      Redirect(routes.AccountController.listAccounts()).flashing("info" -> "Account added!")
    }

    val formValidationResult = form.bindFromRequest
    formValidationResult.fold(errorFunction, successFunction)
  }

}
