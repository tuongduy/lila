package lila.db
package api

import Implicits._

import reactivemongo.bson._
import reactivemongo.api.Cursor
import play.modules.reactivemongo.json.ImplicitBSONHandlers._
import play.api.libs.json._
import play.api.libs.iteratee._

object $enumerate {

  def apply[A: BSONDocumentReader](query: QueryBuilder)(op: A ⇒ Funit): Funit =
    query.cursor[A].enumerate run {
      Iteratee.foreach((obj: A) ⇒ op(obj))
    }

  def bulk[A: BSONDocumentReader](query: QueryBuilder, size: Int, limit: Int = Int.MaxValue)(op: List[A] ⇒ Funit): Funit =
    query.batch(size).cursor[A].enumerateBulks(limit) run {
      Iteratee.foreach((objs: Iterator[A]) ⇒
        op(objs.toList)
      )
    }
}
