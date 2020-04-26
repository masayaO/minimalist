package jdbc.models

import scalikejdbc._
import java.time.{ZonedDateTime}

case class ItemCategory(
  categoryId: Long,
  minimalistId: Long,
  categoryName: String,
  createdAt: Option[ZonedDateTime] = None,
  updatedAt: Option[ZonedDateTime] = None) {

  def save()(implicit session: DBSession = ItemCategory.autoSession): ItemCategory = ItemCategory.save(this)(session)

  def destroy()(implicit session: DBSession = ItemCategory.autoSession): Int = ItemCategory.destroy(this)(session)

}


object ItemCategory extends SQLSyntaxSupport[ItemCategory] {

  override val tableName = "item_category"

  override val columns = Seq("category_id", "minimalist_id", "category_name", "created_at", "updated_at")

  def apply(ic: SyntaxProvider[ItemCategory])(rs: WrappedResultSet): ItemCategory = apply(ic.resultName)(rs)
  def apply(ic: ResultName[ItemCategory])(rs: WrappedResultSet): ItemCategory = new ItemCategory(
    categoryId = rs.get(ic.categoryId),
    minimalistId = rs.get(ic.minimalistId),
    categoryName = rs.get(ic.categoryName),
    createdAt = rs.get(ic.createdAt),
    updatedAt = rs.get(ic.updatedAt)
  )

  val ic = ItemCategory.syntax("ic")

  override val autoSession = AutoSession

  def find(categoryId: Long)(implicit session: DBSession = autoSession): Option[ItemCategory] = {
    withSQL {
      select.from(ItemCategory as ic).where.eq(ic.categoryId, categoryId)
    }.map(ItemCategory(ic.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ItemCategory] = {
    withSQL(select.from(ItemCategory as ic)).map(ItemCategory(ic.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ItemCategory as ic)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ItemCategory] = {
    withSQL {
      select.from(ItemCategory as ic).where.append(where)
    }.map(ItemCategory(ic.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ItemCategory] = {
    withSQL {
      select.from(ItemCategory as ic).where.append(where)
    }.map(ItemCategory(ic.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ItemCategory as ic).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    minimalistId: Long,
    categoryName: String,
    createdAt: Option[ZonedDateTime] = None,
    updatedAt: Option[ZonedDateTime] = None)(implicit session: DBSession = autoSession): ItemCategory = {
    val generatedKey = withSQL {
      insert.into(ItemCategory).namedValues(
        column.minimalistId -> minimalistId,
        column.categoryName -> categoryName,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    ItemCategory(
      categoryId = generatedKey,
      minimalistId = minimalistId,
      categoryName = categoryName,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: collection.Seq[ItemCategory])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("minimalistId") -> entity.minimalistId,
        Symbol("categoryName") -> entity.categoryName,
        Symbol("createdAt") -> entity.createdAt,
        Symbol("updatedAt") -> entity.updatedAt))
    SQL("""insert into item_category(
      minimalist_id,
      category_name,
      created_at,
      updated_at
    ) values (
      {minimalistId},
      {categoryName},
      {createdAt},
      {updatedAt}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: ItemCategory)(implicit session: DBSession = autoSession): ItemCategory = {
    withSQL {
      update(ItemCategory).set(
        column.categoryId -> entity.categoryId,
        column.minimalistId -> entity.minimalistId,
        column.categoryName -> entity.categoryName,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.categoryId, entity.categoryId)
    }.update.apply()
    entity
  }

  def destroy(entity: ItemCategory)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ItemCategory).where.eq(column.categoryId, entity.categoryId) }.update.apply()
  }

}
