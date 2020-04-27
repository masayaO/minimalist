package models

import play.api.libs.functional.syntax._
import play.api.libs.json.{Format, Writes, __}

case class Item(
    itemId: Long,
    minimalistId: Long,
    itemName: String,
    itemComment: String,
    itemQuantity: Int,
    itemStatus: String,
    itemImageUrl: Option[String] = None
)

object Item {
  implicit val itemFormat: Writes[Item] = ((__ \ "itemId").write[Long] and
    (__ \ "minimalistId").write[Long] and
    (__ \ "itemName").write[String] and
    (__ \ "itemComment").write[String] and
    (__ \ "itemQuantity").write[Int] and
    (__ \ "itemStatus").write[String] and
    (__ \ "itemImageUrl").writeNullable[String])(unlift(Item.unapply))
}
