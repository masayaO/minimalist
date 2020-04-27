package jdbc.models

import org.scalatest._
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._
import java.time.{ZonedDateTime}


class SysConfigSpec extends fixture.FlatSpec with Matchers with AutoRollback {
  val sc = SysConfig.syntax("sc")

  behavior of "SysConfig"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = SysConfig.find("MyString")
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = SysConfig.findBy(sqls.eq(sc.variable, "MyString"))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = SysConfig.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = SysConfig.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = SysConfig.findAllBy(sqls.eq(sc.variable, "MyString"))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = SysConfig.countBy(sqls.eq(sc.variable, "MyString"))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = SysConfig.create(variable = "MyString")
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = SysConfig.findAll().head
    // TODO modify something
    val modified = entity
    val updated = SysConfig.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = SysConfig.findAll().head
    val deleted = SysConfig.destroy(entity)
    deleted should be(1)
    val shouldBeNone = SysConfig.find("MyString")
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = SysConfig.findAll()
    entities.foreach(e => SysConfig.destroy(e))
    val batchInserted = SysConfig.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
