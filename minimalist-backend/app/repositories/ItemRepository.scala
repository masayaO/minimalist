package repositories

import com.google.inject.ImplementedBy
import models.command.ItemInfo
import repositories.jdbc.ItemRepositoryJDBC

@ImplementedBy(classOf[ItemRepositoryJDBC])
trait ItemRepository {
  def createItem(item: ItemInfo): Unit
}
