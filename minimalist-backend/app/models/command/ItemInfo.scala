package models.command

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.Reads.{maxLength, minLength}
import play.api.libs.json.{Reads, __}

case class ItemInfo(
    minimalistId: Long,
    itemName: String,
    itemComment: String,
    itemQuantity: Int,
    itemStatus: String,
    itemImageUrl: Option[String]
)

object ItemInfo {
  implicit val itemInfoReads: Reads[ItemInfo] = (
    (__ \ "minimalistId").read[Long] and
      (__ \ "itemName").read[String](minLength[String](1).keepAnd(maxLength[String](50))) and
      (__ \ "itemComment").read[String](minLength[String](1).keepAnd(maxLength[String](150))) and
      (__ \ "itemQuantity").read[Int](min(1)) and
      (__ \ "itemStatus").read[String](minLength[String](1).keepAnd(maxLength[String](150))) and
      (__ \ "itemImageUrl").readNullable[String]
  )(ItemInfo.apply _)
}
