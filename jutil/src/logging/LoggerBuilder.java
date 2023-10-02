package logging;

import java.util.Optional;

public class LoggerBuilder {
  Optional<Mode> mode;
  Optional<String> path;
  Optional<Boolean> emoji;
  Optional<Boolean> modeEmoji;

  public LoggerBuilder() {
    this.mode = Optional.empty();
    this.path = Optional.empty();
    this.emoji = Optional.empty();
    this.modeEmoji = Optional.empty();
  }



  public LoggerBuilder mode(Mode mode) {
    this.mode = Optional.of(mode);
    return this;
  }

  public LoggerBuilder path(String path) {
    this.path = Optional.of(path);
    return this;
  }

  public LoggerBuilder emoji(boolean emoji) {
    this.emoji = Optional.of(emoji);
    return this;
  }

  public LoggerBuilder modeEmoji(boolean emoji) {
    this.modeEmoji = Optional.of(emoji);
    return this;
  }

  public Logger build() {
    return new Logger(
        this.mode.orElse(Mode.Main),
        this.path,
        this.emoji.orElse(false),
        this.modeEmoji.orElse(false));
  }
}
