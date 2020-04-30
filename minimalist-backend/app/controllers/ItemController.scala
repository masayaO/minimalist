package controllers

import javax.inject.Inject
import jdbc.models._
import models.Item
import models.command.ItemInfo
import play.api.libs.json.{JsError, JsValue, Json}
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import repositories.ItemRepository
import scalikejdbc._

class ItemController @Inject() (components: ControllerComponents, itemRepository: ItemRepository)
    extends AbstractController(components) {

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
          itemStatus = item.itemStatus,
          itemImageUrl = item.itemImageUrl
        )
      }

      Ok(Json.obj("items" -> itemsForResponse))
    }
  }

  def create(): Action[JsValue] = Action(parse.json) { implicit req =>
    req.body
      .validate[ItemInfo]
      .map { itemInfo =>
        itemRepository.createItem(itemInfo)
        Ok(Json.obj("result" -> "success"))
      }
      .recoverTotal { e =>
        BadRequest(Json.obj("result" -> "failure", "error" -> JsError.toJson(e)))
      }
  }
}
