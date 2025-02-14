package tests.tokens

import tests.BaseSemanticTokensSuite

class SemanticTokensScala3Suite extends BaseSemanticTokensSuite {
  override protected def ignoreScalaVersion: Option[IgnoreScalaVersion] = Some(
    IgnoreScala2
  )

  check(
    "enum",
    s"""|package <<example>>/*namespace*/
        |
        |enum <<FooEnum>>/*enum,abstract*/:
        |  case <<Bar>>/*enum*/, <<Baz>>/*enum*/
        |object <<FooEnum>>/*class*/
        |""".stripMargin,
  )

  check(
    "enum1",
    s"""|package <<example>>/*namespace*/
        |
        |enum <<FooEnum>>/*enum,abstract*/:
        |  case <<A>>/*enum*/(<<a>>/*variable,readonly*/: <<Int>>/*class,abstract*/)
        |  case <<B>>/*enum*/(<<a>>/*variable,readonly*/: <<Int>>/*class,abstract*/, <<b>>/*variable,readonly*/: <<Int>>/*class,abstract*/)
        |  case <<C>>/*enum*/(<<a>>/*variable,readonly*/: <<Int>>/*class,abstract*/, <<b>>/*variable,readonly*/: <<Int>>/*class,abstract*/, <<c>>/*variable,readonly*/: <<Int>>/*class,abstract*/)
        |""".stripMargin,
  )

  // Issue: Sequential parameters are not highlighted
  // https://github.com/scalameta/metals/issues/4985
  check(
    "named-arguments",
    s"""|package <<example>>/*namespace*/
        |
        |def <<m>>/*method*/(<<xs>>/*parameter*/: <<Int>>/*class,abstract*/*) = <<xs>>/*parameter*/.<<map>>/*method*/(<<_>>/*parameter*/ <<+>>/*method*/ 1)
        |val <<a>>/*variable,readonly*/ = <<m>>/*method*/(xs = 1,2,3)
        |""".stripMargin,
  )

  // Issue: Structural types are not highlighted
  // https://github.com/scalameta/metals/issues/4984
  check(
    "structural-types",
    s"""|package <<example>>/*namespace*/
        |
        |import <<reflect>>/*namespace*/.<<Selectable>>/*class*/.<<reflectiveSelectable>>/*method*/
        |
        |object <<StructuralTypes>>/*class*/:
        |  type <<User>>/*type*/ = {
        |    def <<name>>/*method*/: <<String>>/*type*/
        |    def <<age>>/*method*/: <<Int>>/*class,abstract*/
        |  }
        |
        |  val <<user>>/*variable,readonly*/ = null.<<asInstanceOf>>/*method*/[<<User>>/*type*/]
        |  <<user>>/*variable,readonly*/.name
        |  <<user>>/*variable,readonly*/.age
        |
        |  val <<V>>/*variable,readonly*/: <<Object>>/*class*/ {
        |    def <<scalameta>>/*method*/: <<String>>/*type*/
        |  } = new:
        |    def <<scalameta>>/*method*/ = "4.0"
        |  <<V>>/*variable,readonly*/.scalameta
        |end StructuralTypes
        |""".stripMargin,
  )

  check(
    "vars",
    s"""|package <<example>>/*namespace*/
        |
        |object <<A>>/*class*/ {
        |  val <<a>>/*variable,readonly*/ = 1
        |  var <<b>>/*variable*/ = 2
        |  val <<c>>/*variable,readonly*/ = <<List>>/*variable,readonly*/(1,<<a>>/*variable,readonly*/,<<b>>/*variable*/)
        |  <<b>>/*variable*/ = <<a>>/*variable,readonly*/
        |""".stripMargin,
  )

}
