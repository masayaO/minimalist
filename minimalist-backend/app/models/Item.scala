package models

import play.api.libs.functional.syntax._
import play.api.libs.json.{Format, __}

case class Item(
    itemId: Long,
    minimalistId: Long,
    itemName: String,
    itemComment: String,
    itemQuantity: Int,
    itemStatus: String
)

object Item {
  implicit val itemFormat: Format[Item] = ((__ \ "itemId").format[Long] and
    (__ \ "minimalistId").format[Long] and
    (__ \ "itemName").format[String] and
    (__ \ "itemComment").format[String] and
    (__ \ "itemQuantity").format[Int] and
    (__ \ "itemStatus").format[String])(Item.apply, unlift(Item.unapply))
}
