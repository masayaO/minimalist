package controllers

import javax.inject.Inject
import jdbc.models.Items.i
import play.api.mvc.{AbstractController, ControllerComponents}
import scalikejdbc._
import jdbc.models._
import models.Item
import play.api.libs.json.Json

class ItemController @Inject() (components: ControllerComponents) extends AbstractController(components) {

  def list(minimalistId: Long) = Action { implicit req =>
    val i = Items.syntax("i")
    DB.readOnly { implicit session =>
      val items = withSQL {
        select.from(Items as i).where.eq(i.minimalistId, minimalistId)
      }.map(Items(i.resultName)).list.apply()

      val itemsForResponse: Seq[Item] = items.map { item =>
        Item(
          itemId = item.itemId,
          minimalistId = item.minimalistId,
          itemName = item.itemName,
          itemComment = item.itemComment,
          itemQuantity = item.itemQuantity,
          itemStatus = item.itemStatus
        )
      }

      Ok(Json.obj("items" -> itemsForResponse))
    }
  }
}
