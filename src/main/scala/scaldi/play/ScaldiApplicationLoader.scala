package scaldi.play

import play.api.ApplicationLoader.Context
import play.api._
import play.core.WebCommands
import scaldi._

class ScaldiApplicationLoader(val builder: ScaldiApplicationBuilder) extends ApplicationLoader {
  def this() = this(new ScaldiApplicationBuilder())

  def load(context: Context): Application =
    builder
      .in(context.environment)
      .loadConfig(context.initialConfiguration)
      .prependModule(new Module {
        bind [OptionalDevContext] to new OptionalDevContext(context.devContext)
        // TODO: evaluate if `OptionalSourceMapper` still needs to be bound
        bind [OptionalSourceMapper] to new OptionalSourceMapper(context.devContext.map(_.sourceMapper))
      })
      .build()
}