package jdbc.models

import scalikejdbc._
import java.time.{ZonedDateTime}

case class Minimalists(
  minimalistId: Long,
  minimalistName: String,
  minimalistEmail: String,
  minimalistPassword: String,
  createdAt: Option[ZonedDateTime] = None,
  updatedAt: Option[ZonedDateTime] = None) {

  def save()(implicit session: DBSession = Minimalists.autoSession): Minimalists = Minimalists.save(this)(session)

  def destroy()(implicit session: DBSession = Minimalists.autoSession): Int = Minimalists.destroy(this)(session)

}


object Minimalists extends SQLSyntaxSupport[Minimalists] {

  override val tableName = "minimalists"

  override val columns = Seq("minimalist_id", "minimalist_name", "minimalist_email", "minimalist_password", "created_at", "updated_at")

  def apply(m: SyntaxProvider[Minimalists])(rs: WrappedResultSet): Minimalists = apply(m.resultName)(rs)
  def apply(m: ResultName[Minimalists])(rs: WrappedResultSet): Minimalists = new Minimalists(
    minimalistId = rs.get(m.minimalistId),
    minimalistName = rs.get(m.minimalistName),
    minimalistEmail = rs.get(m.minimalistEmail),
    minimalistPassword = rs.get(m.minimalistPassword),
    createdAt = rs.get(m.createdAt),
    updatedAt = rs.get(m.updatedAt)
  )

  val m = Minimalists.syntax("m")

  override val autoSession = AutoSession

  def find(minimalistId: Long)(implicit session: DBSession = autoSession): Option[Minimalists] = {
    withSQL {
      select.from(Minimalists as m).where.eq(m.minimalistId, minimalistId)
    }.map(Minimalists(m.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Minimalists] = {
    withSQL(select.from(Minimalists as m)).map(Minimalists(m.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Minimalists as m)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Minimalists] = {
    withSQL {
      select.from(Minimalists as m).where.append(where)
    }.map(Minimalists(m.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Minimalists] = {
    withSQL {
      select.from(Minimalists as m).where.append(where)
    }.map(Minimalists(m.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Minimalists as m).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    minimalistName: String,
    minimalistEmail: String,
    minimalistPassword: String,
    createdAt: Option[ZonedDateTime] = None,
    updatedAt: Option[ZonedDateTime] = None)(implicit session: DBSession = autoSession): Minimalists = {
    val generatedKey = withSQL {
      insert.into(Minimalists).namedValues(
        column.minimalistName -> minimalistName,
        column.minimalistEmail -> minimalistEmail,
        column.minimalistPassword -> minimalistPassword,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    Minimalists(
      minimalistId = generatedKey,
      minimalistName = minimalistName,
      minimalistEmail = minimalistEmail,
      minimalistPassword = minimalistPassword,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: collection.Seq[Minimalists])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("minimalistName") -> entity.minimalistName,
        Symbol("minimalistEmail") -> entity.minimalistEmail,
        Symbol("minimalistPassword") -> entity.minimalistPassword,
        Symbol("createdAt") -> entity.createdAt,
        Symbol("updatedAt") -> entity.updatedAt))
    SQL("""insert into minimalists(
      minimalist_name,
      minimalist_email,
      minimalist_password,
      created_at,
      updated_at
    ) values (
      {minimalistName},
      {minimalistEmail},
      {minimalistPassword},
      {createdAt},
      {updatedAt}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: Minimalists)(implicit session: DBSession = autoSession): Minimalists = {
    withSQL {
      update(Minimalists).set(
        column.minimalistId -> entity.minimalistId,
        column.minimalistName -> entity.minimalistName,
        column.minimalistEmail -> entity.minimalistEmail,
        column.minimalistPassword -> entity.minimalistPassword,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.minimalistId, entity.minimalistId)
    }.update.apply()
    entity
  }

  def destroy(entity: Minimalists)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Minimalists).where.eq(column.minimalistId, entity.minimalistId) }.update.apply()
  }

}
