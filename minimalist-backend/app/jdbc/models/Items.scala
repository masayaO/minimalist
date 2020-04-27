package jdbc.models

import scalikejdbc._
import java.time.{ZonedDateTime}

case class Items(
    itemId: Long,
    minimalistId: Long,
    itemName: String,
    itemComment: String,
    itemQuantity: Int,
    itemStatus: String,
    itemImageUrl: Option[String] = None,
    createdAt: Option[ZonedDateTime] = None,
    updatedAt: Option[ZonedDateTime] = None
) {

  def save()(implicit session: DBSession = Items.autoSession): Items = Items.save(this)(session)

  def destroy()(implicit session: DBSession = Items.autoSession): Int = Items.destroy(this)(session)

}

object Items extends SQLSyntaxSupport[Items] {

  override val tableName = "items"

  override val columns = Seq(
    "item_id",
    "minimalist_id",
    "item_name",
    "item_comment",
    "item_quantity",
    "item_status",
    "item_image_url",
    "created_at",
    "updated_at"
  )

  def apply(i: SyntaxProvider[Items])(rs: WrappedResultSet): Items = apply(i.resultName)(rs)
  def apply(i: ResultName[Items])(rs: WrappedResultSet): Items = new Items(
    itemId = rs.get(i.itemId),
    minimalistId = rs.get(i.minimalistId),
    itemName = rs.get(i.itemName),
    itemComment = rs.get(i.itemComment),
    itemQuantity = rs.get(i.itemQuantity),
    itemStatus = rs.get(i.itemStatus),
    itemImageUrl = rs.get(i.itemImageUrl),
    createdAt = rs.get(i.createdAt),
    updatedAt = rs.get(i.updatedAt)
  )

  val i = Items.syntax("i")

  override val autoSession = AutoSession

  def find(itemId: Long)(implicit session: DBSession = autoSession): Option[Items] = {
    withSQL {
      select.from(Items as i).where.eq(i.itemId, itemId)
    }.map(Items(i.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Items] = {
    withSQL(select.from(Items as i)).map(Items(i.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Items as i)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Items] = {
    withSQL {
      select.from(Items as i).where.append(where)
    }.map(Items(i.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Items] = {
    withSQL {
      select.from(Items as i).where.append(where)
    }.map(Items(i.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Items as i).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
      minimalistId: Long,
      itemName: String,
      itemComment: String,
      itemQuantity: Int,
      itemStatus: String,
      itemImageUrl: Option[String] = None
  )(implicit session: DBSession = autoSession): Unit = {
    val generatedKey = withSQL {
      insert
        .into(Items)
        .namedValues(
          column.minimalistId -> minimalistId,
          column.itemName -> itemName,
          column.itemComment -> itemComment,
          column.itemQuantity -> itemQuantity,
          column.itemStatus -> itemStatus,
          column.itemImageUrl -> itemImageUrl
        )
    }.updateAndReturnGeneratedKey.apply()
  }

  def batchInsert(entities: collection.Seq[Items])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("minimalistId") -> entity.minimalistId,
        Symbol("itemName") -> entity.itemName,
        Symbol("itemComment") -> entity.itemComment,
        Symbol("itemQuantity") -> entity.itemQuantity,
        Symbol("itemStatus") -> entity.itemStatus,
        Symbol("itemImageUrl") -> entity.itemImageUrl,
        Symbol("createdAt") -> entity.createdAt,
        Symbol("updatedAt") -> entity.updatedAt
      )
    )
    SQL("""insert into items(
      minimalist_id,
      item_name,
      item_comment,
      item_quantity,
      item_status,
      item_image_url,
      created_at,
      updated_at
    ) values (
      {minimalistId},
      {itemName},
      {itemComment},
      {itemQuantity},
      {itemStatus},
      {itemImageUrl},
      {createdAt},
      {updatedAt}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: Items)(implicit session: DBSession = autoSession): Items = {
    withSQL {
      update(Items)
        .set(
          column.itemId -> entity.itemId,
          column.minimalistId -> entity.minimalistId,
          column.itemName -> entity.itemName,
          column.itemComment -> entity.itemComment,
          column.itemQuantity -> entity.itemQuantity,
          column.itemStatus -> entity.itemStatus,
          column.itemImageUrl -> entity.itemImageUrl,
          column.createdAt -> entity.createdAt,
          column.updatedAt -> entity.updatedAt
        )
        .where
        .eq(column.itemId, entity.itemId)
    }.update.apply()
    entity
  }

  def destroy(entity: Items)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Items).where.eq(column.itemId, entity.itemId) }.update.apply()
  }

}
