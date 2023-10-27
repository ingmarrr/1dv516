import logging.Logger;
import logging.Mode;
import sorting.Shell;
import testing.Test;
import testing.Unit;

import java.util.Arrays;

public class ShellSortTest {

  public static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testShellSort() throws Test.FailException {
    var arr = new Integer[] { 3, 4, 2, 1 };
    log.info(Arrays.toString(arr));
    Shell.sortStd(arr);
    log.info(Arrays.toString(arr));
  }
}
