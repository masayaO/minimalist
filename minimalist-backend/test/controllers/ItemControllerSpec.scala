package controllers

import akka.stream.Materializer
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._
import repositories.ItemRepository

class ItemControllerSpec extends PlaySpecBase {

  implicit lazy val materializer: Materializer = app.materializer

  def createController(mockItemRepository: ItemRepository) = new ItemController(
    components = stubControllerComponents(),
    itemRepository = mockItemRepository
  )

  "#create" when {
    "アイテムの新規登録に成功した時" must {
      "200を返す" in {
        val mockItemRepository: ItemRepository = mock[ItemRepository]
        val controller = createController(mockItemRepository)

        val requestBody = Json.obj(
          "minimalistId" -> 1,
          "itemName" -> "iPhone",
          "itemComment" -> "My Smart Phone",
          "itemQuantity" -> 1,
          "itemStatus" -> "HAVING"
        )
        val request = FakeRequest(POST, "/item/create")
          .withJsonBody(requestBody)
          .withHeaders(CONTENT_TYPE -> JSON)
        val apiResult = call(controller.create(), request)

        status(apiResult) mustBe OK
      }
    }
  }
}
