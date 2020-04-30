package repositories.jdbc

import jdbc.models._
import models.command.ItemInfo
import repositories.ItemRepository
import scalikejdbc.DB

class ItemRepositoryJDBC extends ItemRepository {
  def createItem(item: ItemInfo): Unit = {
    DB.localTx { implicit session =>
      Items.create(
        minimalistId = item.minimalistId,
        itemName = item.itemName,
        itemComment = item.itemComment,
        itemQuantity = item.itemQuantity,
        itemStatus = item.itemStatus,
        itemImageUrl = item.itemImageUrl
      )
    }
  }
}
