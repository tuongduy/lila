package lila

package object db
    extends common.PackageObject
    with common.WithPlay
    with common.WithDb {

  type WithStringId = { def id: String }
}
